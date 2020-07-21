package com.app.android2048.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.app.android2048.service.OnSwipeTouchListener
import com.app.android2048.R
import com.app.android2048.model.Game
import com.app.android2048.model.Record
import com.app.android2048.model.createNewCellButton
import com.app.android2048.service.*
import com.app.android2048.util.JSONHelper
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private val GAME_FIELD_PREF: String = "GameField"
    private val RECORDS_PREF: String = "Records"
    private lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val arguments = intent.extras
        var game :Game
        if(arguments != null) {
            game = arguments.getSerializable(Game::class.java.simpleName) as Game
        } else{
            game = Game(3, 3)
            game.createGameField()
        }
        redrawGameField(game)
        userMotion(game)
    }

    override fun onPause() {
        super.onPause()
        saveGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveGame()
    }

    fun buttonLink(view: View) {
        when (view.id) {
            R.id.menu -> startActivity(Intent(this, MainActivity::class.java))
            R.id.records -> startActivity(Intent(this, RecordActivity::class.java))
            R.id.input_ok -> saveRecord()
        }
    }

    private fun userMotion(game: Game) {
        val gameFieldSwipe = findViewById<View>(R.id.game_field_swipe)
        gameFieldSwipe.setOnTouchListener(object : OnSwipeTouchListener(this@GameActivity) {
            override fun onSwipeRight() {
                Movement.RIGHT.swipe(game)
                processUserMotion(game)
            }

            override fun onSwipeLeft() {
                Movement.LEFT.swipe(game)
                processUserMotion(game)
            }

            override fun onSwipeTop() {
                Movement.UP.swipe(game)
                processUserMotion(game)
            }

            override fun onSwipeBottom() {
                Movement.DOWN.swipe(game)
                processUserMotion(game)
            }
        })
    }

    private fun processUserMotion(game: Game) {
        if (!isFieldChanged(game)) {
            if (game.hasEmptyCell()) {
                createNewCellButton(game)
            } else {
                stopGame()
            }
        }

        if (!game.hasEmptyCell() && !game.hasCellsToSum()) {
            stopGame()
        }

        redrawGameField(game)
        game.setAllCellsNotSummed()
    }

    private fun stopGame() {
        game_over_layout.visibility = 1
    }

    private fun isFieldChanged(game: Game): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
                val row = game_field_table.getChildAt(i) as TableRow
                val cellButton = row.getChildAt(j) as Button
                if (cellButton.text == "") {
                    if (game.field[i][j].value != 0) {
                        return false
                    }
                } else if (cellButton.text != game.field[i][j].value.toString()) {
                    return false
                }
            }
        }
        return true
    }

    private fun saveRecord() {
        val username = findViewById<EditText>(R.id.input_name)
        if (!username.getText().toString().equals("")) {
            val record = Record(
                username.text.toString(),
                score_text.text.toString().toInt()
            )
            val listRecords = JSONHelper.importFromJSON(this)
            if (listRecords != null) {
                val list = listRecords.toMutableList()
                list.add(record)
                JSONHelper.exportToJSON(this, list)
            } else {
                JSONHelper.exportToJSON(this, listOf(record))
            }
            startActivity(Intent(this, RecordActivity::class.java))
        }
    }

    private fun redrawGameField(game: Game) {
        for (i in 0..3) {
            for (j in 0..3) {
                val row = game_field_table.getChildAt(i) as TableRow
                val cellButton = row.getChildAt(j) as Button
                if (game.field[i][j].value == 0) {
                    cellButton.text = ""
                } else {
                    cellButton.text = game.field[i][j].value.toString()
                }
                setBackgroundCellButton(cellButton)
            }
        }
        score_text.text = game.score.toString()
    }

    private fun setBackgroundCellButton(cellButton: Button) {
        when (cellButton.text) {
            "" -> cellButton.setBackgroundResource(R.color.zero)
            "2" -> cellButton.setBackgroundResource(R.color.two)
            "4" -> cellButton.setBackgroundResource(R.color.four)
            "8" -> cellButton.setBackgroundResource(R.color.eight)
            "16" -> cellButton.setBackgroundResource(R.color.sixteen)
            "32" -> cellButton.setBackgroundResource(R.color.thirtyTwo)
            "64" -> cellButton.setBackgroundResource(R.color.sixtyFour)
            "128" -> cellButton.setBackgroundResource(R.color.oneHundredTwentyEight)
            "256" -> cellButton.setBackgroundResource(R.color.twoHundredFiftySix)
            "512" -> cellButton.setBackgroundResource(R.color.fiveHundredTwelve)
            "1024" -> cellButton.setBackgroundResource(R.color.oneThousandTwentyFour)
            "2048" -> cellButton.setBackgroundResource(R.color.twoThousandFortyEight)
            "4096" -> cellButton.setBackgroundResource(R.color.fourThousandNinetySix)
            "8192" -> cellButton.setBackgroundResource(R.color.eightThousandOneHundredNinetyTwo)
        }
    }

    private fun saveGame() {
        sPref = getSharedPreferences(GAME_FIELD_PREF, Context.MODE_PRIVATE)
        val editor: Editor = sPref.edit()
        editor.clear().apply()

        if (game_over_layout.visibility == View.INVISIBLE) {
            editor.putString("score", score_text.text.toString())
            for (i in 0..3) {
                for (j in 0..3) {
                    val row = game_field_table.getChildAt(i) as TableRow
                    val cellButton = row.getChildAt(j) as Button
                    editor.putString("cell_${i}_${j}", cellButton.text.toString())
                }
            }
            editor.apply()
        }
    }

}
