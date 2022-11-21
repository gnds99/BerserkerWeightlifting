package com.example.berserkerweightlifting.sharedPreferences

import android.content.Context
import com.example.berserkerweightlifting.core.Options

class Prefs(private val context: Context) {

    private val SHARED_NAME = "MyDataBase"
    private val SHARED_EMAIL = "NO_ID_USER"
    private val DEVICE_TOKEN = "device_token"
    private val LOGIN_STATUS = false

    private val storage = context.getSharedPreferences(SHARED_NAME, 0)


    // Metodo para guarda el id del usuario
    fun saveId(id: String){
        storage.edit().putString(SHARED_EMAIL, id).apply()
    }

    fun getId(): String{
        return storage.getString(SHARED_EMAIL, "")!!
    }
    // METODO PARA GUARDAR EL CORREO DEL USUAIRO
    fun saveUuid(token:String)
    {
        storage.edit().putString(DEVICE_TOKEN, token).apply()
    }

    // METODO PARA RETORNAR EL CORREO DEL USUARIO
    fun getUuid(): String{

        return storage.getString(DEVICE_TOKEN, "")!!
    }

    fun saveLogin(status: Boolean){
        storage.edit().putBoolean(LOGIN_STATUS.toString(), status).apply()
    }

    fun getLogin(): Boolean{
        return  storage.getBoolean(LOGIN_STATUS.toString(), false)
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}