package me.mateuszgrabarski.day6

import me.mateuszgrabarski.utils.readFile

fun main() {
    val input = readFile("day_6.txt")[0].split(",")
    val inputValues = input.map { it.toLong() }.toMutableList()
    println("Initial state: $inputValues")

    val days = 256

    for (i in 0 until days) {
        val temp = inputValues.toMutableList()
        temp.forEachIndexed { index, value ->
            when (value) {
                0L -> {
                    inputValues[index] = 6
                    inputValues.add(8)
                }
                else -> {
                    inputValues[index]--
                }
            }
        }
        println("After  ${i + 1} ")
    }

    println("Sum: ${inputValues.size}")
}