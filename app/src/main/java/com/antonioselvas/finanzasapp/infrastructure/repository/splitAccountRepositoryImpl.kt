package com.antonioselvas.finanzasapp.infrastructure.repository

import android.util.Log
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
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

                val newTransactionRef = transactionsRef.document()
                val newId = newTransactionRef.id

                val transactionWithId = splitAccountTransaction.copy(
                    id = newId
                )

                val userSnapshot = firestoreTransaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("currentBalance") ?: 0.0

                val newBalance = currentBalance - expenseAmount


                firestoreTransaction.update(userRef, "currentBalance", newBalance)

                firestoreTransaction.set(newTransactionRef, transactionWithId)

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

                    Log.d("SplitRepo", "Raw data: ${document.data}")
                    val transaction = document.toObject(SplitAccountTransaction::class.java)

                    Log.d("SplitRepo", "Mapped transaction: $transaction")

                    Log.d("SplitRepo", transaction.toString())

                    SplitAccountInfo(
                        id = document.id,
                        amount = transaction.amount,
                        description = transaction.description,
                        category = transaction.category,
                        type = transaction.type,
                        date = transaction.date,
                        divisionForm = transaction.divisionForm,
                        usersNumber = transaction.users.size,
                        typeTransaction = transaction.typeTransaction,
                        isCompleted = transaction.isCompleted
                    )

                }
        }catch (e: Exception) {
            Log.e("FinanceRepo", "Error al obtener SplitAccounts: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun getSplitAccountDetails(id: String, uid: String): SplitAccountTransaction? {
        return try {
            firestore.collection("Users")
                .document(uid)
                .collection("transactions")
                .document(id)
                .get()
                .await()
                .toObject(SplitAccountTransaction::class.java)

        }catch (e: Exception) {
            Log.e("FinanceRepo", "Error al obtener SplitAccounts: ${e.message}", e)
            return null
        }
    }

    override suspend fun updateSplitAccountUser(
        transactionId: String,
        uid: String,
        debtorUserId: String,
        newPaidAmount: Double?
    ): Result<Unit> {
        return try {
            firestore.runTransaction { firestoreTransaction ->
                val userRef = firestore.collection("Users").document(uid)
                val transactionsRef = userRef.collection("transactions")
                val transactionDocRef = transactionsRef.document(transactionId)

                val transactionSnapshot = firestoreTransaction.get(transactionDocRef)

                val currentTransaction = transactionSnapshot.toObject(SplitAccountTransaction::class.java)
                    ?: throw IllegalStateException("Transaction not found or invalid.")

                var paymentDelta = 0.0

                val updatedUsers = currentTransaction.users.map { user ->
                    if (user.id == debtorUserId) {
                        val amountDue = user.amount
                        val currentPaidAmount = user.paidAmount

                        val newPaidState = !user.paid

                        val amountToRegister: Double

                        if (newPaidState) {
                            amountToRegister = amountDue
                        } else {
                            amountToRegister = 0.0
                        }

                        paymentDelta = amountToRegister - currentPaidAmount


                        return@map user.copy(
                            paidAmount = amountToRegister,
                            paid = newPaidState,
                        )
                    }

                    user
                }
                    val allUsersPaid = updatedUsers.all { it.paid }

                val transactionUpdates = mutableMapOf<String, Any>()
                transactionUpdates["users"] = updatedUsers

                if (allUsersPaid && !currentTransaction.isCompleted) {
                    transactionUpdates["isCompleted"] = true
                }else if (!allUsersPaid && currentTransaction.isCompleted) {
                    transactionUpdates["isCompleted"] = false
                }

                val userBalanceSnapshot = firestoreTransaction.get(userRef)
                val currentBalance = userBalanceSnapshot.getDouble("currentBalance") ?: 0.0


                val newBalance = currentBalance + paymentDelta

                firestoreTransaction.update(transactionDocRef, transactionUpdates as Map<String, Any>)
                firestoreTransaction.update(userRef, "currentBalance", newBalance)

            }.await()


            Result.success(Unit)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addUserToSplitAccount(
        uid: String,
        transactionId: String,
        newUser: SplitAccount
    ): Result<Unit> {
        return try {
            firestore.runTransaction { firestoreTransaction ->
                val userRef = firestore.collection("Users").document(uid)
                val transactionDocRef = userRef.collection("transactions").document(transactionId)

                val transactionSnapshot = firestoreTransaction.get(transactionDocRef)
                val currentTransaction = transactionSnapshot.toObject(SplitAccountTransaction::class.java)
                    ?: throw IllegalStateException("Transaction not found or invalid.")

                val updatedUsers = currentTransaction.users.toMutableList().apply {
                    val newId = (size + 1).toString()
                    add(newUser.copy(id = newId))
                }

                val newTotalAmount = currentTransaction.amount + newUser.amount

                val transactionUpdates = mapOf(
                    "users" to updatedUsers,
                    "amount" to newTotalAmount,
                    "isCompleted" to false
                )
                firestoreTransaction.update(transactionDocRef, transactionUpdates)

            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("SplitRepo", "Error al agregar usuario: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun removeUserFromSplitAccount(
        uid: String,
        transactionId: String,
        debtorUserId: String
    ): Result<Unit> {
        return try {
            firestore.runTransaction { firestoreTransaction ->
                val userRef = firestore.collection("Users").document(uid)
                val transactionDocRef = userRef.collection("transactions").document(transactionId)

                val transactionSnapshot = firestoreTransaction.get(transactionDocRef)
                val currentTransaction = transactionSnapshot.toObject(SplitAccountTransaction::class.java)
                    ?: throw IllegalStateException("Transaction not found or invalid.")

                    val userToRemove = currentTransaction.users.find { it.id == debtorUserId }
                    ?: throw IllegalStateException("User not found in transaction.")

                if (userToRemove.paidAmount > 0.0) {
                    throw IllegalStateException("No se puede eliminar un usuario con pagos registrados.")
                }

                val updatedUsers = currentTransaction.users.filter { it.id != debtorUserId }

                val newTotalAmount = currentTransaction.amount - userToRemove.amount

                val newIsCompleted = updatedUsers.all { it.paid }

                val transactionUpdates = mapOf(
                    "users" to updatedUsers,
                    "amount" to newTotalAmount,
                    "isCompleted" to newIsCompleted
                )
                firestoreTransaction.update(transactionDocRef, transactionUpdates)

            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("SplitRepo", "Error al eliminar usuario: ${e.message}", e)
            Result.failure(e)
        }
    }


}