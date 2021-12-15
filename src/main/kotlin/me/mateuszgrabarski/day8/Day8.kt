package me.mateuszgrabarski.day8

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputs = readFile("day_8.txt").map { line ->
        val (patterns, output) = line.split(" | ")
        Entry(
            patterns.split(" ").map { it.toSet() },
            output.split(" ").map { it.toSet() }
        )
    }

    inputs.forEach {
        println(it)
    }

    println()

    val validLengths = listOf(2, 4, 3, 7)
    val result = inputs.map { it.output }
        .flatten()
        .count { output -> output.size in validLengths }

    println("Part 1: $result")
}

private data class Entry(
    val patterns: List<Set<Char>>,
    val output: List<Set<Char>>
)