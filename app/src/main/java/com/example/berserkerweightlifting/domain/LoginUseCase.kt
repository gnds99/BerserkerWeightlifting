package com.example.berserkerweightlifting.domain

import com.example.berserkerweightlifting.data.AppRepository

class LoginUseCase {
    private val repository = AppRepository()
    fun invoke(email:String, password:String): Boolean{

        return repository.getLogin(email, password)

    }
}