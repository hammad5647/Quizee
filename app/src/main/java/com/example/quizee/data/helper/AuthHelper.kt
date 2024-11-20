package com.example.quizee.data.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthHelper {
    companion object {
        var authHelper = AuthHelper()
    }

    var auth = FirebaseAuth.getInstance()
    var user: FirebaseUser? = null
    var message: String? = null

    suspend fun signUp(email: String, password: String): String? {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.e("signedUp", "signUp:==========:$it ")
                message = "Success"
            }.addOnFailureListener {
                message = it.message
            }.await()
        } catch (e: FirebaseAuthException) {
            message = "${e.message}"
        }
        return message
    }

    suspend fun signIn(email: String, password: String): String? {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.e("signedIn", "signIp:==========:$it ")
                message = "Success"
            }.addOnFailureListener {
                message = it.message
            }.await()
        } catch (e: FirebaseAuthException) {
            message = "${e.message}"
        }
        return message
    }

    fun logOut(){
        auth.signOut()
    }
    fun currentUser(){
        user = auth.currentUser
    }
}