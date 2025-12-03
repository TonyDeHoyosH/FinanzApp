package com.antonioselvas.finanzasapp.data.repository

import android.util.Log
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.Transaction
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


    override suspend fun addExpense(uid: String, transaction: Transaction): Result<Unit> {
        return try {
            // 1. Guardar gasto
            firestore.collection("Users")
                .document(uid)
                .collection("transactions")
                .add(transaction)
                .await()

            // 2. Actualizar balance
            val currentBalance = getCurrentBalance(uid)
            firestore.collection("Users")
                .document(uid)
                .update("currentBalance", currentBalance - transaction.amount)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpensesForChart(
        uid: String,
        fromDate: Long,
        toDate: Long
    ): List<Transaction> {
        return firestore.collection("Users").document(uid)
            .collection("transactions")
            .whereGreaterThan("date", fromDate)
            .whereLessThan("date", toDate)
            .get()
            .await()
            .toObjects(Transaction::class.java)
    }


    override suspend fun addIncome(
        uid: String,
        transaction: Transaction
    ): Result<Unit> {
        return try {
            firestore.runTransaction { firestoreTransaction ->
                val userRef = firestore.collection("Users").document(uid)

                val userSnapshot = firestoreTransaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("currentBalance") ?: 0.0

                val newBalance = currentBalance + transaction.amount

                firestoreTransaction.update(userRef, "currentBalance", newBalance)
            }.await()

            firestore.collection("Users")
                .document(uid)
                .collection("transactions")
                .add(transaction)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getUserName(uid: String): String {
        return try {
            val doc = firestore.collection("Users").document(uid).get().await()

            doc.getString("name") ?: "Usuario"
        } catch (e: Exception) {
            "Usuario"
        }
    }

    override suspend fun getLastFiveExpenses(uid: String): List<Transaction> {
        return try {

            firestore.collection("Users")
                .document(uid)
                .collection("transactions")
                .limit(5)
                .get()
                .await()
                .map { document ->
                    document.toObject(Transaction::class.java).copy(id = document.id)
                }
        } catch (e: Exception) {
            Log.e("FinanceRepo", "Error getting expenses: ${e.message}")
            emptyList()
        }
    }


}
