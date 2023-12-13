import java.io.File

class Day13 {
    val doubleNewLines = "(\r\n\r\n)|(\n\n)"
    val newLines = "(\r\n)|(\n)"
    fun solve1() {
        val blocks = File("inputs/13.txt").inputStream().bufferedReader().use { it.readText() }
            .split(doubleNewLines.toRegex()).map { it.split(newLines.toRegex()).filter { it.isNotBlank() } }

        println(blocks.sumOf { processBlock(it, 0) })
    }

    fun solve2() {
        val blocks = File("inputs/13.txt").inputStream().bufferedReader().use { it.readText() }
            .split(doubleNewLines.toRegex()).map { it.split(newLines.toRegex()).filter { it.isNotBlank() } }

        println(blocks.sumOf { processBlock(it, 1) })
    }

    fun processBlock(block: List<String>, mistakes: Int): Int {
        var verticalRes = 0
        var horizontalRes = 0

        for (i in 0..<block.size - 1) {
            val topSize = i + 1
            val botSize = block.size - topSize
            val topRange = if (topSize > botSize) IntRange(topSize - botSize, i) else IntRange(0, i)
            val botRange = if (topSize > botSize) IntRange(i + 1, block.size - 1) else IntRange(
                i + 1,
                block.size - 1 - (botSize - topSize)
            )

            val top = block.slice(topRange)
            val bot = block.slice(botRange)
            if (top.zip(bot.reversed())
                    .sumOf { (t, b) -> t.zip(b).sumOf { (a, b) -> if (a != b) 1L else 0L } } == mistakes.toLong()
            ) horizontalRes += 100 * (i + 1)
        }

        for (i in 0..<block[0].length - 1) {
            val leftSize = i + 1
            val rightSize = block[0].length - leftSize
            val leftRange = if (leftSize > rightSize) IntRange(leftSize - rightSize, i) else IntRange(0, i)
            val rightRange = if (leftSize > rightSize) IntRange(i + 1, block[0].length - 1) else IntRange(
                i + 1, block[0].length - 1 - (rightSize - leftSize)
            )
            val left = block.map { it.slice(leftRange) }
            val right = block.map { it.slice(rightRange) }

            if (left.zip(right)
                    .sumOf { (l, r) ->
                        l.zip(r.reversed()).sumOf { (a, b) -> if (a != b) 1L else 0L }
                    } == mistakes.toLong()
            ) verticalRes += i + 1
        }
        return verticalRes + horizontalRes
    }
}