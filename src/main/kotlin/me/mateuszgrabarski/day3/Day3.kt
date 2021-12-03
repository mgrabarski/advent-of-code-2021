package me.mateuszgrabarski.day3

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputValues = readFile("day_3.txt")

    println("Part 1")
    part1(inputValues)

    println("Part 2")

    var oxygenList = inputValues.map { it.toBitsList() }
    println(oxygenList)

    val mostCommonBits = mostCommonBits(oxygenList)
    println("Most common bits: $mostCommonBits")

    var oxygenIdx = 0
    while (oxygenList.size > 1) {
        println("oxygen index: $oxygenIdx, common bit: ${mostCommonBits[oxygenIdx]}")
        oxygenList = if (oxygenList.size == 2) {
            if (oxygenList[0][oxygenIdx] == 1 && oxygenList[1][oxygenIdx] == 0) {
                oxygenList.filter { it[oxygenIdx] == 1 }
            } else {
                oxygenList.filter { it[oxygenIdx] == mostCommonBits[oxygenIdx] }
            }
        } else {
            oxygenList.filter { it[oxygenIdx] == mostCommonBits[oxygenIdx] }
        }
        oxygenIdx += 1
    }
    println("oxygen: $oxygenList, ${oxygenList.first().bitListToNumber()}")

    var scrubberList = inputValues.map { it.toBitsList() }
    var scrubberIdx = 0
    while (scrubberList.size > 1) {
        scrubberList = scrubberList.filter { it[scrubberIdx] != mostCommonBits[scrubberIdx] }
        scrubberIdx += 1
    }
    println("scrubber: $scrubberList, ${scrubberList.first().bitListToNumber()}")

    val lifeSupportRating = oxygenList.first().bitListToNumber() * scrubberList.first().bitListToNumber()
    println(lifeSupportRating)
}

private fun part1(values: List<String>) {
    val listOfBits = values.map { it.toBitsList() }

    val mostCommonBits = mostCommonBits(listOfBits)
    println(mostCommonBits)

    val gammaRate = mostCommonBits.bitListToNumber()
    println(gammaRate)

    val epsilonBits = mostCommonBits.map { bit -> 1 - bit }
    println(epsilonBits)

    val epsilonRate = epsilonBits.bitListToNumber()
    println(epsilonRate)

    val powerConsumption = gammaRate * epsilonRate
    println(powerConsumption)
}

fun String.toBitsList(): List<Int> = toList().map { it.toString().toInt() }

fun addLists(l1: List<Int>, l2: List<Int>): List<Int> = l1.zip(l2).map { it.first + it.second }

fun mostCommonBits(input: List<List<Int>>): List<Int> =
    input
        .reduce(::addLists)
        .map { bit ->
            if (bit.toDouble() / input.size >= 0.5) 1 else 0
        }

fun List<Int>.bitListToNumber(): Int = reduce { acc, bit -> acc * 2 + bit }

