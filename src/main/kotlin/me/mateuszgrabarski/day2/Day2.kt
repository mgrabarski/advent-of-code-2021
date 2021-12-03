package me.mateuszgrabarski.day2

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputData = readFile("day_2.txt")

    println(inputData)

    println("Part 1. ${part1(inputData)}") // 1804520
    println("Part 2. ${part2(inputData)}") // 1971095320
}

fun part1(inputData: List<String>): Int {
    var horizontal = 0
    var depth = 0

    inputData.forEach {
        val (direction, value) = it.split(" ")

        when (direction) {
            "forward" -> horizontal += value.toInt()
            "down" -> depth += value.toInt()
            "up" -> depth -= value.toInt()
        }
    }

    return horizontal * depth
}

fun part2(inputData: List<String>): Int {
    var horizontal = 0
    var depth = 0
    var aim = 0

    inputData.forEach {
        val (direction, value) = it.split(" ")

        when (direction) {
            "forward" -> {
                horizontal += value.toInt()
                depth += aim * value.toInt()
            }
            "down" -> aim += value.toInt()
            "up" -> aim -= value.toInt()
        }
    }

    return horizontal * depth
}