package com.yancorp.videomadlib

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btnOpenActivity : Button = findViewById(R.id.buttonenter)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this,TextActivity :: class.java)
            startActivity(intent)
        }


    }
}
