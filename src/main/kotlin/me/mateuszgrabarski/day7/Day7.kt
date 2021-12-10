package me.mateuszgrabarski.day7

import me.mateuszgrabarski.utils.readFile
import kotlin.math.absoluteValue

fun main() {
    val input = readFile("day_7.txt")[0].split(",").map { it.toInt() }

    val sorted = input.sorted()
    val size = sorted.size
    val isEven = size % 2 == 0
    val median = median(isEven, size, sorted)

    val result = sorted.sumOf { position -> (position - median).absoluteValue }
    println(result)
}

private fun median(isEven: Boolean, size: Int, numbers: List<Int>) =
    if (isEven) {
        val middle = size / 2
        (numbers[middle - 1] + numbers[middle]) / 2
    } else {
        numbers[(size + 1) / 2]
    }