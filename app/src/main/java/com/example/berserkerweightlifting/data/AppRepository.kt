package com.example.berserkerweightlifting.data

import com.example.berserkerweightlifting.data.network.FirebaseService

class AppRepository {

    private val service = FirebaseService()

    fun getLogin(email:String, password:String):Boolean{
        return service.loginUser(email, password)
    }

    fun getRegisterUser(email: String, password: String){
        val response = service.registerUser(email, password)
    }
}