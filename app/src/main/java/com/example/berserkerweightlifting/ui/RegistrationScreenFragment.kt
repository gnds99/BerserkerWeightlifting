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
import com.example.berserkerweightlifting.core.RC_SIGN_IN
import com.example.berserkerweightlifting.databinding.FragmentRegistrationScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentRegistrationScreenBinding? = null
    private val binding get() = _binding!!

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // [END config_signin]
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

    }

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
                goToHome()
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
            this.signIn()
        }


    }

    // [ CONFIGURANDO EL LOGIN CON GOOGLE ]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                println( "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                println("Google sign in failed" + e)
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    println("signInWithCredential:success")
                    val user = auth.currentUser
                    this.goToHome()
                } else {
                    // If sign in fails, display a message to the user.
                    println( "signInWithCredential:failure" + task.exception)
                }
            }
    }
    // [END auth_with_google]

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    // [ TERMINANDO LA CONFIGURACION DE LOGIN CON GOOGLE]



    private fun goToLogin(){
        val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToHome(){
        //val action = RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToHomeScreenFragment()
        findNavController().navigate(R.id.homeScreenFragment)
    }


}