import java.io.File
import java.math.BigInteger
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 {

    fun solve1() {
        val grid = File("inputs/11.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()

        val rowsWithGalaxy = (0..<grid.count()).toSortedSet()
        val colsWithGalaxy = (0..<grid.count()).toSortedSet()
        val galaxies = emptySet<Point>().toMutableSet()
        for (i in 0..<grid.count()) {
            for (j in 0..<grid.count()) {
                if (grid[i][j] == '#') {
                    rowsWithGalaxy.remove(i)
                    colsWithGalaxy.remove(j)
                    galaxies.add(Point(i, j))
                }
            }
        }
        var res = 0L
        for (g1 in galaxies) for (g2 in galaxies) {
            var dist = abs(g1.x - g2.x) + abs(g1.y - g2.y)
            val minX = min(g1.x, g2.x)
            val maxX = max(g1.x, g2.x)
            val minY = min(g1.y, g2.y)
            val maxY = max(g1.y, g2.y)
            dist += rowsWithGalaxy.subSet(minX, maxX).count()
            dist += colsWithGalaxy.subSet(minY, maxY).count()
            res += dist
        }
        println(res / 2)
    }

    fun solve2() {
        val grid = File("inputs/11.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()

        val rowsWithGalaxy = (0..<grid.count()).toSortedSet()
        val colsWithGalaxy = (0..<grid.count()).toSortedSet()
        val galaxies = emptySet<Point>().toMutableSet()
        for (i in 0..<grid.count()) {
            for (j in 0..<grid.count()) {
                if (grid[i][j] == '#') {
                    rowsWithGalaxy.remove(i)
                    colsWithGalaxy.remove(j)
                    galaxies.add(Point(i, j))
                }
            }
        }
        var res = BigInteger.ZERO
        val resizeConst = 1000000.toBigInteger()
        for (g1 in galaxies) for (g2 in galaxies) {
            val minX = min(g1.x, g2.x)
            val maxX = max(g1.x, g2.x)
            val minY = min(g1.y, g2.y)
            val maxY = max(g1.y, g2.y)
            val rowsCnt = rowsWithGalaxy.subSet(minX, maxX).count().toBigInteger()
            val colsCnt = colsWithGalaxy.subSet(minY, maxY).count().toBigInteger()
            var dist = ((abs(g1.x - g2.x) + abs(g1.y - g2.y)).toBigInteger() - rowsCnt - colsCnt)

            dist += BigInteger.ZERO.max((rowsCnt * resizeConst))
            dist += BigInteger.ZERO.max((colsCnt * resizeConst))
            res += dist
        }
        println(res / 2.toBigInteger())
    }

    data class Point(val x: Int, val y: Int)

}