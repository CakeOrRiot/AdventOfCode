import java.io.File

class Day15 {
    fun solve1() {
        val res = File("inputs/15.txt").inputStream().bufferedReader().use { it.readText() }
            .filter { it != '\n' && it != '\r' }.split(',').sumOf { hash(it) }
        println(res)
    }

    fun solve2() {
        val dict =
            emptyMap<Int, MutableList<Lens>>().toMutableMap()
        for (i in 0..255)
            dict[i] = emptyList<Lens>().toMutableList()
        File("inputs/15.txt").inputStream().bufferedReader().use { it.readText() }
            .filter { it != '\n' && it != '\r' }.split(',').forEach {
                val split = it.split("-|=".toRegex()).filter { it.isNotBlank() }
                val label = split[0]
                val box = hash(label)
                if (split.size == 1) {
                    dict.getValue(box).removeIf { lens -> lens.label == label }
                } else {
                    val len = split[1].toInt()
                    val list = dict.getValue(box)
                    val idx = list.indexOfFirst { lens -> lens.label == label }
                    if (idx == -1) {
                        list.add(Lens(label, len))
                    } else {
                        list[idx] = Lens(label, len)
                    }
                }
            }
        println(dict.map { (box, list) -> (box + 1) * list.mapIndexed { i, lens -> (i + 1) * lens.len }.sum() }.sum())
    }

    fun hash(s: String): Int {
        return s.fold(0) { acc, ch -> ((acc + ch.code) * 17) % 256 }
    }

    data class Lens(val label: String, val len: Int)
}