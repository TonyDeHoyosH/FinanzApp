package com.antonioselvas.finanzasapp.dataStores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.text.get


class StoreOnBoarding(private val context: Context) {
    companion object {
        private val Context.dataStore:
                DataStore<Preferences> by preferencesDataStore("StoreOnBoarding")
        val STORE_ONBOARD = booleanPreferencesKey("store_onboarding")
    }

    val getOnBoarding: Flow<Boolean> = context.dataStore.data
        .map {
            it[STORE_ONBOARD] == true
        }

    suspend fun saveOnBoarding(value: Boolean){
        context.dataStore.edit {
            it[STORE_ONBOARD] = value
        }
    }
}