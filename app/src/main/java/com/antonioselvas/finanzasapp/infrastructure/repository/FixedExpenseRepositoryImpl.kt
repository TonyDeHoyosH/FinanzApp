package com.antonioselvas.finanzasapp.infrastructure.repository

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.interfaces.FixedExpenseRepository
import com.antonioselvas.finanzasapp.domain.models.FixedExpense
import com.antonioselvas.finanzasapp.domain.models.Transaction
import com.antonioselvas.finanzasapp.presentation.viewModels.FixedExpenseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FixedExpenseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val financeRepository: FinanceRepository
) : FixedExpenseRepository {

    override suspend fun saveFixedExpense(expense: FixedExpense) {
        try {
            val userId = financeRepository.getCurrentUserId()
            if (userId.isEmpty()) {
                throw IllegalStateException("Usuario no autenticado")
            }

            val userFixedExpensesRef = firestore
                .collection("Users")
                .document(userId)
                .collection("fixed_expenses")

            val newDocRef = userFixedExpensesRef.document()
            val expenseWithId = expense.copy(id = newDocRef.id)

            newDocRef.set(expenseWithId).await()
            println("Gasto fijo registrado con éxito. ID: ${newDocRef.id}")

            if (expenseWithId.nextChargeDate <= System.currentTimeMillis()) {
                processFixedExpense(expenseWithId)
            }

        } catch (e: Exception) {
            println("Error al guardar el gasto fijo en Firestore: ${e.message}")
            throw e
        }
    }

    override suspend fun processFixedExpense(expense: FixedExpense) {
        try {
            val userId = financeRepository.getCurrentUserId()
            if (userId.isEmpty()) {
                throw IllegalStateException("Usuario no autenticado")
            }

            val today = System.currentTimeMillis()

            firestore.runTransaction { transaction ->
                val userRef = firestore.collection("Users").document(userId)
                val transactionsRef = userRef.collection("transactions")
                val fixedExpenseRef = userRef.collection("fixed_expenses").document(expense.id)

                val userSnapshot = transaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("currentBalance") ?: 0.0

                val newTransactionRef = transactionsRef.document()
                val newTransaction = Transaction(
                    id = newTransactionRef.id,
                    amount = expense.amount,
                    description = expense.description,
                    category = expense.category,
                    type = expense.type,
                    date = today,
                    typeTransaction = "expense"
                )
                transaction.set(newTransactionRef, newTransaction)
                println("Transacción generada para el gasto fijo: ${expense.description}")

                val newBalance = currentBalance - expense.amount
                transaction.update(userRef, "currentBalance", newBalance)

                val newNextChargeDate = FixedExpenseViewModel.calculateNextChargeDate(
                    today,
                    expense.frequency,
                    expense.chargeDay
                )

                val updates = mapOf(
                    "nextChargeDate" to newNextChargeDate
                )
                transaction.update(fixedExpenseRef, updates)
                println("Gasto fijo ${expense.id} actualizado a la próxima fecha: $newNextChargeDate")

            }.await()

        } catch (e: Exception) {
            println("Error al procesar gasto fijo: ${e.message}")
            throw e
        }
    }

    override suspend fun getDueFixedExpenses(todayTimestamp: Long): List<FixedExpense> {
        return try {
            val userId = financeRepository.getCurrentUserId()
            if (userId.isEmpty()) {
                return emptyList()
            }

            val snapshot = firestore
                .collection("Users")
                .document(userId)
                .collection("fixed_expenses")
                .whereLessThanOrEqualTo("nextChargeDate", todayTimestamp)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(FixedExpense::class.java) }

        } catch (e: Exception) {
            println("Error al obtener gastos fijos pendientes: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getFixedExpenses(): List<FixedExpense> {
        return try {
            val userId = financeRepository.getCurrentUserId()
            if (userId.isEmpty()) {
                return emptyList()
            }

            val snapshot = firestore
                .collection("Users")
                .document(userId)
                .collection("fixed_expenses")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(FixedExpense::class.java) }

        } catch (e: Exception) {
            println("Error al obtener la lista de gastos fijos: ${e.message}")
            emptyList()
        }
    }
}