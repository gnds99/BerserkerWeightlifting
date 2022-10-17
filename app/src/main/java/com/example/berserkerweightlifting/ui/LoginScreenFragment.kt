package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.FragmentLoginScreenBinding


class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCrearCuenta.setOnClickListener { goToRegistration() }
        binding.btnIngresar.setOnClickListener { Toast.makeText(context, "Ingresando", Toast.LENGTH_SHORT).show() }
        binding.btnGoogle.setOnClickListener { Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show() }
        binding.btnFacebook.setOnClickListener { Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show() }
        binding.btnRecuperarContrasenia.setOnClickListener { Toast.makeText(context, "Reset Password", Toast.LENGTH_SHORT).show() }
    }

    private fun goToRegistration(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToRegistrationScreenFragment()
        findNavController().navigate(action)
    }
}