package com.example.berserkerweightlifting.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.core.RC_SIGN_IN
import com.example.berserkerweightlifting.data.models.ValidateEmail
import com.example.berserkerweightlifting.databinding.FragmentRegistrationScreenBinding
import com.example.berserkerweightlifting.sharedPreferences.UserApplication.Companion.prefs
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
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
        binding.btnLogin.setOnClickListener{this.goToLogin()}


        sharedViewModel.register.observe(viewLifecycleOwner){
            if(sharedViewModel.register.value == Options.CREATE){
                Toast.makeText(context, prefs.getId(), Toast.LENGTH_SHORT).show()
                goToHome()
            } else if (sharedViewModel.register.value == Options.NOCREATED){
                Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRegistrar.setOnClickListener{
            val email = clearEntry(binding.txtcorreoUsuario.editText?.text.toString())
            val password = clearEntry(binding.txtPasswordUsuario.editText?.text.toString())
            val confirmationPassword = clearEntry(binding.txtPassword2Usuario.editText?.text.toString())
            val name = clearEntry(binding.txtnombreUsuario.editText?.text.toString())
            val lastname = clearEntry(binding.txtapellidoUsuario.editText?.text.toString())
            val fullname = "${name} ${lastname}"

            val validnameAndPassword = this.validateName(name)
            val validEmail = this.validateEmail(email)
            val validLastname = this.validateLastname(lastname)
            val validPassword = this.validatePassword(password, confirmationPassword)
            if( validnameAndPassword && validPassword && validLastname && validEmail ){
                sharedViewModel.RegisterUser(email, password,fullname)
            }

        }


    }




    private fun goToLogin(){
        val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToHome(){
        //val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToHomeScreenFragment()
        findNavController().navigate(R.id.homeScreenFragment)
    }

    private fun validatePassword(password: String, confirmationPassword: String): Boolean{

        var bandera = false
        if(TextUtils.isEmpty(password)){
            binding.txtPasswordUsuario.error = "La contraseña es necesaria"
            binding.txtPassword2Usuario.error = "La confirmación es necesaria"
        }else{
            binding.txtPasswordUsuario.error = ""
            binding.txtPassword2Usuario.error = ""
            if(password == confirmationPassword){
                bandera = true
            }else{
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        return bandera

    }

    private fun validateName(name: String): Boolean {
        var bandera = false
        if(TextUtils.isEmpty(name)){
            binding.txtnombreUsuario.error = "El nombre es necesario"
        }else{
            binding.txtnombreUsuario.error = ""
            binding.txtapellidoUsuario.error = ""
            bandera = true
        }

        return bandera
    }

    private fun validateLastname(lastname: String): Boolean{
        var bandera = false
       if(TextUtils.isEmpty(lastname)){
            binding.txtapellidoUsuario.error = "El apellido es necesario"
        }else{
           binding.txtapellidoUsuario.error = ""
           bandera = true
       }
        return bandera
    }

    private fun validateEmail(email: String): Boolean{
        var bandera = false
        if( !ValidateEmail.isEmail(email)){
            binding.txtcorreoUsuario.error = "Correo no valido"
        }else{
            binding.txtcorreoUsuario.error = ""
            bandera =  true
        }
        return bandera
    }

    private fun clearEntry(text: String): String{
        val text = text.replace("\\s+".toRegex(), "")
        return text
    }

}