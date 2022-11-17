package com.example.berserkerweightlifting.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berserkerweightlifting.core.*
import com.example.berserkerweightlifting.data.models.User
import com.example.berserkerweightlifting.sharedPreferences.UserApplication.Companion.prefs
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch



class AppViewModel(): ViewModel() {

    private val auth = FirebaseHelper.getConnector()
    private val fireStore = FirebaseHelper.getConnectorFireStore()

    // Status del login
    private val _login = MutableLiveData(Options.NONE)
    val login: LiveData<Options> = _login

    // Status register
    private val _register = MutableLiveData(Options.NONE)
    val register: LiveData<Options> = _register

    // Status register
    private val _facebook = MutableLiveData(Options.NONE)
    val face: LiveData<Options> = _facebook

    // Current User Information
    //private val _user = MutableLiveData<String>()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _rutina: MutableLiveData<List<String>> = MutableLiveData()
    val rutina: LiveData<List<String>> = _rutina


    fun StarLogin(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    _login.value = Options.LOGIN
                    prefs.saveId(auth.currentUser?.email.toString())
                    //auth.currentUser
                }else{
                    _login.value = Options.ERROR
                }
            }
    }

    fun RegisterUser(email: String, password: String, name: String, lastname:String){
        val user = User(name, lastname, email, password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    fireStore.collection(USER_COLLECTION).document(email).set(user)
                    prefs.saveId(auth.currentUser?.email.toString())
                    _register.value = Options.CREATE
                }else{
                    _register.value = Options.NOCREATED
                }
            }
    }

    fun updateUserInformation(name: String, lastname: String, phone: String, age: String,
        gender: String, weight: String, height: String,
    ){
        val user = hashMapOf(
            "name" to name,
            "lastname" to lastname,
            "phone" to phone,
            "age" to age,
            "gender" to gender,
            "weight" to weight,
            "height" to height
        )
        val userColection = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())

        for ((key, value) in user) {
            userColection
                .update(key, value)
        }

    }

    fun updateUserPr(cleanJerk: String, snatch: String,powerCleanJerk: String, powerSnatch: String,
    backSquat: String, FrontSquat: String){


        val user = hashMapOf(
            "clean_jerk" to cleanJerk,
            "santch" to snatch,
            "powerClean_jerk" to powerCleanJerk,
            "powerSantc" to powerSnatch,
            "backSquat" to backSquat,
            "frontSquat" to FrontSquat
        )
        val userColection = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())
        for ((key, value) in user) {
            userColection
                .update(key, value)
        }
    }

    fun funcionPrueba(){
        _facebook.value = Options.CREATE
    }

    fun getUserInformation(){

        val collection = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())
        viewModelScope.launch {
            collection.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Document found in the offline cache
                    val document = task.result
                    val user = hashMapOf(
                        "name" to document?.data?.get("name"),
                        "lastname" to document?.data?.get("lastname"),
                        "phone" to document?.data?.get("phone"),
                        "age" to document?.data?.get("age"),
                        "gender" to document?.data?.get("gender"),
                        "weight" to document?.data?.get("weight"),
                        "height" to document?.data?.get("height")
                    )

                } else {
                    Log.d(TAG, "Cached get failed: ", task.exception)
                }
            }
        }

    }

    fun customObjects() {
        // [START custom_objects]
        val docRef = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject<User>()
            _user.value = city!!
        }
        // [END custom_objects]
    }

    fun signOff(): Boolean{
        _login.value = Options.LOGOUT

        /*if(auth.currentUser != null){
            //auth.signOut()
            _login.value = Options.LOGOUT
            return true
        }else{
            return false
        }*/
        return true
    }

    fun getRutina(numberRoutine: String){
        val rutina = "sesión-${numberRoutine}"
        val docRef = fireStore.collection(RUTINAS_COLLECTION).document(rutina)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data.toString()
                    val rutina = data.split(",")
                    _rutina.value = rutina
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "NO EXISTE EL DOCUMENTO")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

}