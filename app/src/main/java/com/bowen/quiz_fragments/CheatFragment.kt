package com.bowen.quiz_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.bowen.quiz_fragments.databinding.FragmentCheatBinding


class CheatFragment : Fragment() {

    private var _binding: FragmentCheatBinding? = null
    private val binding get() = _binding!!
    var hasCheated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = CheatFragmentArgs.fromBundle(requireArguments())
        val correctAnswer = args.answerArg
        // Inflate the layout for this fragment
        binding.showAnswerButton.setOnClickListener {
            binding.cheatAnswerTextView.text = correctAnswer.toString().uppercase()
            hasCheated = true

            setFragmentResult("requestKey", bundleOf("bundleKey" to hasCheated))

        }
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}