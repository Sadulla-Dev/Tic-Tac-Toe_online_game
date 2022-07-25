package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_multi_player_game_selection.*

class MultiPlayerGameSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player_game_selection)

        idBtnOnline.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,OnlineCodeGeneratorActivity::class.java))
        }
        idBtnOffline.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,GamePlayActivity::class.java))
        }
    }
}