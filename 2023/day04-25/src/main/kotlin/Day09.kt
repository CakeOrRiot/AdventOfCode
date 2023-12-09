import java.io.File

class Day09 {
    fun solve1() {
        val seqs = File("inputs/9.txt").inputStream().bufferedReader().lineSequence().toList()
            .map { it.split(' ').map { x -> x.toInt() } }

        val results = mutableListOf<Int>()
        for (seq in seqs) {
            var curSeq = seq
            val history = mutableListOf<List<Int>>()
            history.add(curSeq)
            while (!curSeq.all { it == 0 }) {
                curSeq = curSeq.windowed(2, partialWindows = false).map { (x, y) ->
                    y - x
                }
                history.add(curSeq)
            }
            results.add(history.sumOf { x -> x.last() })
        }

        println(results.sum())
    }

    fun solve2() {
        val seqs = File("inputs/9.txt").inputStream().bufferedReader().lineSequence().toList()
            .map { it.split(' ').map { x -> x.toInt() } }

        val results = mutableListOf<Int>()
        for (seq in seqs) {
            var curSeq = seq
            val history = mutableListOf<List<Int>>()
            history.add(curSeq)
            while (!curSeq.all { it == 0 }) {
                curSeq = curSeq.windowed(2, partialWindows = false).map { (x, y) ->
                    y - x
                }
                history.add(curSeq)
            }
            results.add(history.reversed().fold(0) { acc, x -> x.first() - acc })
        }
        println(results.sum())
    }
}