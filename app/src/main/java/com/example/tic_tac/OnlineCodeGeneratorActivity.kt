package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import com.example.tic_tac.databinding.ActivityOnlineCodeGeneratorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue: String = "null"

class OnlineCodeGeneratorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineCodeGeneratorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineCodeGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnCreate.setOnClickListener {

            code = "null"
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = binding.idEditCode.text.toString()
            binding.idBtnCreate.visibility = View.GONE
            binding.idBtnJoin.visibility = View.GONE
            binding.idEditCode.visibility = View.GONE
            binding.idBtnCreate.visibility = View.GONE
            binding.idPBLoading.visibility = View.VISIBLE
            if (code != "null" && code != "") {
                isCodeMaker = true
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var check = isValueAvaliable(snapshot, code)
                            Handler().postDelayed({
                                if (check == true) {
                                    binding.idBtnCreate.visibility = View.VISIBLE
                                    binding.idBtnJoin.visibility = View.VISIBLE
                                    binding.idEditCode.visibility = View.VISIBLE
                                    binding.idPBLoading.visibility = View.GONE
                                } else {
                                    FirebaseDatabase.getInstance().reference.child("codes").push()
                                        .setValue(code)
                                    isValueAvaliable(snapshot, code)
                                    checkTemp = false
                                    Handler().postDelayed({
                                        accepted()
                                        Toast.makeText(
                                            this@OnlineCodeGeneratorActivity,
                                            "Please don't go back ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }, 300)
                                }
                            }, 2000)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            } else {
                binding.idBtnCreate.visibility = View.VISIBLE
                binding.idBtnJoin.visibility = View.VISIBLE
                binding.idEditCode.visibility = View.VISIBLE
                binding.idPBLoading.visibility = View.GONE
                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
            }
        }
        binding.idBtnJoin.setOnClickListener {
            code = "null"
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = binding.idEditCode.text.toString()
            if (code != "null" && code != "") {
                binding.idBtnCreate.visibility = View.GONE
                binding.idBtnJoin.visibility = View.GONE
                binding.idEditCode.visibility = View.GONE
                binding.idPBLoading.visibility = View.VISIBLE

                isCodeMaker = false
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var data: Boolean = isValueAvaliable(snapshot, code)
                            Handler().postDelayed({
                                if (data==true){
                                    codeFound=true
                                    accepted()
                                    binding.idBtnCreate.visibility = View.VISIBLE
                                    binding.idBtnJoin.visibility = View.VISIBLE
                                    binding.idEditCode.visibility = View.VISIBLE
                                    binding.idPBLoading.visibility = View.GONE
                                }else{
                                    binding.idBtnCreate.visibility = View.VISIBLE
                                    binding.idBtnJoin.visibility = View.VISIBLE
                                    binding.idEditCode.visibility = View.VISIBLE
                                    binding.idPBLoading.visibility = View.GONE
                                    Toast.makeText(this@OnlineCodeGeneratorActivity, "Invalid Code", Toast.LENGTH_SHORT).show()
                                }
                            },2000)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            } else {
                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun accepted() {
        startActivity(Intent(this, OnlineMultiPlayerGameActivity::class.java))
        binding.idBtnCreate.visibility = View.VISIBLE
        binding.idBtnJoin.visibility = View.VISIBLE
        binding.idEditCode.visibility = View.VISIBLE
        binding.idPBLoading.visibility = View.GONE
    }

    fun isValueAvaliable(snapshot: DataSnapshot, code: String): Boolean {
        var data = snapshot.children
        data.forEach {
            var value = it.getValue().toString()
            if (value == code) {
                keyValue = it.key.toString()
                return true
            }
        }
        return false
    }

}