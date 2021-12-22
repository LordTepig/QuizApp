package com.bowen.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bowen.quiz.MainActivity.Question
import com.bowen.quiz.databinding.ActivityMainBinding

const val KEY_CURRENT_QUESTION = "currentQuestionKey"
lateinit var binding: ActivityMainBinding
val questionList = listOf(
    Question(R.string.question1, true),
    Question(R.string.question2, false),
    Question(R.string.question3, false),
    Question(R.string.question4, true),
    Question(R.string.question5, false)
)
var currentQuestionIndex = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "onCreate Called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        if(savedInstanceState != null)
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION)

        updateQuestionText()
        binding.trueAnswerButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseAnswerButton.setOnClickListener {
            checkAnswer(false)
        }
        binding.nextQuestionButton.setOnClickListener {
            nextQuestion()
        }
        binding.quizQuestionTextView.setOnClickListener{
            nextQuestion()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_CURRENT_QUESTION, currentQuestionIndex)
    }

    data class Question(val questionID: Int, val answer:Boolean){
    }


    fun nextQuestion(){
        val maxIndex = questionList.size - 1
        if(currentQuestionIndex < maxIndex)
            currentQuestionIndex++
        else
            currentQuestionIndex=0 //loops back to the first index if it is at the last index
        updateQuestionText()
    }

    fun updateQuestionText(){
        val questionText = getString(questionList[currentQuestionIndex].questionID) //need to reference the string Id
        binding.quizQuestionTextView.text = questionText
    }

    fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionList[currentQuestionIndex].answer
        if(userAnswer == correctAnswer)
            Toast.makeText(this,R.string.correct, Toast.LENGTH_SHORT).show() //display toast saying "Correct!"
        else
            Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show() //display toast saying "Incorrect!"
    }
}