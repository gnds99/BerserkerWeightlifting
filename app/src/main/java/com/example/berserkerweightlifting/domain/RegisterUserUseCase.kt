package com.example.berserkerweightlifting.domain

import com.example.berserkerweightlifting.data.AppRepository

class RegisterUserUseCase {
    private val repository = AppRepository()

    fun invoke(email:String, password:String){
        repository.getRegisterUser(email, password)
    }
}