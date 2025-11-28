package com.antonioselvas.finanzasapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.antonioselvas.finanzasapp.domain.models.OnboardingData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore


class OnboardingViewModel: ViewModel() {
    var tempGoal: String = ""
    var tempInitialBalance: String = ""
    var tempCategories: List<String> = emptyList()
    var tempFixedExpensesOption: String = ""
    private val auth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    fun saveOnboardingData(
        goal: String,
        initialBalance: Double,
        favoriteCategories: List<String>,
        fixedExpensesOption: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onError("Usuario no autenticado")
            return
        }

        val data = hashMapOf(
            "goal" to goal,
            "initialBalance" to initialBalance,
            "favoriteCategories" to favoriteCategories,
            "fixedExpensesOption" to fixedExpensesOption,
            "currentBalance" to initialBalance
        )

        firestore.collection("Users").document(uid)
            .update(data)
            .addOnSuccessListener {
                Log.d("OnboardingVM", "Datos guardados con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("OnboardingVM", "Error guardando datos: ${e.localizedMessage}")
                onError(e.localizedMessage ?: "Error desconocido")
            }
    }

    fun getOnboardingData(onResult: (OnboardingData?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(null)
            return
        }

        firestore.collection("Users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val data = OnboardingData(
                        goal = document.getString("goal") ?: "",
                        initialBalance = document.getDouble("initialBalance") ?: 0.0,
                        favoriteCategories = document.get("favoriteCategories") as? List<String> ?: emptyList(),
                        fixedExpensesOption = document.getString("fixedExpensesOption") ?: "",
                        currentBalance = document.getDouble("currentBalance") ?: 0.0
                    )
                    onResult(data)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun updateCurrentBalance(
        newBalance: Double,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onError("Usuario no autenticado")
            return
        }

        firestore.collection("Users").document(uid)
            .update("currentBalance", newBalance)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.localizedMessage ?: "Error desconocido") }
    }
}