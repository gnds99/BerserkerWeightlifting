package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.FragmentProfileScreenBinding
import com.example.berserkerweightlifting.databinding.FragmentPrsScreenBinding
import com.example.berserkerweightlifting.databinding.FragmentSettingsScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel

class SettingsScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlan.setOnClickListener {
            this.goToPremium()
        }
        binding.btnSalir.setOnClickListener {
            this.logOut()
        }
    }

    // Rutas
    private fun goToPremium(){
        val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToPremiumScreenFragment()
        findNavController().navigate(action)
    }
    private fun logOut(){
        findNavController().navigate(R.id.loginScreenFragment)
    }


}