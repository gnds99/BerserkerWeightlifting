package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.databinding.FragmentSlashScreenBinding
import com.example.berserkerweightlifting.sharedPreferences.UserApplication.Companion.prefs
import com.example.berserkerweightlifting.viewModel.AppViewModel


class SlashScreenFragment : Fragment() {

    private var _binding: FragmentSlashScreenBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()
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
            prefs.saveLogin(true);
            this.goToLogin()
        }
    }

    private fun goToLogin()
    {
        val action = SlashScreenFragmentDirections.actionSlashScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }


}