package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.berserkerweightlifting.databinding.FragmentPremiumScreenBinding



class PremiumScreenFragment : Fragment() {
    private var _binding: FragmentPremiumScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPremiumScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


}