package com.example.berserkerweightlifting.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.databinding.FragmentRegistrationScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


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
        binding.btnLogin.setOnClickListener{this.goToLogin()}



        sharedViewModel.register.observe(viewLifecycleOwner){
            if(sharedViewModel.register.value == Options.CREATE){
                Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                goTohome()
            } else if (sharedViewModel.register.value == Options.NOCREATED){
                Toast.makeText(context, "Hubo un error con el registro", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRegistrar.setOnClickListener{
            val email = binding.txtCorreoUsuario.text.toString()
            val password = binding.txtPasswordUsuario.text.toString()
            val name = binding.txtNombreUsuario.text.toString()
            val lastname = binding.txtApellidoUsuario.text.toString()

            sharedViewModel.RegisterUser(email, password,name,lastname)
        }
        binding.btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            // Build a GoogleSignInClient with the options specified by gso.
            val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Toast.makeText(context, account.email.toString(), Toast.LENGTH_SHORT).show()
            this.goTohome()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           print( "signInResult:failed code=" + e.statusCode)
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