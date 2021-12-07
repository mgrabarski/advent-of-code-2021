package me.mateuszgrabarski.day5

import me.mateuszgrabarski.utils.readFile
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val inputValues = readFile("day_5.txt")

    val lines = mutableListOf<Line>()
    inputValues.forEach { line ->
        val points = line.split(" -> ")
        val (x1, y1) = points[0].split(",").map { it.toInt() }
        val (x2, y2) = points[1].split(",").map { it.toInt() }
        lines.add(Line(from = Point(x1, y1), to = Point(x2, y2), true))
    }

    val part1 = getOverlappingLines(lines)
    println("\n$part1")
}

private fun getOverlappingLines(lines: List<Line>): Int {
    val allPoints = List(1000) { IntArray(1000).toMutableList() }

    lines.forEach { line ->
        line.draw(allPoints)
    }

//    Line(Point(1, 1), Point(3, 3), true).draw(allPoints)

//    printAllPoints(allPoints)

    return allPoints.sumOf { it.count { i -> i > 1 } }
}

private fun printAllPoints(map: List<List<Int>>) {
    map.forEach { line ->
        println()
        line.forEach {
            print(it)
        }
    }
}

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val from: Point,
    val to: Point,
    val diagonal: Boolean = false
) {

    fun draw(map: List<MutableList<Int>>) {
        println("draw: $this")
        val dx = to.x - from.x
        val dy = to.y - from.y
        println("draw: dx:$dx, dy:$dy")
        for (i in 0..maxOf(abs(dx), abs(dy))) {
            val x = from.x + i * dx.sign
            val y = from.y + i * dy.sign
            print("(x:$x,y:$y),")
            if (diagonal)
                map[x][y]++
            else if (dx == 0 || dy == 0) {
                map[y][x]++
            }
        }
        println()
    }
}