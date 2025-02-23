package board

import board.Direction.*

open class SquareBoardImp(override val width: Int) : SquareBoard {
    private val cells = mutableListOf<Cell>()

    open fun init() {
        repeat(width * width) { cells.add(Cell((it / width) + 1, (it % width) + 1)) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.firstOrNull { it == Cell(i, j) }

    override fun getCell(i: Int, j: Int): Cell =
        if (Cell(i, j) in cells) cells.first { it == Cell(i, j) }
        else throw IllegalArgumentException()

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = cells
        .filter { cell -> cell.i == i && cell.j in jRange }
        .sortedWith(if (jRange.first < jRange.last) compareBy { it.j } else compareByDescending { it.j })

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = cells
        .filter { cell -> cell.i in iRange && cell.j == j }
        .sortedWith(if (iRange.first < iRange.last) compareBy { it.i } else compareByDescending { it.i })

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        UP -> getCellOrNull(i - 1, j)
        DOWN -> getCellOrNull(i + 1, j)
        LEFT -> getCellOrNull(i, j - 1)
        RIGHT -> getCellOrNull(i, j + 1)
    }

    override fun toString(): String = "[${cells.joinToString(", ") { it.toString() }}]"
}

class GameBoardImp<T>(override val width: Int) : SquareBoardImp(width), GameBoard<T> {
    private val values = mutableMapOf<Cell, T?>()

    override fun init() {
        super.init()
        getAllCells().forEach { values[it] = null }
    }

    override fun get(cell: Cell): T? {
        return values.getValue(cell)
    }

    override fun set(cell: Cell, value: T?) {
        if (value == null) return
        this.values[getCell(cell.i, cell.j)] = value

    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = values
        .filter { predicate(it.value) }
        .map { it.key }

    override fun find(predicate: (T?) -> Boolean): Cell? = values
        .entries
        .find { predicate(it.value) }
        ?.key

    override fun any(predicate: (T?) -> Boolean): Boolean = values
        .entries
        .any { predicate(it.value) }

    override fun all(predicate: (T?) -> Boolean): Boolean = values
        .entries
        .all { predicate(it.value) }
}

fun createSquareBoard(width: Int): SquareBoard {
    val squareBoard = SquareBoardImp(width)
    squareBoard.init()
    return squareBoard
}

fun <T> createGameBoard(width: Int): GameBoard<T> {
    val gameBoard = GameBoardImp<T>(width)
    gameBoard.init()
    return gameBoard
}

fun main() {
    val squareBoard = createSquareBoard(4)
    println(squareBoard.toString())

    val gameBoard = createGameBoard<Int>(2)
    println(gameBoard.toString())
}
