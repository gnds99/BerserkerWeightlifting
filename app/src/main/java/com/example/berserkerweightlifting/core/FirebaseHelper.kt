package com.example.berserkerweightlifting.core

import android.provider.Settings.Global.getString
import com.example.berserkerweightlifting.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper{

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    /*private val signInRequest = BeginSignInRequest.builder()
    .setGoogleIdTokenRequestOptions(
    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
    .setSupported(true)
    // Your server's client ID, not your Android client ID.
    .setServerClientId(getString(R.string.your_web_client_id))
    // Only show accounts previously used to sign in.
    .setFilterByAuthorizedAccounts(true)
    .build())
    .build()*/

    fun getConnector(): FirebaseAuth{
        return mAuth
    }

    fun getConnectorFireStore(): FirebaseFirestore{
        return fireStore
    }


}