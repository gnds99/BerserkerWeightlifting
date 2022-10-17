package com.example.berserkerweightlifting.data.network

import com.example.berserkerweightlifting.core.FirebaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class FirebaseService {
    private val auth = FirebaseHelper.getConnector()
    private var value = false
     fun loginUser(email:String, password:String): Boolean{
         auth.signInWithEmailAndPassword("noe.gnds.96@gmail.com", "1234567")
             .addOnCompleteListener{
                 if (it.isSuccessful)
                 {
                     this.setValue(true)
                     println("entre")
                 }else{
                     println("no entre")
                 }
             }
         println("MOSTRANDO SI CAMBIE EL PERRO VALOR ALV: " + this.getValue() )
         return this.getValue()
        //auth.signInWithEmailAndPassword(email, password)
    }

     fun registerUser(email:String, password:String){
         auth.createUserWithEmailAndPassword(email,password)
    }

    fun setValue(bandera: Boolean){
        value = bandera
    }
    fun getValue(): Boolean{
        return this.value
    }
}