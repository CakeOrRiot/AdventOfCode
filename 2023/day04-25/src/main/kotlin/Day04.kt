import java.io.File
import java.util.Dictionary
import kotlin.math.pow

class Day04 {

    public fun Solve1() {
        var res = 0
        File("inputs/4.txt").inputStream().bufferedReader().forEachLine {
            val sp = it.split(":")[1].split("|")
            val cardNums = sp[0].split(' ').toSet().filter { it -> it.isNotBlank() }
            val myNums = sp[1].split(' ').toSet().filter { it -> it.isNotBlank() }
            val intersectionSize = cardNums.intersect(myNums).count()

            res += if (intersectionSize > 0) 2.0.pow(intersectionSize.toDouble() - 1).toInt() else 0
        }
        println(res)
    }


    public fun Solve2() {
        var res = 0

        val d = emptyMap<Int, Int>().toMutableMap()
        d.putIfAbsent(1,1)
        var idx = 1
        File("inputs/4.txt").inputStream().bufferedReader().forEachLine {
            d.putIfAbsent(idx, 1)
            val sp = it.split(":")[1].split("|")
            val cardNums = sp[0].split(' ').toSet().filter { it -> it.isNotBlank() }
            val myNums = sp[1].split(' ').toSet().filter { it -> it.isNotBlank() }
            val intersectionSize = cardNums.intersect(myNums).count()
            val copies = d.getValue(idx)

            for (i in idx + 1..idx + intersectionSize) {
                d.putIfAbsent(i, 1)
                d[i] = d.getValue(i) + copies
            }

            idx += 1
        }
        println(d.values.sum())
    }
}