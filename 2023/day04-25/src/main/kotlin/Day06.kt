import java.io.File

class Day06 {
    fun solve1() {
        val input =
            File("inputs/6.txt").inputStream().bufferedReader().lineSequence().toList()
        var res = 1
        val times = input[0].split(' ').drop(1).filter { x -> x.isNotBlank() }.map { x -> x.toInt() }
        val distances = input[1].split(' ').drop(1).filter { x -> x.isNotBlank() }.map { x -> x.toInt() }

        for (i in 0..<times.count()) {
            val t = times[i]
            val bestDist = distances[i]
            var cnt = 0
            for (holdTime in 0..t) {
                val timeLeft = t - holdTime
                val dist = timeLeft * holdTime
                if (dist > bestDist) {
                    cnt++
                }
            }
            res *= cnt
        }
        println(res)
    }

    fun solve2() {
        val input =
            File("inputs/6.txt").inputStream().bufferedReader().lineSequence().toList()
        var res = 1
        val time = input[0].split(' ').drop(1).filter { x -> x.isNotBlank() }.joinToString("").toLong()
        val bestDist = input[1].split(' ').drop(1).filter { x -> x.isNotBlank() }.joinToString ("").toLong()

        var cnt = 0
        for (holdTime in 0..time) {
            val timeLeft = time - holdTime
            val dist = timeLeft * holdTime
            if (dist > bestDist) {
                cnt++
            }
        }
        res *= cnt
        println(res)

    }
}
