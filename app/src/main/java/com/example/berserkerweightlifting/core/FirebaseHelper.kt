package com.example.berserkerweightlifting.core

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