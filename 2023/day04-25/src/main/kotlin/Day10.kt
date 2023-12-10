import java.io.File
import java.util.LinkedList
import java.util.Queue

class Day10 {
    fun solve1() {
        val grid = File("inputs/10.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()

        var start = Pair(0, 0)
        grid.forEachIndexed { i, line -> val j = line.indexOf('S'); if (j != -1) start = Pair(i, j) else null }
        grid[start.first][start.second] = '|'
        val dist = bfs(grid, start, ::getNeighboursShape, emptySet())
        println(dist.values.max())
    }

    fun solve2() {
        val grid = File("inputs/10.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()

        var start = Pair(0, 0)
        grid.forEachIndexed { i, line -> val j = line.indexOf('S'); if (j != -1) start = Pair(i, j) else null }
        grid[start.first][start.second] = '|'
        val dist = bfs(grid, start, ::getNeighboursShape, emptySet())
        val loop = dist.keys.toSet()
        var res = 0
        for (i in 0..<grid.size) {
            var pos = Position.OUTSIDE
            for (j in 0..<grid[0].size) {
                if (loop.contains(Pair(i, j))) {
                    pos = when (pos) {
                        Position.OUTSIDE -> when (grid[i][j]) {
                            '|' -> Position.INSIDE
                            'F' -> Position.TOP_BORDER
                            'L' -> Position.BOT_BORDER
                            else -> pos
                        }

                        Position.TOP_BORDER -> when (grid[i][j]) {
                            'J' -> Position.INSIDE
                            '7' -> Position.OUTSIDE
                            else -> pos
                        }

                        Position.BOT_BORDER -> when (grid[i][j]) {
                            '7' -> Position.INSIDE
                            'J' -> Position.OUTSIDE
                            else -> pos
                        }

                        Position.INSIDE -> when (grid[i][j]) {
                            '|' -> Position.OUTSIDE
                            'F' -> Position.BOT_BORDER
                            'L' -> Position.TOP_BORDER
                            else -> pos
                        }
                    }
                } else {
                    if (pos == Position.INSIDE)
                        res++
                }
            }
        }
        println(res)
    }

    enum class Position {
        INSIDE, TOP_BORDER, BOT_BORDER, OUTSIDE
    }

    fun getNeighboursShape(grid: List<List<Char>>, pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val neighbours = emptyList<Pair<Int, Int>>().toMutableList()
        val shape = grid[pos.first][pos.second]
        when (shape) {
            '|' -> {
                neighbours.add(Pair(pos.first - 1, pos.second))
                neighbours.add(Pair(pos.first + 1, pos.second))
            }

            '-' -> {
                neighbours.add(Pair(pos.first, pos.second + 1))
                neighbours.add(Pair(pos.first, pos.second - 1))
            }

            'L' -> {
                neighbours.add(Pair(pos.first - 1, pos.second))
                neighbours.add(Pair(pos.first, pos.second + 1))
            }

            'J' -> {
                neighbours.add(Pair(pos.first - 1, pos.second))
                neighbours.add(Pair(pos.first, pos.second - 1))
            }

            '7' -> {
                neighbours.add(Pair(pos.first + 1, pos.second))
                neighbours.add(Pair(pos.first, pos.second - 1))
            }

            'F' -> {
                neighbours.add(Pair(pos.first + 1, pos.second))
                neighbours.add(Pair(pos.first, pos.second + 1))
            }

        }
        return neighbours.filter { it.first >= 0 && it.second >= 0 && it.first < grid.size && it.second < grid[0].size }
    }

    fun bfs(
        grid: List<List<Char>>,
        start: Pair<Int, Int>,
        getNeighbours: (List<List<Char>>, Pair<Int, Int>) -> List<Pair<Int, Int>>,
        skip: Set<Pair<Int, Int>>
    ): MutableMap<Pair<Int, Int>, Int> {
        val q: Queue<Pair<Int, Int>> = LinkedList()
        q.add(start)
        val dist = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
        dist[start] = 0
        while (!q.isEmpty()) {
            val cur = q.poll()

            val neighbours = getNeighbours(grid, cur)


            for (next in neighbours) {
                if (next == start || dist.containsKey(next) || skip.contains(next)) continue
                dist[next] = dist.getValue(cur) + 1
                q.add(next)
            }

        }
        return dist
    }
}