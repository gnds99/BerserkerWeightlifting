package com.example.berserkerweightlifting.data.models

data class User(
    val name: String? = "",
    val email: String? = "",
    val password: String? = "",
    val phone: String = "",
    val age: String = "",
    val gender: String = "",
    val weight: String = "",
    val height: String = "",
    val clean_jerk: String = "",
    val santch: String = "",
    val powerClean_jerk: String = "",
    val powerSantc: String = "",
    val backSquat: String = "",
    val frontSquat: String = "",
    val paquete: String = "",
    val subscription: String = "0",
    val completeRoutines: String = "0"
)