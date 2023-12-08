import java.io.File
import java.math.BigInteger

class Day08 {
    fun solve1() {
        val input = File("inputs/8.txt").inputStream().bufferedReader().lineSequence().toList()
        val turns = input[0]

        val map = emptyMap<String, Pair<String, String>>().toMutableMap()
        input.drop(2).forEach { line ->
            val split = line.split(' ', ',', '(', ')', '=').filter { it.isNotBlank() }
            map[split[0]] = Pair(split[1], split[2])
        }

        var turnIdx = 0
        var curLocation = "AAA"
        var steps = 0
        while (true) {
            curLocation =
                if (turns[turnIdx] == 'L') map.getValue(curLocation).first else map.getValue(curLocation).second
            steps++
            if (curLocation == "ZZZ") {
                println(steps)
                break
            }


            turnIdx = (turnIdx + 1) % turns.count()
        }
    }

    fun solve2() {
        val input = File("inputs/8.txt").inputStream().bufferedReader().lineSequence().toList()
        val turns = input[0]

        val map = emptyMap<String, Pair<String, String>>().toMutableMap()
        input.drop(2).forEach { line ->
            val split = line.split(' ', ',', '(', ')', '=').filter { it.isNotBlank() }
            map[split[0]] = Pair(split[1], split[2])
        }

        val startLocations = map.filterKeys { it.endsWith("A") }.keys.toList()
        val periods = startLocations.map { startLocation ->
            var curLocation = startLocation
            var turnIdx = 0
            var steps = 0.toBigInteger()
            var isFirstZ = true

            while (true) {
                curLocation =
                    if (turns[turnIdx] == 'L') map.getValue(curLocation).first else map.getValue(curLocation).second
                if (!isFirstZ)
                    steps++
                if (curLocation.endsWith("Z")) {
                    if (isFirstZ) {
                        isFirstZ = false
                    } else {
                        break
                    }
                }
                turnIdx = (turnIdx + 1) % turns.count()
            }
            steps
        }

        val g = periods.reduce { acc, x -> gcd(acc, x) }
        val lcm = periods.reduce { acc, x -> acc * x / g }
        println(lcm)
    }

    private fun gcd(x: BigInteger, y: BigInteger): BigInteger {
        if (y == 0.toBigInteger()) return x
        return gcd(y, x % y)
    }

}