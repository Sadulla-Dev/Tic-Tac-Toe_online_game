package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tic_tac.databinding.ActivityMultiPlayerGameSelectionBinding

class MultiPlayerGameSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiPlayerGameSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiPlayerGameSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnOnline.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,OnlineCodeGeneratorActivity::class.java))
        }
        binding.idBtnOffline.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,GamePlayActivity::class.java))
        }
    }
}