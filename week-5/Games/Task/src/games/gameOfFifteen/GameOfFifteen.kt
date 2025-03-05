package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)


    override fun initialize() {
        println(initializer.initialPermutation)

        board.getAllCells().forEachIndexed { i, cell ->
            if (i < initializer.initialPermutation.size) {
                board[cell] = initializer.initialPermutation[i]
            } else {
                board[cell] = null
            }
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon(): Boolean {
        val values = board.getValues()

        return values.lastOrNull() == null && values.filterNotNull().let { it == it.sorted() }
    }

    override fun processMove(direction: Direction) {
        val nullCell = board.find { it == null }

        println("Null cell: $nullCell")

        nullCell ?: return

        val neighborCell = with(board) {
            nullCell.getNeighbour(direction.reversed())
        }

        println("Neighbor cell: $neighborCell")

        neighborCell ?: return

        board[nullCell] = board[neighborCell]
        board[neighborCell] = null
    }


    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

    private fun <T> GameBoard<T?>.getValues(): List<T?> {
        return getAllCells().map { get(it) }
    }
}
