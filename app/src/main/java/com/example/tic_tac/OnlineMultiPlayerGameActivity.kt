package com.example.tic_tac


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tic_tac.databinding.ActivityOnlineMultiPlayerGameBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.exitProcess

var isMyMove = isCodeMaker

class OnlineMultiPlayerGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineMultiPlayerGameBinding
    var player1Count = 0
    var player2Count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activityUser = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineMultiPlayerGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnReset.setOnClickListener {
            reset()
        }

        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var data = snapshot.value
                    if (isMyMove == true) {
                        isMyMove = false
                        moveOnline(data.toString(), isMyMove)
                    } else {
                        isMyMove = true
                        moveOnline(data.toString(), isMyMove)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    reset()
                    Toast.makeText(
                        this@OnlineMultiPlayerGameActivity,
                        "Game reset",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun moveOnline(data: String, move: Boolean) {

        if (move) {
            var buttonSelected: Button?
            buttonSelected = when (data.toInt()) {
                1 -> binding.idBtnBox1
                2 -> binding.idBtnBox2
                3 -> binding.idBtnBox3
                4 -> binding.idBtnBox4
                5 -> binding.idBtnBox5
                6 -> binding.idBtnBox6
                7 -> binding.idBtnBox7
                8 -> binding.idBtnBox8
                9 -> binding.idBtnBox9
                else -> {
                    binding.idBtnBox1
                }
            }
            buttonSelected.text = "O"

            binding.idTvTurn.text = "Turn: player 1"
            buttonSelected.setTextColor(resources.getColor(R.color.red))
            player2.add(data.toInt())
            emptyCells.add(data.toInt())
            buttonSelected.isEnabled = false

            checkWinner()
        }
    }

    private fun playNow(buttonSelected: Button, currCell: Int) {
        if (activityUser == 1) {
            buttonSelected.text = "X"
            emptyCells.remove(currCell)
            binding.idTvTurn.text = "Turn: player2"
            buttonSelected.setTextColor(resources.getColor(R.color.green))
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            checkWinner()

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
            disableReset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game over")
            build.setMessage("Player 1 Wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                removeCode()
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        } else if (
            player2.contains(1) && player2.contains(2) && player2.contains(3) ||
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
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game over")
            build.setMessage("Player 2 Wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                removeCode()
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3)
            && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6)
            && emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9)
        ) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game draw")
            build.setMessage("Game draw \n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
                removeCode()
            }
            build.show()
            return 1
        }
        return 0

    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activityUser = 1
        for (i in 1..9) {
            var buttonSelected: Button?
            buttonSelected = when (i) {
                1 -> binding.idBtnBox1
                2 -> binding.idBtnBox2
                3 -> binding.idBtnBox3
                4 -> binding.idBtnBox4
                5 -> binding.idBtnBox5
                6 -> binding.idBtnBox6
                7 -> binding.idBtnBox7
                8 -> binding.idBtnBox8
                9 -> binding.idBtnBox9
                else -> {
                    binding.idBtnBox1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            binding.user1.text = player1Count.toString()
            binding.user2.text = player2Count.toString()
            if (isCodeMaker) {
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()

            }
        }
    }

    private fun buttonDisabled() {
        for (i in 1..9) {
            val buttonSelected = when (i) {
                1 -> binding.idBtnBox1
                2 -> binding.idBtnBox2
                3 -> binding.idBtnBox3
                4 -> binding.idBtnBox4
                5 -> binding.idBtnBox5
                6 -> binding.idBtnBox6
                7 -> binding.idBtnBox7
                8 -> binding.idBtnBox8
                9 -> binding.idBtnBox9
                else -> {
                     binding.idBtnBox1
                }
            }
            if (buttonSelected.isEnabled == true) {
                buttonSelected.isEnabled = false
            }
        }
    }

    fun removeCode() {
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("codes").child(keyValue).removeValue()
        }
    }

    fun disableReset() {
        binding.idBtnReset.isEnabled = false
        Handler().postDelayed(Runnable { binding.idBtnReset.isEnabled = true }, 2000)
    }

    fun updateDatabase(cellId: Int) {
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        removeCode()
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        }
        exitProcess(0)
    }

    fun buttonClick(view: View) {
        if (playerTurn) {
            val but = view as Button
            var cellOnLine = 0
            when (but.id) {
                R.id.idBtnBox1 -> cellOnLine = 1
                R.id.idBtnBox2 -> cellOnLine = 2
                R.id.idBtnBox3 -> cellOnLine = 3
                R.id.idBtnBox4 -> cellOnLine = 4
                R.id.idBtnBox5 -> cellOnLine = 5
                R.id.idBtnBox6 -> cellOnLine = 6
                R.id.idBtnBox7 -> cellOnLine = 7
                R.id.idBtnBox8 -> cellOnLine = 8
                R.id.idBtnBox9 -> cellOnLine = 9
            }
            playerTurn = false
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playNow(but, cellOnLine)
            updateDatabase(cellOnLine)
        }
    }


}