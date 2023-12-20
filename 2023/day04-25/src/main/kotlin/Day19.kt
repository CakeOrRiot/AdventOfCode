import java.io.File
import java.math.BigInteger
import kotlin.collections.Map
import kotlin.math.max
import kotlin.math.min

class Day19 {
    val doubleNewLines = "(\r\n\r\n)|(\n\n)"
    val newLines = "(\r\n)|(\n)"
    fun solve1() {
        val input =
            File("inputs/19.txt").inputStream().bufferedReader().use { it.readText() }.split(doubleNewLines.toRegex())

        val conditions = input[0].split(newLines.toRegex()).associate { line ->

            val split = line.split("[{,}]".toRegex()).filter { it.isNotBlank() }
            val name = split[0]
            val result = split.last()
            val conditions = split.drop(1).dropLast(1).map { cond ->
                val splitCondition = cond.split(':')
                Pair(splitCondition[0], splitCondition[1])
            }

            name to Pair(conditions, result)
        }

        val ratings = input[1].split(newLines.toRegex()).filter { it.isNotBlank() }.map { it ->
            val split = it.drop(1).dropLast(1).split("[,=]".toRegex()).filter { it.isNotBlank() }
            val x = split[1].toInt()
            val m = split[3].toInt()
            val a = split[5].toInt()
            val s = split[7].toInt()
            Rating(x, m, a, s)
        }
        var res = 0
        ratings.forEach { rating ->
            var curCondition = conditions.getValue("in")
            while (true) {
                val branches = curCondition.first
                var ratingResult = curCondition.second
                for (branch in branches) {
                    val split = branch.first.split("[<>]".toRegex()).filter { it.isNotBlank() }
                    val variable = split[0]
                    val value = split[1].toInt()
                    val result = branch.second
                    val pass = when (branch.first[1]) {
                        '>' -> rating[variable] > value
                        '<' -> rating[variable] < value
                        else -> throw Exception("Can't compare")
                    }
                    if (!pass) continue
                    ratingResult = result
                    break
                }
                if (ratingResult == "A") {
                    res += rating.x + rating.m + rating.a + rating.s
                    break
                } else if (ratingResult == "R") break
                else curCondition = conditions.getValue(ratingResult)
            }
        }
        println(res)
    }

    fun solve2() {
        val input = File("inputs/19.txt").inputStream().bufferedReader().use { it.readText() }
            .split(doubleNewLines.toRegex())

        val conditions = input[0].split(newLines.toRegex()).associate { line ->
            val split = line.split("[{,}]".toRegex()).filter { it.isNotBlank() }
            val name = split[0]
            val result = split.last()
            val conditions = split.drop(1).dropLast(1).map { cond ->
                val splitCondition = cond.split(':')
                Pair(splitCondition[0], splitCondition[1])
            }

            name to Pair(conditions, result)
        }

        val range = RatingRange()

        println(applyRange(conditions, range, "in"))
    }

    fun applyRange(
        conditions: Map<String, Pair<List<Pair<String, String>>, String>>,
        range: RatingRange,
        startCond: String,
        skipBranches: Int = 0
    ): BigInteger {
        if (range.isEmpty()) return BigInteger.ZERO
        val curCondition = conditions.getValue(startCond)
        val branches = curCondition.first
        val finalResult = curCondition.second

        if (skipBranches >= branches.size) {
            val res = when (finalResult) {
                "A" -> (range.x.count().toBigInteger() * range.m.count().toBigInteger() * range.a.count()
                    .toBigInteger() * range.s.count().toBigInteger())

                "R" -> BigInteger.ZERO
                else -> applyRange(conditions, range, finalResult, 0)
            }
            return res
        }
        val branch = branches[skipBranches]
        val branchResult = branch.second
        val split = branch.first.split("[<>]".toRegex()).filter { it.isNotBlank() }
        val variable = split[0]
        val value = split[1].toInt()
        val cmpOperator = branch.first[1]

        val newRangesVar = when (cmpOperator) {
            '>' -> {
                val rangeAccept = IntRange(max(range[variable].first, value + 1), range[variable].last)
                val rangeDecline = IntRange(range[variable].first, rangeAccept.first - 1)
                Pair(rangeAccept, rangeDecline)
            }

            '<' -> {
                val rangeAccept = IntRange(range[variable].first, min(range[variable].last, value - 1))
                val rangeDecline = IntRange(value, range[variable].last)
                Pair(rangeAccept, rangeDecline)
            }

            else -> throw Exception("Can't compare")
        }


        val newRangeDecline = range.clone()
        newRangeDecline[variable] = newRangesVar.second

        val resDecline = applyRange(conditions, newRangeDecline, startCond, skipBranches = skipBranches + 1)

        val newRangeAccept = range.clone()
        newRangeAccept[variable] = newRangesVar.first

        val resAccept = when (branchResult) {
            "A" -> (newRangeAccept.x.count().toBigInteger() * newRangeAccept.m.count()
                .toBigInteger() * newRangeAccept.a.count().toBigInteger() * newRangeAccept.s.count()
                .toBigInteger())

            "R" -> BigInteger.ZERO
            else -> applyRange(conditions, newRangeAccept, branchResult, 0)
        }
        return resAccept + resDecline
    }

    class RatingRange() {
        var x: IntRange = IntRange(1, 4000)
        var m: IntRange = IntRange(1, 4000)
        var a: IntRange = IntRange(1, 4000)
        var s: IntRange = IntRange(1, 4000)

        constructor(x: IntRange, m: IntRange, a: IntRange, s: IntRange) : this() {
            this.x = x
            this.m = m
            this.a = a
            this.s = s
        }

        operator fun get(field: String): IntRange {
            return when (field) {
                "x" -> x
                "m" -> m
                "a" -> a
                "s" -> s
                else -> throw Exception("Unknown field")
            }
        }

        fun isEmpty(): Boolean {
            return x.isEmpty() || m.isEmpty() || a.isEmpty() || s.isEmpty()
        }

        fun clone(): RatingRange {
            return RatingRange(
                IntRange(x.first, x.last),
                IntRange(m.first, m.last),
                IntRange(a.first, a.last),
                IntRange(s.first, s.last)
            )
        }

        operator fun set(field: String, value: IntRange) {
            when (field) {
                "x" -> this.x = value
                "m" -> this.m = value
                "a" -> this.a = value
                "s" -> this.s = value
                else -> throw Exception("Unknown field")
            }
        }
    }

    class Rating<T>(val x: T, val m: T, val a: T, val s: T) where T : Comparable<T> {
        operator fun get(field: String): T {
            return when (field) {
                "x" -> x
                "m" -> m
                "a" -> a
                "s" -> s
                else -> throw Exception("Unknown field")
            }
        }
    }
}
