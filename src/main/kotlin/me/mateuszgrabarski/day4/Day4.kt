package me.mateuszgrabarski.day4

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputValues = readFile("day_4.txt")

    val randomValues = inputValues[0].split(",").map { it.toInt() }
    println("random values: $randomValues")

    val boards = loadBoards(inputValues.subList(2, inputValues.size)).toMutableList()

    part1(randomValues, boards)
    part2(boards, randomValues)
}

private fun part1(
    randomValues: List<Int>,
    boards: List<BingoBoard>
) {
    println("Part 1")
    randomValues.forEach { randomNumber ->
        boards.forEach {
            val winner = it.markNumber(randomNumber)
            if (winner) {
                println("Winner: $randomNumber, $winner, $it")
                val result = it.score(randomNumber)
                println(result)
                return
            }
        }
    }
}

private fun part2(
    boards: MutableList<BingoBoard>,
    randomValues: List<Int>
) {
    println("Part 2")
    var index = -1
    while (boards.size > 1) {
        index++
        boards.forEach { it.markNumber(randomValues[index]) }
        boards.removeIf { it.isWinner() }
    }

    val lastWinBoard = boards.first()
    println(lastWinBoard)
    while (!lastWinBoard.isWinner()) {
        index++
        lastWinBoard.markNumber(randomValues[index])
    }

    println(lastWinBoard.score(randomValues[index]))
}

fun loadBoards(inputs: List<String>): List<BingoBoard> {
    val boards = mutableListOf<BingoBoard>()
    var currentBoard = mutableListOf<Int>()
    inputs.forEach { row ->
        if (row.isEmpty()) {
            boards.add(BingoBoard(currentBoard))
            currentBoard = mutableListOf()
        } else {
            val rowValues = row.trim().split("\\s+".toRegex()).map { it.toInt() }
            currentBoard.addAll(rowValues)
        }
    }
    boards.add(BingoBoard(currentBoard))
    return boards
}

data class BingoBoard(
    private val values: List<Int>
) {

    private val SIZE = 5

    private val marks: BooleanArray = BooleanArray(values.size)

    private var winner = false

    fun score(winNumber: Int): Int {
        val filledMap = mappedCells()
        val notMarkedCells = mutableListOf<CellStatus>()
        filledMap.forEach { row ->
            row.forEach {
                if (!it.mark) {
                    notMarkedCells.add(it)
                }
            }
        }

        val sumUnMarkedNumbers = notMarkedCells.sumOf { it.value }
        return sumUnMarkedNumbers * winNumber
    }

    fun markNumber(randomNumber: Int): Boolean {
        val index = values.indexOf(randomNumber)
        if (index >= 0) {
            marks[index] = true
        }
        winner = checkIsWinner()
        return winner
    }

    fun isWinner() = winner

    private fun checkIsWinner(): Boolean {
        val filledMap = mappedCells()

        filledMap.forEach { row ->
            if (checkMarks(row)) {
                return true
            }
        }

        (0 until SIZE).forEach { column ->
            val columnCells = mutableListOf<CellStatus>()
            filledMap.forEach { row ->
                columnCells.add(row.find { cell -> cell.column == column }!!)
            }
            if (checkMarks(columnCells)) {
                return true
            }
        }

        return false
    }

    private fun mappedCells(): List<List<CellStatus>> {
        val filledMap = (0 until SIZE).map { row ->
            (0 until SIZE).map { column ->
                val status = CellStatus(row, column, marks[SIZE * row + column], values[SIZE * row + column])
                status
            }
        }
        return filledMap
    }

    private fun checkMarks(cells: List<CellStatus>): Boolean = cells.filter { it.mark }.size == SIZE

    private data class CellStatus(
        val row: Int,
        val column: Int,
        val mark: Boolean,
        val value: Int
    )

    override fun toString(): String {
        return "BingoBoard(values=$values,\nmarks=${marks.contentToString()})"
    }
}