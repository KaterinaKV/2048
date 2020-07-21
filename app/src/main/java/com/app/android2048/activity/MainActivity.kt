package com.app.android2048.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.app.android2048.R
import com.app.android2048.model.Game

class MainActivity : AppCompatActivity() {

    private lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hasSavedGame()
    }

    private fun hasSavedGame() {
        val keepGoingButton = findViewById<Button>(R.id.keep_going)
        if (loadGame() == null){
            keepGoingButton.visibility = View.GONE
        } else{
            keepGoingButton.visibility = View.VISIBLE
        }
    }

    private fun loadGame(): Game? {
        sPref = getSharedPreferences("GameField", Context.MODE_PRIVATE)
        val score = sPref.getString("score", "")
        if (score != "" ) {
            val game = Game(3, 3)
            game.score = score.toString().toInt()
            for (i in 0..3) {
                for (j in 0..3) {
                    val cellText = sPref.getString("cell_${i}_${j}", "")
                    if (cellText == "") {
                        game.field[i][j].value = 0
                    } else {
                        game.field[i][j].value = cellText?.toInt()!!
                    }
                }
            }
            return game
        }
        return null
    }

    fun buttonLink (view: View) {
        when (view.id) {
            R.id.new_game -> {
                intent = Intent(this, GameActivity::class.java)
                val newGame = Game(3, 3)
                newGame.createGameField()
                intent.putExtra(Game::class.java.simpleName, newGame)
                startActivity(intent)
            }
            R.id.keep_going -> {
                intent = Intent(this, GameActivity::class.java)
                intent.putExtra(Game::class.java.simpleName, loadGame())
                startActivity(intent)
            }
            R.id.records -> startActivity(Intent(this, RecordActivity::class.java))
        }
    }

}
