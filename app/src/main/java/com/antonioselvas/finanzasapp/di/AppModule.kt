package com.antonioselvas.finanzasapp.di

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.interfaces.FixedExpenseRepository
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.infrastructure.repository.FinanceRepositoryImpl
import com.antonioselvas.finanzasapp.infrastructure.repository.FixedExpenseRepositoryImpl
import com.antonioselvas.finanzasapp.infrastructure.repository.SplitAccountRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFinanceRepository(
        firestore: FirebaseFirestore
    ): FinanceRepository = FinanceRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideSplitAccountRepository(
        firestore: FirebaseFirestore
    ): SplitAccountRepository = SplitAccountRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFixedExpenseRepository(
        firestore: FirebaseFirestore,
        financeRepository: FinanceRepository
    ): FixedExpenseRepository = FixedExpenseRepositoryImpl(firestore, financeRepository)
}