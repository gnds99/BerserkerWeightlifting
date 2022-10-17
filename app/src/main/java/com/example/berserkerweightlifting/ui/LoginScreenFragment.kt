package com.example.berserkerweightlifting.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.core.FirebaseHelper
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.databinding.FragmentLoginScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel


class LoginScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()

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
        binding.btnGoogle.setOnClickListener { Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show() }
        binding.btnFacebook.setOnClickListener { Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show() }
        binding.btnRecuperarContrasenia.setOnClickListener { Toast.makeText(context, "Reset Password", Toast.LENGTH_SHORT).show() }


        sharedViewModel.login.observe(viewLifecycleOwner){
            if(sharedViewModel.login.value == Options.LOGIN){
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                this.goToHome()
            }else if (sharedViewModel.login.value == Options.ERROR){
                Toast.makeText(context, "Hubo un error iniciando sesión", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnIngresar.setOnClickListener {
            val auth = FirebaseHelper.getConnector()
            val email = binding.txtCorreoUsuario.text.toString()

            val password = binding.txtPasswordUsuario.text.toString()

            sharedViewModel.StarLogin(email, password)
        }


    }

    private fun goToRegistration(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToRegistrationScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToHome(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToHomeScreenFragment()
        findNavController().navigate(action)
    }
}