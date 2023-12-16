import java.io.File

class Day16 {
    fun solve1() {
        val grid = File("inputs/16.txt").inputStream().bufferedReader().lineSequence().map { it.toList() }.toList()
        println(calcEnergy(grid, Point(0, 0), Point(0, 1)))
    }


    fun solve2() {
        val grid = File("inputs/16.txt").inputStream().bufferedReader().lineSequence().map { it.toList() }.toList()

        val starts = emptyList<Pair<Point, Point>>().toMutableList()
        for (i in grid.indices) {
            starts.add(Pair(Point(i, 0), Point(0, 1)))
            starts.add(Pair(Point(i, grid[0].size - 1), Point(0, -1)))
        }

        for (i in grid[0].indices) {
            starts.add(Pair(Point(0, i), Point(1, 0)))
            starts.add(Pair(Point(grid.size - 1, i), Point(-1, 0)))
        }
        println(starts.maxOf { calcEnergy(grid, it.first, it.second) })
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }
    }

    operator fun List<List<Char>>.get(p: Point): Char {
        return this[p.x][p.y]
    }

    fun calcEnergy(grid: List<List<Char>>, initialPos: Point, initialDirection: Point): Int {
        val energized = emptySet<Point>().toMutableSet()
        val memo = emptySet<Pair<Point, Point>>().toMutableSet()
        fun moveBeam(grid: List<List<Char>>, pos: Point, direction: Point) {
            if (memo.contains(Pair(pos, direction)))
                return
            memo.add(Pair(pos, direction))
            var curPos = pos
            var curDirection = direction
            while (curPos.x >= 0 && curPos.y >= 0 && curPos.x < grid.size && curPos.y < grid[0].size) {
                energized.add(curPos)
                if (grid[curPos] == '/') {
                    curDirection = Point(curDirection.y * -1, curDirection.x * -1)
                } else if (grid[curPos] == '\\') {
                    curDirection = Point(curDirection.y, curDirection.x)
                } else if (grid[curPos] == '|') {
                    if (curDirection.y != 0) {
                        moveBeam(grid, curPos + Point(-1, 0), Point(-1, 0))
                        moveBeam(grid, curPos + Point(1, 0), Point(1, 0))
                        break
                    }
                } else if (grid[curPos] == '-') {
                    if (curDirection.x != 0) {
                        moveBeam(grid, curPos + Point(0, 1), Point(0, 1))
                        moveBeam(grid, curPos + Point(0, -1), Point(0, -1))
                        break
                    }
                }
                curPos += curDirection
            }
        }
        moveBeam(grid, initialPos, initialDirection)
        return energized.size
    }
}