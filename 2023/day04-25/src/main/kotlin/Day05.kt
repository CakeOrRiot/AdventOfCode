import java.io.File
import kotlin.math.min

class Day05 {

    val doubleNewLines = "(\r\n\r\n)|(\n\n)"
    val newLines = "(\r\n)|(\n)"
    fun solve1() {
        val blocks =
            File("inputs/5.txt").inputStream().bufferedReader().use { it.readText() }.split(doubleNewLines.toRegex())

        val seeds = blocks[0].split(' ').drop(1).map { x -> x.toLong() }
        val maps = mutableListOf<Map>()
        for (block in blocks.drop(1)) {
            val lines = block.split(newLines.toRegex()).drop(1)
            val dsts = mutableListOf<Long>()
            val srcs = mutableListOf<Long>()
            val lens = mutableListOf<Long>()

            for (line in lines.filter { x -> x.isNotEmpty() }) {
                val split = line.split(' ').map { x -> x.toLong() }
                dsts.add(split[0])
                srcs.add(split[1])
                lens.add(split[2])
            }
            maps.add(Map(dsts.toList(), srcs.toList(), lens.toList()))
        }

        var mappedSeeds = seeds.toList()
        for (map in maps) {
            mappedSeeds = mappedSeeds.map { seed ->
                map.apply(seed)
            }
        }
        println(mappedSeeds.min())
    }


    fun solve2() {
        val blocks =
            File("inputs/5.txt").inputStream().bufferedReader().use { it.readText() }
                .split(doubleNewLines.toRegex())

        val ranges =
            blocks[0].split(' ').drop(1).map { x -> x.toLong() }.chunked(2).map { (seed, len) -> Range(seed, len) }
        val maps = mutableListOf<Map>()
        for (block in blocks.drop(1)) {
            val lines = block.split(newLines.toRegex()).drop(1)
            val dsts = mutableListOf<Long>()
            val srcs = mutableListOf<Long>()
            val lens = mutableListOf<Long>()

            for (line in lines.filter { x -> x.isNotEmpty() }) {
                val split = line.split(' ').map { x -> x.toLong() }
                dsts.add(split[0])
                srcs.add(split[1])
                lens.add(split[2])
            }
            maps.add(Map(dsts.toList(), srcs.toList(), lens.toList()))
        }

        var mappedRanges = ranges.toList()
        for (map in maps) {
            mappedRanges = mappedRanges.flatMap { range -> map.apply(range) }
        }
        println(mappedRanges.minBy { x -> x.start }.start)
    }
}

data class Range(val start: Long, val len: Long)

fun rangeIntersection(range1: Range, range2: Range): Range? {
    fun isInside(range1: Range, range2: Range): Range? {
        if (range1.start >= range2.start && range1.start < range2.start + range2.len) {
            return Range(
                range1.start,
                min(range1.len, range2.start + range2.len - range1.start)
            )
        }
        return null
    }

    val inside1 = isInside(range1, range2)
    if (inside1 != null)
        return inside1

    val inside2 = isInside(range2, range1)
    if (inside2 != null)
        return inside2
    return null
}


data class Map(val dstRangeStart: List<Long>, val sourceRangeStart: List<Long>, val len: List<Long>) {
    fun apply(seed: Long): Long {
        for ((dst, src, l) in dstRangeStart.zip(sourceRangeStart).zip(len)
            .map { x -> listOf(x.first.first, x.first.second, x.second) })

            if (seed >= src && seed < src + l) {
                return seed - src + dst
            }
        return seed
    }

    fun apply(range: Range): List<Range> {
        var ranges = listOf(range)
        val resultRanges = mutableListOf<Range>()
        for ((dst, src, l) in dstRangeStart.zip(sourceRangeStart).zip(len)
            .map { x -> listOf(x.first.first, x.first.second, x.second) }) {
            for (range in ranges) {
                val newRanges = mutableListOf<Range>()
                val intersection = rangeIntersection(range, Range(src, l))
                if (intersection != null) {
                    resultRanges.add(Range(dst + (intersection.start - src), intersection.len))
                    val left = Range(range.start, intersection.start - range.start)
                    val right = Range(
                        intersection.start + intersection.len,
                        range.start + range.len - (intersection.start + intersection.len)
                    )
                    if (left.len > 0)
                        newRanges.add(left)
                    if (right.len > 0)
                        newRanges.add(right)
                } else {
                    newRanges.add(range)
                }
                ranges = newRanges.toList()
            }
        }

        resultRanges.addAll(ranges)
        return resultRanges.toList()
    }
}