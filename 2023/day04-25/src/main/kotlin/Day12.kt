import java.io.File

class Day12 {
    fun solve1() {
        val lines =
            File("inputs/12.txt").inputStream().bufferedReader().lineSequence().map { processLine1(it) }.toList()
        println(lines.sum())
    }

    fun solve2() {
        val lines = File("inputs/12.txt").inputStream().bufferedReader().lineSequence().map {
            processLine2(it)
        }.toList()
        println(lines.sum())
    }

    fun processLine1(line: String): Int {
        val split = line.split(' ')
        val data = split[0].toMutableList()
        val blockSizes = split[1].split(',').map { it.toInt() }
        return processLineHelp(data, blockSizes, 0, 0, 0, emptyMap<CacheEntity, Long>().toMutableMap()).toInt()
    }

    fun processLine2(line: String): Long {
        val split = line.split(' ')
        val data = (split[0] + "?").repeat(5).dropLast(1).toMutableList()
        val blockSizes = List(5) { split[1].split(',').map { it.toInt() } }.flatten()
        return processLineHelp(data, blockSizes, 0, 0, 0, emptyMap<CacheEntity, Long>().toMutableMap())
    }

    data class CacheEntity(val pos: Int, val block: Int, val curBlockLen: Int)

    fun processLineHelp(
        data: List<Char>,
        blockSizes: List<Int>,
        pos: Int,
        block: Int,
        curBlockLen: Int,
        memo: MutableMap<CacheEntity, Long>
    ): Long {
        val entity = CacheEntity(pos, block, curBlockLen)
        if (memo.containsKey(entity)) return memo.getValue(entity)
        if (pos == data.size) {
            if (block == blockSizes.size - 1 && blockSizes[block] == curBlockLen) return 1
            if (block == blockSizes.size && curBlockLen == 0) return 1
            return 0
        }
        var resDot = 0L
        var resSharp = 0L
        if (data[pos] == '.' || data[pos] == '?') {
            if (curBlockLen == 0) resDot = processLineHelp(data, blockSizes, pos + 1, block, 0, memo)
            else if (block < blockSizes.size && curBlockLen == blockSizes[block]) resDot =
                processLineHelp(data, blockSizes, pos + 1, block + 1, 0, memo)
        }
        if (data[pos] == '#' || data[pos] == '?') {
            resSharp = processLineHelp(data, blockSizes, pos + 1, block, curBlockLen + 1, memo)
        }

        memo[entity] = resDot + resSharp
        return resDot + resSharp
    }
}