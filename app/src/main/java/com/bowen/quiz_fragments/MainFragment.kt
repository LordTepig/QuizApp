package com.bowen.quiz_fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bowen.quiz_fragments.databinding.FragmentMainBinding



    const val KEY_CURRENT_QUESTION = "currentQuestionKey"
    val questionList = listOf(
        MainFragment.Question(R.string.question1, true, false),
        MainFragment.Question(R.string.question2, false, false),
        MainFragment.Question(R.string.question3, false, false),
        MainFragment.Question(R.string.question4, true, false),
        MainFragment.Question(R.string.question5, false, false)
    )
    var currentQuestionIndex = 0
    var numCorrectAnswers = 0
    var numIncorrectAnswers = 0

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    data class Question(val questionID: Int, val answer:Boolean, var hasCheated:Boolean){
    }

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

            if(numCorrectAnswers == 3){
                val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(numIncorrectAnswers)
                rootView.findNavController().navigate(action)
            }

        }
        binding.falseAnswerButton.setOnClickListener {
            checkAnswer(false)

            if(numCorrectAnswers == 3){
                val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(numIncorrectAnswers)
                rootView.findNavController().navigate(action)

            }

        }
        binding.nextQuestionButton.setOnClickListener {
            nextQuestion()
        }
        binding.quizQuestionTextView.setOnClickListener{
            nextQuestion()
        }
        binding.cheatButton.setOnClickListener {
            val correctAnswer = questionList[currentQuestionIndex].answer

            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(correctAnswer)
            rootView.findNavController().navigate(action)
        }
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getString("bundleKey")
            questionList[currentQuestionIndex].hasCheated = true


        }
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
                onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_CURRENT_QUESTION, currentQuestionIndex)
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
        if((userAnswer == correctAnswer) && !(questionList[currentQuestionIndex].hasCheated)) {
            Toast.makeText(activity, R.string.correct, Toast.LENGTH_SHORT).show() //display toast saying "Correct!"
            numCorrectAnswers++
        }
        else if((userAnswer == correctAnswer && (questionList[currentQuestionIndex].hasCheated))){
            Toast.makeText(activity, "Cheating is wrong!", Toast.LENGTH_SHORT).show()
        }
        else if (userAnswer != correctAnswer){
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show() //display toast saying "Incorrect!"
            numIncorrectAnswers++
        }

    }
}