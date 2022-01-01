package com.bignerdranch.android.geomain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var buttBack: Button
    private lateinit var buttStart: Button
    private lateinit var questionTextView: TextView


    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        buttBack = findViewById(R.id.buttBack)
        buttStart = findViewById(R.id.buttStart)
        questionTextView = findViewById(R.id.question_text_view)



        trueButton.setOnClickListener{

            trueButton.setEnabled(false)
            falseButton.setEnabled(false)
            checkAnswer(true)
        }

       falseButton.setOnClickListener {
           falseButton.setEnabled(false)
           trueButton.setEnabled(false)
           checkAnswer(false)
         }

        nextButton.setOnClickListener {
            if (quizViewModel.currentIndex == quizViewModel.questionBank.lastIndex){
                //Log.d("MyLogActivMain", "И что ? и все!")
                val intent = Intent(this, FinisActivity::class.java)
                startActivity(intent)
            }
            else
            {
                quizViewModel.moveToNext()
                updateQuestion()
            }
            falseButton.setEnabled(true)
            trueButton.setEnabled(true)
            buttBack.setEnabled(true)
        }
        //buttBack с условием и неактивной кнопкой
        buttBack.setOnClickListener {
            if (quizViewModel.currentIndex == 0)
            {
                buttBack.setEnabled(false)
                Log.d("MyLogActivMain", "И что ? и все!")


            }
            else {

                quizViewModel.moveToBack()
                updateQuestion()
            }
        }
        buttStart.setOnClickListener {
            quizViewModel.moveToStart()
            updateQuestion()
            buttBack.setEnabled(true)
            falseButton.setEnabled(true)
            trueButton.setEnabled(true)
        }

        updateQuestion()
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
      //  var counters = 0
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId =
            if (userAnswer == correctAnswer)
            {
                R.string.correct_toast
            }
            else {
                R.string.incorrect_toast
            }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        //Log.d("MyLogActivMain" ,"$counters")
    }


}
