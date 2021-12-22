package com.bowen.quiz_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bowen.quiz_fragments.databinding.FragmentMainBinding

    const val KEY_CURRENT_QUESTION = "currentQuestionKey"
    val questionList = listOf(
        MainFragment.Question(R.string.question1, true),
        MainFragment.Question(R.string.question2, false),
        MainFragment.Question(R.string.question3, false),
        MainFragment.Question(R.string.question4, true),
        MainFragment.Question(R.string.question5, false)
    )
    var currentQuestionIndex = 0

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
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
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
            Toast.makeText(activity,R.string.correct, Toast.LENGTH_SHORT).show() //display toast saying "Correct!"
        else
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show() //display toast saying "Incorrect!"
    }
}