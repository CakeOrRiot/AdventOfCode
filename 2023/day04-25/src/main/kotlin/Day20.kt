import java.io.File
import java.util.LinkedList
import java.util.Queue

class Day20 {
    fun solve1() {
        val modules = emptyMap<String, Module>().toMutableMap()
        val moduleOutputs = emptyMap<String, List<String>>().toMutableMap()
        val conjunctionModules = emptyList<String>().toMutableList()
        File("inputs/20.txt").inputStream().bufferedReader().lineSequence().toList().forEach { line ->
            val split = line.split(" -> ").filter { it.isNotBlank() }
            val type = split[0][0]
            val name = if (type == 'b') split[0] else split[0].drop(1)
            val outputs = split[1].split("[, ]".toRegex()).filter { it.isNotBlank() }

            val module = when (type) {
                '%' -> FlipFlop(name)
                '&' -> {
                    conjunctionModules.add(name)
                    Conjunction(name)
                }

                else -> Broadcaster(name)
            }
            modules[name] = module
            moduleOutputs[name] = outputs
        }

        val undefinedModules = emptyList<String>().toMutableList()
        for ((module, outputList) in moduleOutputs) {
            for (output in outputList) {
                if (!modules.containsKey(output)) undefinedModules.add(output)
                if (conjunctionModules.contains(output)) {
                    (modules[output] as Conjunction).memory[modules.getValue(module)] = Pulse.LOW
                }
            }
        }
        undefinedModules.forEach {
            modules[it] = Undefined(it)
            moduleOutputs[it] = emptyList()
        }

        for ((moduleName, module) in modules) {
            val outputs = emptyList<Module>().toMutableList()
            for (output in moduleOutputs.getValue(moduleName)) {
                outputs.add(modules.getValue(output))
            }
            module.outputs = outputs
        }


        var lowPulses = 0L
        var highPulses = 0L
        for (i in 0..<1000) {
            val q: Queue<QueueItem> = LinkedList()
            val broadcaster = modules.getValue("broadcaster")
            broadcaster.outputs.forEach { output -> q.add(QueueItem(broadcaster, output, Pulse.LOW)) }
            lowPulses += 1
            while (!q.isEmpty()) {
                val next = q.poll()
                if (next.pulse == Pulse.LOW) lowPulses += 1
                else highPulses += 1
                q.addAll(next.to.apply(next.from, next.pulse))
            }
        }
        println(lowPulses * highPulses)
    }

    fun solve2() {
        val modules = emptyMap<String, Module>().toMutableMap()
        val moduleOutputs = emptyMap<String, List<String>>().toMutableMap()
        val conjunctionModules = emptyList<String>().toMutableList()
        File("inputs/20.txt").inputStream().bufferedReader().lineSequence().toList().forEach { line ->
            val split = line.split(" -> ").filter { it.isNotBlank() }
            val type = split[0][0]
            val name = if (type == 'b') split[0] else split[0].drop(1)
            val outputs = split[1].split("[, ]".toRegex()).filter { it.isNotBlank() }

            val module = when (type) {
                '%' -> FlipFlop(name)
                '&' -> {
                    conjunctionModules.add(name)
                    Conjunction(name)
                }

                else -> Broadcaster(name)
            }
            modules[name] = module
            moduleOutputs[name] = outputs
        }

        val undefinedModules = emptyList<String>().toMutableList()
        for ((module, outputList) in moduleOutputs) {
            for (output in outputList) {
                if (!modules.containsKey(output)) undefinedModules.add(output)
                if (conjunctionModules.contains(output)) {
                    (modules[output] as Conjunction).memory[modules.getValue(module)] = Pulse.LOW
                }
            }
        }
        undefinedModules.forEach {
            modules[it] = Undefined(it)
            moduleOutputs[it] = emptyList()
        }

        for ((moduleName, module) in modules) {
            val outputs = emptyList<Module>().toMutableList()
            for (output in moduleOutputs.getValue(moduleName)) {
                outputs.add(modules.getValue(output))
            }
            module.outputs = outputs
        }

        var i = 0L
        var res = -1L
        val states = emptyMap<String, MutableList<Pulse>>().toMutableMap()

        modules.forEach { states[it.key] = emptyList<Pulse>().toMutableList() }
        while (i < 40000) {
            i += 1
            val seen = emptySet<String>().toMutableSet()
            val queue: Queue<QueueItem> = LinkedList()
            val broadcaster = modules.getValue("broadcaster")
            broadcaster.outputs.forEach { output -> queue.add(QueueItem(broadcaster, output, Pulse.LOW)) }
            while (!queue.isEmpty()) {
                val next = queue.poll()
                if (!seen.contains(next.from.name))
                    states[next.from.name]?.add(next.pulse)
                seen.add(next.from.name)

                if (next.pulse == Pulse.LOW && next.to.name == "rx") {
                    res = i
                    break
                }
                queue.addAll(next.to.apply(next.from, next.pulse))
            }
            if (res != -1L) break
        }
        for (node in listOf("bb", "mr", "gl", "kk")) {
            println(node)
            val cycles = states[node]?.mapIndexedNotNull { index, elem -> index.takeIf { elem == Pulse.HIGH } } ?: break
            println(cycles[0])
            println(cycles.windowed(2, 1).map { window -> window[1] - window[0] })
        }

        //ответ - LCM длин
    }

    enum class Pulse {
        LOW, HIGH
    }

    interface Module {
        var outputs: List<Module>
        val name: String
        fun apply(source: Module, pulse: Pulse): Queue<QueueItem>
    }

    data class QueueItem(val from: Module, val to: Module, val pulse: Pulse)
    data class Broadcaster(override val name: String) : Module {
        override var outputs: List<Module> = emptyList()
        override fun apply(source: Module, pulse: Pulse): Queue<QueueItem> {
            val q: Queue<QueueItem> = LinkedList()

            outputs.forEach { output -> q.add(QueueItem(this, output, pulse)) }
            return q
        }
    }

    data class Undefined(override val name: String) : Module {
        override var outputs: List<Module> = emptyList()
        override fun apply(source: Module, pulse: Pulse): Queue<QueueItem> {
            val q: Queue<QueueItem> = LinkedList()
            return q
        }
    }

    data class FlipFlop(override val name: String) : Module {
        private var isOn = false
        override var outputs: List<Module> = emptyList()

        override fun apply(source: Module, pulse: Pulse): Queue<QueueItem> {
            val q: Queue<QueueItem> = LinkedList()
            if (pulse == Pulse.HIGH) return q
            outputs.forEach { output -> q.add(QueueItem(this, output, if (isOn) Pulse.LOW else Pulse.HIGH)) }
            isOn = !isOn
            return q
        }
    }

    data class Conjunction(override val name: String) : Module {
        val memory = emptyMap<Module, Pulse>().toMutableMap()
        override var outputs: List<Module> = emptyList()
        override fun apply(source: Module, pulse: Pulse): Queue<QueueItem> {
            val q: Queue<QueueItem> = LinkedList()
            memory[source] = pulse

            var pulseToSend = Pulse.HIGH
            if (memory.all { (_, p) -> p == Pulse.HIGH }) {
                pulseToSend = Pulse.LOW
            }

            outputs.forEach { output -> q.add(QueueItem(this, output, pulseToSend)) }

            return q
        }
    }

}