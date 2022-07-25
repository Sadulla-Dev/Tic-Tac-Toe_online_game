package com.example.tic_tac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_game_play.*
import kotlin.system.exitProcess

var playerTurn = true

class GamePlayActivity : AppCompatActivity() {
    var player1Count = 0
    var player2Count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activityUser = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)

        idBtnReset.setOnClickListener {
            reset()
        }
    }


    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activityUser = 1
        for (i in 1..9) {
            var buttonSelected: Button?
            buttonSelected = when (i) {
                1 -> idBtnBox1
                2 -> idBtnBox2
                3 -> idBtnBox3
                4 -> idBtnBox4
                5 -> idBtnBox5
                6 -> idBtnBox6
                7 -> idBtnBox7
                8 -> idBtnBox8
                9 -> idBtnBox9
                else -> {
                    idBtnBox1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            user1.text = player1Count.toString()
            user2.text = player2Count.toString()
        }
    }

    fun buttonClick(view: View) {
        if (playerTurn) {
            val but = view as Button
            var cellId = 0
            when (but.id) {
                R.id.idBtnBox1 -> cellId = 1
                R.id.idBtnBox2 -> cellId = 2
                R.id.idBtnBox3 -> cellId = 3
                R.id.idBtnBox4 -> cellId = 4
                R.id.idBtnBox5 -> cellId = 5
                R.id.idBtnBox6 -> cellId = 6
                R.id.idBtnBox7 -> cellId = 7
                R.id.idBtnBox8 -> cellId = 8
                R.id.idBtnBox9 -> cellId = 9
            }
            playerTurn = false
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playNow(but, cellId)
        }
    }

    private fun playNow(buttonSelected: Button, currCell: Int) {
        if (activityUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(resources.getColor(R.color.green))
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val chekWinner = checkWinner()
            if (chekWinner == 1) {
                reset()
            } else if (singleUser) {
                robot()
            } else {
                activityUser = 2
            }
        } else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(resources.getColor(R.color.red))
            activityUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() },4000)
            }

        }
    }

    private fun robot() {
        val rnd = (1..9).random()
        if (emptyCells.contains(rnd)) {
            robot()
        } else {
            val buttonSelected = when (rnd) {
                1 -> idBtnBox1
                2 -> idBtnBox2
                3 -> idBtnBox3
                4 -> idBtnBox4
                5 -> idBtnBox5
                6 -> idBtnBox6
                7 -> idBtnBox7
                8 -> idBtnBox8
                9 -> idBtnBox9
                else -> {
                    idBtnBox1
                }
            }
            emptyCells.add(rnd)
            buttonSelected.text = "o"
            buttonSelected.setTextColor(resources.getColor(R.color.red))
            player2.add(rnd)
            buttonSelected.isEnabled = false
            var checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() },2000)
            }
        }
    }

    private fun checkWinner(): Int {

        if (player1.contains(1) && player1.contains(2) && player1.contains(3) ||
            player1.contains(1) && player1.contains(4) && player1.contains(7) ||
            player1.contains(3) && player1.contains(6) && player1.contains(9) ||
            player1.contains(7) && player1.contains(8) && player1.contains(8) ||
            player1.contains(4) && player1.contains(5) && player1.contains(6) ||
            player1.contains(1) && player1.contains(5) && player1.contains(9) ||
            player1.contains(3) && player1.contains(5) && player1.contains(7) ||
            player1.contains(2) && player1.contains(5) && player1.contains(8)
        ) {

            player1Count += 1
            buttonDisabled()
            disabledReset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game over")
            build.setMessage("Player 1 Wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
//                finish()
            }
            build.show()
            return 1
        } else if (player2.contains(1) && player2.contains(2) && player2.contains(3) ||
            player2.contains(1) && player2.contains(4) && player2.contains(7) ||
            player2.contains(3) && player2.contains(6) && player2.contains(9) ||
            player2.contains(7) && player2.contains(8) && player2.contains(8) ||
            player2.contains(4) && player2.contains(5) && player2.contains(6) ||
            player2.contains(1) && player2.contains(5) && player2.contains(9) ||
            player2.contains(3) && player2.contains(5) && player2.contains(7) ||
            player2.contains(2) && player2.contains(5) && player2.contains(8)
        ) {
            player2Count += 1
            buttonDisabled()
            disabledReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game over")
            build.setMessage("Player 2 Wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9)) {


            val build = AlertDialog.Builder(this)
            build.setTitle("Game draw")
            build.setMessage("Game draw \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1
        }
        return 0
    }


    private fun buttonDisabled()  {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activityUser = 1
        for (i in 1..9) {
            var buttonSelected: Button?
            buttonSelected = when (i) {
                1 -> idBtnBox1
                2 -> idBtnBox2
                3 -> idBtnBox3
                4 -> idBtnBox4
                5 -> idBtnBox5
                6 -> idBtnBox6
                7 -> idBtnBox7
                8 -> idBtnBox8
                9 -> idBtnBox9
                else -> {
                    idBtnBox1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            user1.text = player1Count.toString()
            user2.text = player2Count.toString()

        }
    }

    private fun disabledReset() {
        idBtnReset.isEnabled = false
        Handler().postDelayed(Runnable { idBtnReset.isEnabled = true }, 2200)
    }
}