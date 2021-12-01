package me.mateuszgrabarski.day1

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputData = readFile("day_1.txt").map { it.toInt() }
    println(inputData)

    println("Part 1: ${calculate(inputData)}")

    val summarizedData = inputData.windowed(3).map { it.sum() }

    print("Part 2: ${calculate(summarizedData)}")
}

private fun calculate(inputData: List<Int>): Int {
    var result = 0

    inputData.windowed(2).forEach { (a, b) ->
        if (a < b) {
            result++
        }
    }
    return result
}
