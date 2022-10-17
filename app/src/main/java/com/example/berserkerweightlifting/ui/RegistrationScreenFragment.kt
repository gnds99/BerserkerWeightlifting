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
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.databinding.FragmentRegistrationScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class RegistrationScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()
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
        binding.btnLogin.setOnClickListener{this.goToLogin()}



        sharedViewModel.register.observe(viewLifecycleOwner){
            if(sharedViewModel.register.value == Options.CREATE){
                Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                goTohome()
            } else if (sharedViewModel.register.value == Options.NOCREATED){
                Toast.makeText(context, "Hubo un error con el registro", Toast.LENGTH_SHORT).show()
            }
        }
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        binding.btnRegistrar.setOnClickListener{
            val email = binding.txtCorreoUsuario.text.toString()
            val password = binding.txtPasswordUsuario.text.toString()
            val name = binding.txtNombreUsuario.text.toString()
            val lastname = binding.txtApellidoUsuario.text.toString()

            sharedViewModel.RegisterUser(email, password,name,lastname)


        }


    }


    private fun goToLogin(){
        val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }

    private fun goTohome(){
        val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToHomeScreenFragment()
        findNavController().navigate(action)
    }
}