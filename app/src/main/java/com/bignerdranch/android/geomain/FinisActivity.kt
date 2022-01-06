package com.bignerdranch.android.geomain


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView


class FinisActivity : AppCompatActivity() {
        private lateinit var tvResult: TextView
        private lateinit var butRestart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finis)

        tvResult = findViewById(R.id.tvResult)
        butRestart = findViewById(R.id.butRestart)

        val resultSum: String? = intent.getStringExtra("name")
        tvResult.text = resultSum

        butRestart.setOnClickListener{
            this.finish()

        }
    }




}