package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.FragmentRegistrationScreenBinding


class RegistrationScreenFragment : Fragment() {

    private var _binding: FragmentRegistrationScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFacebook.setOnClickListener { Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show() }
        binding.btnGoogle.setOnClickListener { Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show() }
        binding.btnRegistrar.setOnClickListener{ Toast.makeText(context, "Registrando", Toast.LENGTH_SHORT).show()}
        binding.btnLogin.setOnClickListener{this.goToLogin()}

    }


    private fun goToLogin(){
        val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }
}