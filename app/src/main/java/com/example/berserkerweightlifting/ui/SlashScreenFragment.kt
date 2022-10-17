package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.FragmentSlashScreenBinding


class SlashScreenFragment : Fragment() {

    private var _binding: FragmentSlashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentSlashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            this.goToLogin()
        }
    }

    private fun goToLogin()
    {
        val action = SlashScreenFragmentDirections.actionSlashScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }


}