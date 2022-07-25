package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_multi_player_game_selection.*

var singleUser = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        idBtnSinglePlayer.setOnClickListener {
            singleUser = true
            startActivity(Intent(this,GamePlayActivity::class.java))
        }

        idBtnMultiPlayer.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,MultiPlayerGameSelectionActivity::class.java))
        }

    }
}