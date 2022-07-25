package com.example.tic_tac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_online_code_generator.*

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue: String = "null"

class OnlineCodeGeneratorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_code_generator)

        idBtnCreate.setOnClickListener {

            code = "null"
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = idEditCode.text.toString()
            idBtnCreate.visibility = View.GONE
            idBtnJoin.visibility = View.GONE
            idTvHead.visibility = View.GONE
            idEditCode.visibility = View.GONE
            idBtnCreate.visibility = View.GONE
            idPBLoading.visibility = View.VISIBLE
            if (code != "null" && code != "") {
                isCodeMaker = true
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var check = isValueAvaliable(snapshot, code)
                            Handler().postDelayed({
                                if (check == true) {
                                    idBtnCreate.visibility = View.VISIBLE
                                    idBtnJoin.visibility = View.VISIBLE
                                    idTvHead.visibility = View.VISIBLE
                                    idEditCode.visibility = View.VISIBLE
                                    idPBLoading.visibility = View.GONE
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
                idBtnCreate.visibility = View.VISIBLE
                idBtnJoin.visibility = View.VISIBLE
                idTvHead.visibility = View.VISIBLE
                idEditCode.visibility = View.VISIBLE
                idPBLoading.visibility = View.GONE
                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
            }
        }
        idBtnJoin.setOnClickListener {
            code = "null"
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = idEditCode.text.toString()
            if (code != "null" && code != "") {
                idBtnCreate.visibility = View.GONE
                idBtnJoin.visibility = View.GONE
                idTvHead.visibility = View.GONE
                idEditCode.visibility = View.GONE
                idPBLoading.visibility = View.VISIBLE

                isCodeMaker = false
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var data: Boolean = isValueAvaliable(snapshot, code)
                            Handler().postDelayed({
                                if (data==true){
                                    codeFound=true
                                    accepted()
                                    idBtnCreate.visibility = View.VISIBLE
                                    idBtnJoin.visibility = View.VISIBLE
                                    idTvHead.visibility = View.VISIBLE
                                    idEditCode.visibility = View.VISIBLE
                                    idPBLoading.visibility = View.GONE
                                }else{
                                    idBtnCreate.visibility = View.VISIBLE
                                    idBtnJoin.visibility = View.VISIBLE
                                    idTvHead.visibility = View.VISIBLE
                                    idEditCode.visibility = View.VISIBLE
                                    idPBLoading.visibility = View.GONE
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
        idBtnCreate.visibility = View.VISIBLE
        idBtnJoin.visibility = View.VISIBLE
        idEditCode.visibility = View.VISIBLE
        idTvHead.visibility = View.VISIBLE
        idPBLoading.visibility = View.GONE
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