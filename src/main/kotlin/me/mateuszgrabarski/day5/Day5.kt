package me.mateuszgrabarski.day5

import me.mateuszgrabarski.utils.readFile
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val inputValues = readFile("day_5.txt")

    println("Part 1: ${getOverlappingLines(loadLines(inputValues, false))}")
    println("Part 2: ${getOverlappingLines(loadLines(inputValues, true))}")
}

private fun loadLines(
    inputValues: List<String>,
    diagonal: Boolean
): MutableList<Line> {
    val lines = mutableListOf<Line>()
    inputValues.forEach { line ->
        val points = line.split(" -> ")
        val (x1, y1) = points[0].split(",").map { it.toInt() }
        val (x2, y2) = points[1].split(",").map { it.toInt() }
        lines.add(Line(from = Point(x1, y1), to = Point(x2, y2), diagonal))
    }
    return lines
}

private fun getOverlappingLines(lines: List<Line>): Int {
    val allPoints = List(1000) { IntArray(1000).toMutableList() }
    lines.forEach { line ->
        line.draw(allPoints)
    }
    return allPoints.sumOf { it.count { i -> i > 1 } }
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
        val dx = to.x - from.x
        val dy = to.y - from.y
        for (i in 0..maxOf(abs(dx), abs(dy))) {
            val x = from.x + i * dx.sign
            val y = from.y + i * dy.sign
            if (diagonal)
                map[x][y]++
            else if (dx == 0 || dy == 0) {
                map[y][x]++
            }
        }
    }
}