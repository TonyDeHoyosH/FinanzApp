package com.antonioselvas.finanzasapp.di

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.infrastructure.repository.FinanceRepositoryImpl
import com.antonioselvas.finanzasapp.infrastructure.repository.splitAccountRepositoryImpl
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
    ): SplitAccountRepository = splitAccountRepositoryImpl(firestore)
}