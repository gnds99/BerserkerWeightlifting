package com.example.berserkerweightlifting.core

import android.provider.Settings.Global.getString
import com.example.berserkerweightlifting.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper{

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    fun getConnector(): FirebaseAuth{
        return mAuth
    }

    fun getConnectorFireStore(): FirebaseFirestore{
        return fireStore
    }


}