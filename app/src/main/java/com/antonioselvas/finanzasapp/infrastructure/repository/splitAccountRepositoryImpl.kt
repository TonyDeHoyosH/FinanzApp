package com.antonioselvas.finanzasapp.infrastructure.repository

import android.util.Log
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.domain.models.SplitAccountInfo
import com.antonioselvas.finanzasapp.domain.models.SplitAccountTransaction
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class splitAccountRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): SplitAccountRepository {
    override suspend fun addSplitAccount(
        uid: String,
        splitAccountTransaction: SplitAccountTransaction
    ): Result<Unit> {
        if (splitAccountTransaction.typeTransaction != "expense") {
            return Result.failure(IllegalStateException("SplitAccount debe ser de tipo 'expense' para esta operación."))
        }

        val expenseAmount = splitAccountTransaction.amount

        return try {
            firestore.runTransaction { firestoreTransaction ->
                val userRef = firestore.collection("Users").document(uid)
                val transactionsRef = userRef.collection("transactions")

                val userSnapshot = firestoreTransaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("currentBalance") ?: 0.0

                val newBalance = currentBalance - expenseAmount


                firestoreTransaction.update(userRef, "currentBalance", newBalance)

                firestoreTransaction.set(transactionsRef.document(), splitAccountTransaction)

            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSplitAccounts(uid: String): List<SplitAccountInfo> {
        return try {
            firestore.collection("Users")
                .document(uid)
                .collection("transactions")
                .whereIn("divisionForm", listOf("Personalizado", "Equitativo"))
                .get()
                .await()
                .map { document ->
                    val transaction = document.toObject(SplitAccountTransaction::class.java)

                    Log.d("SplitRepo", transaction.toString() )

                    SplitAccountInfo(
                        id = document.id,
                        amount = transaction.amount,
                        description = transaction.description,
                        category = transaction.category,
                        type = transaction.type,
                        date = transaction.date,
                        divisionForm = transaction.divisionForm,
                        usersNumber = transaction.users.size,
                        typeTransaction = transaction.typeTransaction
                    )

                }
        }catch (e: Exception) {
            Log.e("FinanceRepo", "Error al obtener SplitAccounts: ${e.message}", e)
            emptyList()
        }
    }


}