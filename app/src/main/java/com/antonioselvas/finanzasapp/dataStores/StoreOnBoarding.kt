package com.antonioselvas.finanzasapp.dataStores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.text.get


class StoreOnBoarding(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("StoreOnBoarding")
        val STORE_ONBOARD = booleanPreferencesKey("store_onboarding")
    }

    val getOnBoarding: Flow<Boolean> = context.dataStore.data
        .catch { e ->
            e.printStackTrace()
            emit(emptyPreferences()) // evita que el flow muera por excepción
        }
        .map { prefs ->
            val value = prefs[STORE_ONBOARD] ?: false
            android.util.Log.d("StoreOnBoarding", "getOnBoarding emite: $value")
            value
        }

    suspend fun saveOnBoarding(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[STORE_ONBOARD] = value
        }
        android.util.Log.d("StoreOnBoarding", "saveOnBoarding guardó: $value")
    }
}
