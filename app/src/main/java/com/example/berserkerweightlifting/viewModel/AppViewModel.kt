package com.example.berserkerweightlifting.viewModel

import android.content.Context
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.core.*
import com.example.berserkerweightlifting.domain.LoginUseCase
import com.example.berserkerweightlifting.domain.RegisterUserUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class AppViewModel(): ViewModel() {

    private val auth = FirebaseHelper.getConnector()
    private val fireStore = FirebaseHelper.getConnectorFireStore()

    // Status del login
    private val _login = MutableLiveData(Options.LOGOUT)
    val login: LiveData<Options> = _login

    // Status register
    private val _register = MutableLiveData(Options.NONE)
    val register: LiveData<Options> = _register

    // Status register
    private val _facebook = MutableLiveData(Options.NONE)
    val face: LiveData<Options> = _facebook


    fun StarLogin(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    _login.value = Options.LOGIN
                }else{
                    _login.value = Options.ERROR
                }
            }
    }

    fun RegisterUser(email: String, password: String, lastname: String, name:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    fireStore.collection(USER_COLLECTION).document().set(hashMapOf(
                        EMAIL to email,
                        PASSWORD to password,
                        LASTNAME to lastname,
                        NAME to name
                    ))
                    _register.value = Options.CREATE
                }else{
                    _register.value = Options.NOCREATED

                }
            }
    }

    fun funcionPrueba(){
        _facebook.value = Options.CREATE
    }
}