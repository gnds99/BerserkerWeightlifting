package com.example.berserkerweightlifting.viewModel

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berserkerweightlifting.core.*
import com.example.berserkerweightlifting.domain.LoginUseCase
import com.example.berserkerweightlifting.domain.RegisterUserUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class AppViewModel(): ViewModel() {

    private val auth = FirebaseHelper.getConnector()
    private val fireStore = FirebaseHelper.getConnectorFireStore()


    // CASO DE USO PARA INICIAR SESIÓN
    private var loginUseCase = LoginUseCase()
    private var registerUserCase = RegisterUserUseCase();




    // Status del login
    private val _login = MutableLiveData(Options.LOGOUT)
    val login: LiveData<Options> = _login

    // Status register
    private val _register = MutableLiveData(Options.NONE)
    val register: LiveData<Options> = _register


    fun StarLogin(email:String, password:String){
        loginUseCase.invoke(email, password)
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
    fun StarLoginWithGoogle(){

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
}