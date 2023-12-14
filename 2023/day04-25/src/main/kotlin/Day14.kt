import java.io.File

class Day14 {
    fun solve1() {
        val grid = File("inputs/14.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()


        var res = 0
        for (col in 0..<grid[0].size) {
            for (stoneRow in 0..<grid.size) {
                if (grid[stoneRow][col] != 'O') continue
                grid[stoneRow][col] = '.'
                var found = false
                for (row in stoneRow downTo 1) {
                    if (grid[row - 1][col] != '.') {
                        grid[row][col] = 'O'
                        res += grid.size - row
                        found = true
                        break
                    }
                }
                if (!found) {
                    grid[0][col] = 'O'
                    res += grid.size
                }
            }
        }
        println(res)
    }


    fun solve2() {
        val grid = File("inputs/14.txt").inputStream().bufferedReader().lineSequence().map { it.toMutableList() }
            .toMutableList()
        var cycle = 0
        val initialGrid = grid.map { x -> x.toMutableList() }.toMutableList()
        val prevGrids = mutableListOf(initialGrid.toTypedArray())
        var cycleOffset = 0
        var cycleLen = 0
        while (true) {
            cycle += 1

            tiltNorth(grid)
            tiltWest(grid)
            tiltSouth(grid)
            tiltEast(grid)

            var i = 0
            var found = false
            for (prevGrid in prevGrids) {
                if (prevGrid.contentDeepEquals(grid.toTypedArray())) {
                    cycleOffset = i
                    cycleLen = cycle - i
                    found = true
                    break
                }
                i += 1
            }
            if (found) break
            prevGrids.add(grid.map { x -> x.toMutableList() }.toTypedArray())
        }

        val iterations = cycleOffset + (1000000000 - cycleOffset.toLong()) % cycleLen
        for (i in 0..<iterations) {
            tiltNorth(initialGrid)
            tiltWest(initialGrid)
            tiltSouth(initialGrid)
            tiltEast(initialGrid)
        }
        println(countRes(initialGrid))
    }


    fun countRes(grid: MutableList<MutableList<Char>>): Long {
        var res = 0L
        for (row in 0..<grid.size) {
            res += grid[row].count { it == 'O' }.toLong() * (grid.size - row)
        }
        return res
    }

    fun tiltNorth(grid: MutableList<MutableList<Char>>) {
        for (col in 0..<grid[0].size) {
            for (stoneRow in 0..<grid.size) {
                if (grid[stoneRow][col] != 'O') continue
                grid[stoneRow][col] = '.'
                var found = false
                for (row in stoneRow downTo 1) {
                    if (grid[row - 1][col] != '.') {
                        grid[row][col] = 'O'
                        found = true
                        break
                    }
                }
                if (!found) {
                    grid[0][col] = 'O'
                }
            }
        }
    }

    fun tiltSouth(grid: MutableList<MutableList<Char>>) {
        for (col in 0..<grid[0].size) {
            for (stoneRow in grid.size - 1 downTo 0) {
                if (grid[stoneRow][col] != 'O') continue
                grid[stoneRow][col] = '.'
                var found = false
                for (row in stoneRow..<grid.size - 1) {
                    if (grid[row + 1][col] != '.') {
                        grid[row][col] = 'O'
                        found = true
                        break
                    }
                }
                if (!found) {
                    grid.last()[col] = 'O'
                }
            }
        }
    }


    fun tiltEast(grid: MutableList<MutableList<Char>>) {
        for (row in 0..<grid.size) {
            for (stoneCol in grid[0].size - 1 downTo 0) {
                if (grid[row][stoneCol] != 'O') continue
                grid[row][stoneCol] = '.'
                var found = false
                for (col in stoneCol..<grid[0].size - 1) {
                    if (grid[row][col + 1] != '.') {
                        grid[row][col] = 'O'
                        found = true
                        break
                    }
                }
                if (!found) {
                    grid[row][grid[row].size - 1] = 'O'
                }
            }
        }
    }

    fun tiltWest(grid: MutableList<MutableList<Char>>) {
        for (row in 0..<grid.size) {
            for (stoneCol in 0..<grid[0].size) {
                if (grid[row][stoneCol] != 'O') continue
                grid[row][stoneCol] = '.'
                var found = false
                for (col in stoneCol downTo 1) {
                    if (grid[row][col - 1] != '.') {
                        grid[row][col] = 'O'
                        found = true
                        break
                    }
                }
                if (!found) {
                    grid[row][0] = 'O'
                }
            }
        }
    }
}
