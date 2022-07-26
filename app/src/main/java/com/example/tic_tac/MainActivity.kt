package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tic_tac.databinding.ActivityMainBinding

var singleUser = false
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.idBtnSinglePlayer.setOnClickListener {
            singleUser = true
            startActivity(Intent(this,GamePlayActivity::class.java))
        }

        binding.idBtnMultiPlayer.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,MultiPlayerGameSelectionActivity::class.java))
        }

    }
}