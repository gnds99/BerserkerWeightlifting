package com.example.berserkerweightlifting.viewModel

import android.nfc.Tag
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

    val auth = FirebaseHelper.getConnector()
    val fireStore = FirebaseHelper.getConnectorFireStore()

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
    private val _rutina: MutableLiveData<Map<String, Any>> = MutableLiveData()
    val rutina: LiveData<Map<String, Any>> = _rutina

    var preimum = false     // VARIABLE para saber si el usuario es premium

    // VARIABLE QUE VERIFICA SI YA SE REALIZO LA CONSULTA
    val isLoading = MutableLiveData<Boolean>()

    fun StarLogin(email:String, password:String){
        isLoading.postValue(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    _login.value = Options.LOGIN
                    prefs.saveId(auth.currentUser?.email.toString())
                    this.customObjects()
                    //auth.currentUser
                    isLoading.postValue(false)
                }else{
                    _login.value = Options.ERROR
                    isLoading.postValue(false)
                }
            }
    }

    fun RegisterUser(email: String, password: String, name: String){
        val user = User(name, email, password)
        isLoading.postValue(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful)
                {
                    fireStore.collection(USER_COLLECTION).document(email).set(user)
                    prefs.saveId(auth.currentUser?.email.toString())
                    _register.value = Options.CREATE
                    isLoading.postValue(false)
                }
                else{
                    _register.value = Options.NOCREATED
                    isLoading.postValue(false)
                }
            }
    }


    fun updateUserInformation(name: String, phone: String, age: String,
        gender: String, weight: String, height: String,
    ){
        isLoading.postValue(true)
        val user = hashMapOf(
            "name" to name,
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
        isLoading.postValue(false)

    }

    fun updateUserPr(cleanJerk: String, snatch: String,powerCleanJerk: String, powerSnatch: String,
    backSquat: String, FrontSquat: String){

        isLoading.postValue(true)
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
        isLoading.postValue(false)
    }

    fun funcionPrueba(){
        _facebook.value = Options.CREATE
    }


    fun customObjects() {
        isLoading.postValue(true)
        // [START custom_objects]
        val docRef = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject<User>()
            _user.value = city!!
            isLoading.postValue(false)
        }
        // [END custom_objects]
    }

    fun signOff(): Boolean{
        _login.value = Options.LOGOUT
        isLoading.postValue(true)
        if(auth.currentUser != null){
            auth.signOut()
            _login.value = Options.LOGOUT
            isLoading.postValue(false)
            return true
        }else{
            isLoading.postValue(false)
            return false

        }
    }

    fun getRutina(numberRoutine: String){
        val rutina = "sesión-${numberRoutine}"
        isLoading.postValue(true)
        val docRef = fireStore.collection(RUTINAS_COLLECTION).document(rutina)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val ejemplo = document.data!!
                    _rutina.value = ejemplo
                    isLoading.postValue(false)
                } else {
                    Log.d(TAG, "NO EXISTE EL DOCUMENTO")
                    isLoading.postValue(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                isLoading.postValue(false)
            }
    }

    fun iniciarPruebaGratis(){
        val user = hashMapOf(
            "pruebaGratis" to true,
            "subscription" to "7"
        )
        val userColection = fireStore.collection(USER_COLLECTION).document(auth.currentUser?.email.toString())
        for ((key, value) in user) {
            userColection
                .update(key, value)
        }
    }


}