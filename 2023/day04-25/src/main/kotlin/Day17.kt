import java.io.File
import java.util.PriorityQueue

class Day17 {
    fun solve1() {
        val grid = File("inputs/17.txt").inputStream().bufferedReader().lineSequence()
            .map { it.map { x -> x.digitToInt() }.toList() }.toList()
        val res = dijkstra1(grid, Point(0, 0))
        println(res.filter { it.key.point == Point(grid.size - 1, grid[0].size - 1) }.minOf { it.value })
    }

    fun solve2() {
        val grid = File("inputs/17.txt").inputStream().bufferedReader().lineSequence()
            .map { it.map { x -> x.digitToInt() }.toList() }.toList()
        val res = dijkstra2(grid, Point(0, 0))
        println(res.filter { it.key.point == Point(grid.size - 1, grid[0].size - 1) && it.key.sameDirectionCnt >= 4 }
            .minOf { it.value })
    }

    data class Node(val point: Point, val direction: Point, val sameDirectionCnt: Int)


    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }
    }

    operator fun List<List<Int>>.get(p: Point): Int {
        return this[p.x][p.y]
    }

    fun dijkstra1(grid: List<List<Int>>, startPoint: Point): MutableMap<Node, Int> {
        val compareByWeight: Comparator<Pair<Int, Node>> = compareBy { it.first }
        val q = PriorityQueue(compareByWeight)
        val dist = emptyMap<Node, Int>().toMutableMap()
        val startRight = Node(startPoint, Point(0, 1), 0)
        val startDown = Node(startPoint, Point(1, 0), 0)
        dist[startRight] = 0
        dist[startDown] = 0
        q.add(Pair(0, startRight))
        q.add(Pair(0, startDown))
        while (!q.isEmpty()) {
            val cur = q.poll().second

            for (direction in listOf(
                cur.direction, Point(
                    cur.direction.y, cur.direction.x
                ), Point(cur.direction.y * -1, cur.direction.x * -1)
            )) {
                val newPoint = cur.point + direction
                var directionCnt = 1
                if (direction == cur.direction) {
                    if (cur.sameDirectionCnt == 3) continue
                    directionCnt = cur.sameDirectionCnt + 1
                }
                if (!(newPoint.x >= 0 && newPoint.y >= 0 && newPoint.x < grid.size && newPoint.y < grid[0].size)) {
                    continue
                }
                val node = Node(newPoint, direction, directionCnt)
                val curDistToNode = dist.getOrDefault(node, Int.MAX_VALUE)
                val updDistToNode = dist.getValue(cur) + grid[node.point]
                if (curDistToNode > updDistToNode) {
                    q.add(Pair(updDistToNode, node))
                    dist[node] = updDistToNode
                }
            }
        }
        return dist
    }


    fun dijkstra2(grid: List<List<Int>>, startPoint: Point): MutableMap<Node, Int> {
        val compareByWeight: Comparator<Pair<Int, Node>> = compareBy { it.first }
        val q = PriorityQueue(compareByWeight)
        val dist = emptyMap<Node, Int>().toMutableMap()
        val startRight = Node(startPoint, Point(0, 1), 0)
        val startDown = Node(startPoint, Point(1, 0), 0)
        dist[startRight] = 0
        dist[startDown] = 0
        q.add(Pair(0, startRight))
        q.add(Pair(0, startDown))
        while (!q.isEmpty()) {
            val cur = q.poll().second

            val directions = emptyList<Point>().toMutableList()
            if (cur.sameDirectionCnt == 10) {
                directions.add(Point(cur.direction.y, cur.direction.x))
                directions.add(Point(cur.direction.y * -1, cur.direction.x * -1))
            } else if (cur.sameDirectionCnt < 4) {
                directions.add(cur.direction)
            } else {
                directions.add(cur.direction)
                directions.add(Point(cur.direction.y, cur.direction.x))
                directions.add(Point(cur.direction.y * -1, cur.direction.x * -1))
            }

            for (direction in directions) {
                val newPoint = cur.point + direction
                var directionCnt = 1
                if (direction == cur.direction) {
                    directionCnt = cur.sameDirectionCnt + 1
                }
                if (!(newPoint.x >= 0 && newPoint.y >= 0 && newPoint.x < grid.size && newPoint.y < grid[0].size)) {
                    continue
                }
                val node = Node(newPoint, direction, directionCnt)
                val curDistToNode = dist.getOrDefault(node, Int.MAX_VALUE)
                val updDistToNode = dist.getValue(cur) + grid[node.point]
                if (curDistToNode > updDistToNode) {
                    q.add(Pair(updDistToNode, node))
                    dist[node] = updDistToNode
                }
            }
        }
        return dist
    }
}