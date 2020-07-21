package com.app.android2048.model

import java.io.Serializable

data class Game(val countColumn: Int, val countRow: Int) : Serializable{

    var score: Int = 0
    internal var field: Array<Array<Cell>> = Array(countRow + 1) { Array(countColumn + 1) { Cell() } }

    fun createGameField() {
        for (i in 0..countColumn) {
            for (j in 0..countRow) {
                field[i][j] = Cell(0)
            }
        }
        createNewCellButton(this)
        createNewCellButton(this)
    }

    fun setAllCellsNotSummed() {
        for (i in 0..countColumn) {
            for (j in 0..countRow) {
                field[i][j].isSummed = false
            }
        }
    }

    fun hasEmptyCell(): Boolean {
        for (i in 0..countColumn) {
            for (j in 0..countRow) {
                if (this.field[i][j].value == 0) {
                    return true
                }
            }
        }
        return false
    }

    fun hasCellsToSum(): Boolean {
        for (j in 0..countColumn) {
            for (i in 0 until countRow) {
                if (this.field[i][j].value == this.field[i + 1][j].value) {
                    return true
                }
            }
        }
        for (i in 0..countRow) {
            for (j in 0 until countColumn) {
                if (field[i][j].value == field[i][j + 1].value) {
                    return true
                }
            }
        }
        return false
    }

    fun showGameField() {
        for (i in 0..countColumn) {
            for (j in 0..countRow) {
                print(field[i][j].value)
                print("\t")
            }
            println()
        }
        println()
    }
}