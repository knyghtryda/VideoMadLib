package com.yancorp.videomadlib

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val btnShow = findViewById<Button>(R.id.btnShow)
        btnShow?.setOnClickListener { showText() }
    }

    private fun showText() {
        val editText = findViewById<EditText>(R.id.editText)
        if (editText != null) {
            Toast.makeText(this, editText.text, Toast.LENGTH_LONG).show()
        }
        val intent = Intent(this,MainActivity :: class.java)
        startActivity(intent)
    }


}
