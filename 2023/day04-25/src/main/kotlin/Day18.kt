import java.io.File
import kotlin.math.abs

class Day18 {
    fun solve1() {
        val input = File("inputs/18.txt").inputStream().bufferedReader().lineSequence().toList().map {
            val split = it.split(' ')
            val direction = when (split[0][0]) {
                'R' -> Direction.RIGHT
                'D' -> Direction.DOWN
                'L' -> Direction.LEFT
                'U' -> Direction.UP
                else -> throw Exception()
            }
            Instruction(direction, split[1].toLong())
        }

        var curPoint = Point(0, 0)
        val vertices = listOf(Vertex(input[0].direction, Point(0, 0))).toMutableList()
        input.forEach { instruction ->

            curPoint += when (instruction.direction) {
                Direction.RIGHT -> Point(0, 1) * instruction.distance
                Direction.DOWN -> Point(1, 0) * instruction.distance
                Direction.LEFT -> Point(0, -1) * instruction.distance
                Direction.UP -> Point(-1, 0) * instruction.distance
            }
            vertices.add(Vertex(instruction.direction, curPoint))
        }
        val area = calcArea(vertices)
        val perimeter = calcPerimeter(vertices)
        println(area + perimeter / 2 + 1)
    }

    fun solve2() {
        val input = File("inputs/18.txt").inputStream().bufferedReader().lineSequence().toList().map {
            val split = it.split(' ')
            val color = split[2].drop(1).dropLast(1)
            val direction = when (color.last()) {
                '0' -> Direction.RIGHT
                '1' -> Direction.DOWN
                '2' -> Direction.LEFT
                '3' -> Direction.UP
                else -> throw Exception()
            }
            val distance = color.drop(1).dropLast(1).toLong(16)
            Instruction(direction, distance)
        }

        var curPoint = Point(0, 0)
        val vertices = listOf(Vertex(input[0].direction, Point(0, 0))).toMutableList()
        input.forEach { instruction ->

            curPoint += when (instruction.direction) {
                Direction.RIGHT -> Point(0, 1) * instruction.distance
                Direction.DOWN -> Point(1, 0) * instruction.distance
                Direction.LEFT -> Point(0, -1) * instruction.distance
                Direction.UP -> Point(-1, 0) * instruction.distance
            }
            vertices.add(Vertex(instruction.direction, curPoint))
        }
        val area = calcArea(vertices)
        val perimeter = calcPerimeter(vertices)
        println(area.toLong() + perimeter / 2 + 1)
    }

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }

        operator fun times(scale: Long): Point {
            return Point(x * scale, y * scale)
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }


    data class Instruction(val direction: Direction, val distance: Long)
    data class Vertex(val direction: Direction, val point: Point)

    fun calcArea(vertices: List<Vertex>): Double {
        var area = 0.0
        for (i in vertices.indices) {
            val p = if (i == 0) vertices.last.point else vertices[i - 1].point
            val q = vertices[i].point
            area += (p.x - q.x) * (p.y + q.y)
        }
        return abs(area / 2.0)//.toInt()
    }

    fun calcPerimeter(vertices: List<Vertex>): Long {
        var perimeter = 0L

        for (i in 1..<vertices.size) {
            perimeter += abs(vertices[i - 1].point.x - vertices[i].point.x) + abs(vertices[i - 1].point.y - vertices[i].point.y)
        }
        return perimeter
    }
}