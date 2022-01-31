package com.bowen.quiz_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bowen.quiz_fragments.databinding.FragmentCheatBinding


class CheatFragment : Fragment() {

    private var _binding: FragmentCheatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(inflater, container, false)
        val rootView = binding.root
        // Inflate the layout for this fragment
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}