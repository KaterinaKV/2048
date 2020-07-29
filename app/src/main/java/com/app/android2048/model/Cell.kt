package com.app.android2048.model

import java.io.Serializable

class Cell(var value: Int) : Serializable{
    var isSummed: Boolean = false
    constructor() : this(0)
}

fun createNewCellButton(game: Game) {
    var x: Int
    var y: Int
    do {
        x = (0..3).random()
        y = (0..3).random()
    } while (game.field[x][y].value != 0)
    game.field[x][y] = Cell(randomCellValue())
}

fun randomCellValue(): Int {
    return arrayOf(2, 4).random()
}