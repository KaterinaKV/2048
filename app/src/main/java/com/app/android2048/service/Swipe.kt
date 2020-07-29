package com.app.android2048.service

import com.app.android2048.model.Game

interface Swipe{
    fun swipe(game: Game)
}

enum class Movement : Swipe {
    LEFT {
        override fun swipe(game: Game) {
            for (k in 0..3) {
                shiftCellsToLeft(game)
                for (i in 0..3) {
                    for (j in 0..2) {
                        if (game.field[i][j].value == game.field[i][j + 1].value
                            && !game.field[i][j].isSummed
                            && !game.field[i][j+1].isSummed
                        ) {
                            sumCells(game, i, j)
                            game.field[i][j + 1].value = 0
                        }
                    }
                }
            }
        }

        private fun shiftCellsToLeft(game: Game) {
            for (i in 0..3) {
                for (j in 0..2) {
                    if (game.field[i][j].value == 0) {
                        game.field[i][j].value = game.field[i][j + 1].value
                        game.field[i][j].isSummed = game.field[i][j + 1].isSummed
                        game.field[i][j + 1].value = 0
                        game.field[i][j + 1].isSummed = false
                    }
                }
            }
        }
    },

    RIGHT{
        override fun swipe(game: Game) {
            for (k in 0..3) {
                shiftCellsToRight(game)
                for (i in 3 downTo 0) {
                    for (j in 3 downTo 1) {
                        if (game.field[i][j].value == game.field[i][j - 1].value
                            && !game.field[i][j].isSummed
                            && !game.field[i][j - 1].isSummed
                        ) {
                            sumCells(game, i, j)
                            game.field[i][j - 1].value = 0
                        }
                    }
                }
            }
        }

        private fun shiftCellsToRight(game: Game) {
            for (i in 3 downTo 0) {
                for (j in 3 downTo 1) {
                    if (game.field[i][j].value == 0) {
                        game.field[i][j].value = game.field[i][j - 1].value
                        game.field[i][j].isSummed = game.field[i][j - 1].isSummed
                        game.field[i][j - 1].value = 0
                        game.field[i][j - 1].isSummed = false
                    }
                }
            }
        }
    },

    UP{
        override fun swipe(game: Game) {
            for (k in 0..3) {
                shiftCellsToUp(game)
                for (j in 0..3) {
                    for (i in 0..2) {
                        if (game.field[i][j].value == game.field[i + 1][j].value
                            && !game.field[i][j].isSummed
                            && !game.field[i + 1][j].isSummed
                        ) {
                            sumCells(game, i, j)
                            game.field[i + 1][j].value = 0
                        }
                    }
                }
            }
        }

        private fun shiftCellsToUp(game: Game) {
            for (j in 0..3) {
                for (i in 0..2) {
                    if (game.field[i][j].value == 0) {
                        game.field[i][j].value = game.field[i + 1][j].value
                        game.field[i][j].isSummed = game.field[i + 1][j].isSummed
                        game.field[i + 1][j].value = 0
                        game.field[i + 1][j].isSummed = false
                    }
                }
            }
        }
    },

    DOWN{
        override fun swipe(game: Game) {
            for (k in 0..3) {
                shiftCellsToDown(game)
                for (j in 3 downTo 0) {
                    for (i in 3 downTo 1) {
                        if (game.field[i][j].value == game.field[i - 1][j].value
                            && !game.field[i][j].isSummed
                            && !game.field[i - 1][j].isSummed
                        ) {
                            sumCells(game, i, j)
                            game.field[i - 1][j].value = 0
                        }
                    }
                }
            }
        }

        private fun shiftCellsToDown(game: Game) {
            for (j in 3 downTo 0) {
                for (i in 3 downTo 1) {
                    if (game.field[i][j].value == 0) {
                        game.field[i][j].value = game.field[i - 1][j].value
                        game.field[i][j].isSummed = game.field[i - 1][j].isSummed
                        game.field[i - 1][j].value = 0
                        game.field[i - 1][j].isSummed = false
                    }
                }
            }
        }
    };

    fun sumCells(game: Game, i: Int, j: Int) {
        game.field[i][j].value *= 2
        game.field[i][j].isSummed = true
        game.score += game.field[i][j].value
    }
}
