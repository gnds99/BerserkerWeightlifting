package com.example.berserkerweightlifting.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.core.RC_SIGN_IN
import com.example.berserkerweightlifting.core.TAG
import com.example.berserkerweightlifting.core.USER_COLLECTION
import com.example.berserkerweightlifting.data.models.User
import com.example.berserkerweightlifting.data.models.ValidateEmail
import com.example.berserkerweightlifting.databinding.FragmentLoginScreenBinding
import com.example.berserkerweightlifting.sharedPreferences.UserApplication.Companion.prefs
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    private lateinit var googleSignInClient: GoogleSignInClient
    private  var  email: String = ""

    private lateinit var savedStateHandle: SavedStateHandle



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
        auth =   sharedViewModel.auth //Firebase.auth
        // [END initialize_auth]

    }

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
        Log.e(TAG, "Aqui estamos" + prefs.getLogin().toString())
        if(!prefs.getLogin() ){
            findNavController().navigate(R.id.slashScreenFragment)
        }

        sharedViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        binding.btnCrearCuenta.setOnClickListener { goToRegistration() }
        binding.btnRecuperarContrasenia.setOnClickListener {
            this.goToReset()
        }


        sharedViewModel.login.observe(viewLifecycleOwner){
            if(sharedViewModel.login.value == Options.LOGIN){
                prefs.saveId(email)
                Toast.makeText(context, prefs.getId(), Toast.LENGTH_SHORT).show()
                this.goToHome()
            }else if (sharedViewModel.login.value == Options.ERROR){
                Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(tag, "Hola: " + sharedViewModel.login.value.toString())
            }
        }

        binding.btnFacebook.setOnClickListener {
            sharedViewModel.funcionPrueba()
        }

        binding.btnIngresar.setOnClickListener {
            email = clearEntry(binding.txtCorreoUsuario.editText?.text.toString())
            val password = clearEntry(binding.txtPasswordUsuario.editText?.text.toString())
            val validEmail = validateEmail(email)
            val validPassword = validatePassword(password)
            if(validEmail && validPassword){
                sharedViewModel.StarLogin(email, password)
            }
        }

        binding.btnGoogle.setOnClickListener {
            this.signIn()
        }

    }




    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                println("Google sign in failed" + e)
            }
        }
    }



    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user!!.providerId
                    Log.i(TAG, "Provider: " + user.providerId )
                    val name = user.displayName!!
                    val email = user.email!!
                    val data = User(name, email)

                    val docRef = sharedViewModel.fireStore.collection(USER_COLLECTION).document(email)

                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document.data == null) {
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                                sharedViewModel.fireStore.collection(USER_COLLECTION).document(email).set(data)
                                this.goToHome()
                            } else {
                                Log.d(TAG, "DocumentSnapshot data else: ${document.data}")
                                this.goToHome()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }

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

    private fun goToRegistration(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToRegistrationScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToReset(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToResetScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToHome(){
        findNavController().navigate(R.id.profileScreenFragment)
    }

    private fun validateEmail(email: String): Boolean{

        if( !ValidateEmail.isEmail(email)){
            binding.txtCorreoUsuario.error = "Correo no valido"
            binding.txtPasswordUsuario.error = "La contraseña es necesaria"
            return false
        }else{
            binding.txtCorreoUsuario.error = ""
            return true
        }
    }

    private fun validatePassword( password: String): Boolean{

        if(TextUtils.isEmpty(password)){
            binding.txtPasswordUsuario.error = "La contraseña es necesaria"
            return false
        }else{
            binding.txtPasswordUsuario.error = ""
            return true
        }

    }

    private fun clearEntry(text: String): String{
        val text = text.replace("\\s+".toRegex(), "")
        return text
    }

}

