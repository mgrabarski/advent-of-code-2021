package me.mateuszgrabarski.day4

import me.mateuszgrabarski.utils.readFile

fun main() {
    val inputValues = readFile("day_4.txt")

    val randomValues = inputValues[0].split(",").map { it.toInt() }
    println("random values: $randomValues")

    val boards = loadBoards(inputValues.subList(2, inputValues.size))

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

fun loadBoards(inputs: List<String>): List<BingoBoard> {
    val boards = mutableListOf<BingoBoard>()
    var currentBoard = mutableListOf<Int>()
    inputs.forEach { row ->
        println(row)
        if (row.isEmpty()) {
            boards.add(BingoBoard(currentBoard))
            currentBoard = mutableListOf()
        } else {
            val rowValues = row.trim().split(Regex("\\s+")).map { it.toInt() }
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
        return checkIsWinner()
    }

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