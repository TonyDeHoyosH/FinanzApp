package com.antonioselvas.finanzasapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel : ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth

    var showAlert by mutableStateOf(false)

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("Error en Firebase", "Error: ${task.exception?.localizedMessage}")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun register(email: String, password: String, username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUser(username)
                            onSuccess()
                        } else {
                            Log.d("Error en Firebase", "Error: ${task.exception?.localizedMessage}")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid ?: return
        val email = auth.currentUser?.email ?: ""
        viewModelScope.launch(Dispatchers.IO) {

            val user = User(
                userId = id.toString(),
                email = email.toString(),
                name = username,
                onboardingCompleted = false
            )

            FirebaseFirestore.getInstance().collection("Users").document(id)
                .set(user)
                .addOnSuccessListener { Log.d("Saved", "Saved in Firestore") }
                .addOnFailureListener {
                    Log.d("Error al guardar en firestore", "Error while saving on firestore")
                }
        }
    }

    fun setOnboardingCompleted(onComplete: () -> Unit) {
        val id = auth.currentUser?.uid ?: return
        val userRef = FirebaseFirestore.getInstance().collection("Users").document(id)

        userRef.update("onboardingCompleted", true)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { Log.d("Error", "Failed to update onboarding flag") }
    }


    fun closeAlert() {
        showAlert = false
    }

}