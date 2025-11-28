package com.antonioselvas.finanzasapp.data.repository

import android.util.Log
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class FinanceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FinanceRepository {

    override suspend fun getCurrentBalance(uid: String): Double {
        return try {
            val doc = firestore.collection("Users").document(uid).get().await()
            doc.getDouble("currentBalance") ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    override suspend fun getCurrentUserId(): String {
        return try {
            val user = FirebaseAuth.getInstance().currentUser
            user?.uid ?: ""
        } catch (e: Exception) {
            Log.e("GetUserID", "Error al obtener UserID: ${e.message}", e)
            ""
        }
    }


    override suspend fun addExpense(uid: String, expense: Expense): Result<Unit> {
        return try {
            // 1. Guardar gasto
            firestore.collection("Users")
                .document(uid)
                .collection("expenses")
                .add(expense)
                .await()

            // 2. Actualizar balance
            val currentBalance = getCurrentBalance(uid)
            firestore.collection("Users")
                .document(uid)
                .update("currentBalance", currentBalance - expense.amount)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpensesForChart(uid: String, fromDate: Long, toDate: Long): List<Expense> {
        return firestore.collection("Users").document(uid)
            .collection("expenses")
            .whereGreaterThan("date", fromDate)
            .whereLessThan("date", toDate)
            .get()
            .await()
            .toObjects(Expense::class.java)
    }


}
