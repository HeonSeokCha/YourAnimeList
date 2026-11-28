package com.chs.youranimelist.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStorePrefManager(val dataStore: DataStore<Preferences>) {
    suspend inline fun <reified T> putData(
        keyName: String,
        value: T
    ) {
        dataStore.edit { preferences ->
            when (T::class) {
                String::class -> {
                    val prefKey = stringPreferencesKey(keyName)
                    preferences[prefKey] = value as String
                }

                Int::class -> {
                    val prefKey = intPreferencesKey(keyName)
                    preferences[prefKey] = value as Int
                }

                Long::class -> {
                    val prefKey = longPreferencesKey(keyName)
                    preferences[prefKey] = value as Long
                }

                Float::class -> {
                    val prefKey = floatPreferencesKey(keyName)
                    preferences[prefKey] = value as Float
                }

                Double::class -> {
                    val prefKey = doublePreferencesKey(keyName)
                    preferences[prefKey] = value as Double
                }

                Boolean::class -> {
                    val prefKey = booleanPreferencesKey(keyName)
                    preferences[prefKey] = value as Boolean
                }

                else -> {
                    throw IllegalArgumentException("${T::class.simpleName}")
                }
            }
        }
    }

    inline fun <reified T> getData(
        keyName: String,
        defaultValue: T
    ): Flow<T> = dataStore.data
        .catch { e ->
            emit(emptyPreferences())
        }.map { preferences ->
            val prefKey = when (T::class) {
                String::class -> stringPreferencesKey(keyName)
                Int::class -> intPreferencesKey(keyName)
                Long::class -> longPreferencesKey(keyName)
                Float::class -> floatPreferencesKey(keyName)
                Double::class -> doublePreferencesKey(keyName)
                Boolean::class -> booleanPreferencesKey(keyName)
                else -> throw IllegalArgumentException("${T::class.simpleName}")
            }

            (preferences[prefKey] ?: defaultValue) as T
        }
}
