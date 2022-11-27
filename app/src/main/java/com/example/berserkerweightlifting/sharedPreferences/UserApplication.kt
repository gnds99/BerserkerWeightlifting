package com.example.berserkerweightlifting.sharedPreferences

import android.app.Application
import java.util.*

class UserApplication: Application() {

    // Constante que contiene la instancia que almacena todos los valores de la aplicacion
    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)

        if(prefs.getUuid().isEmpty()){
            prefs.saveUuid(UUID.randomUUID().toString())
        }
    }
}