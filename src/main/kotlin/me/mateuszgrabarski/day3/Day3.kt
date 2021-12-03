package me.mateuszgrabarski.day3

import me.mateuszgrabarski.utils.readFile
import kotlin.math.pow

fun main() {
    val values = readFile("day_3.txt")

    values.forEach {
        val value = it.toDecimal()
        val bits = it.toBitsList()

        println("binary: $it, bits: $bits, decimal: $value")
    }

    println("====")

    val listOfBits = mutableListOf<List<Int>>()

    values.forEach {
        listOfBits.add(it.toBitsList())
    }

    val mostCommonBits = mostCommonBits(listOfBits)
    println(mostCommonBits)

    val gammaRate = mostCommonBits.bitListToNumber() // 22

    println(gammaRate)

    val epsilonBits = mostCommonBits.map { bit -> 1 - bit }

    println(epsilonBits)

    val epsilonRate = epsilonBits.bitListToNumber()

    println(epsilonRate)

    val powerConsumption = gammaRate * epsilonRate
    println(powerConsumption)
}

fun String.toBitsList(): List<Int> {
    return toList().map { it.toString().toInt() }
}

fun String.toDecimal(): Int {
    var sum = 0
    reversed().forEachIndexed { k, v ->
        sum += v.toString().toInt() * 2.0.pow(k).toInt()
    }
    return sum
}

fun addLists(l1: List<Int>, l2: List<Int>): List<Int> = l1.zip(l2).map { it.first + it.second }

fun mostCommonBits(input: List<List<Int>>): List<Int> =
    input
        .reduce(::addLists)
        .map { bit ->
            if (bit.toDouble() / input.size >= 0.5) 1 else 0
        }

fun List<Int>.bitListToNumber(): Int {
    return reduce { acc, bit -> acc * 2 + bit }
}

