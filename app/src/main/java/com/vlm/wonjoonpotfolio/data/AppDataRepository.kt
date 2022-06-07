package com.vlm.wonjoonpotfolio.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.domain.PreferencesKey
import com.vlm.wonjoonpotfolio.domain.PreferencesKey.LOCALE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AppDataRepository
@Inject
constructor(
    private val appDataStore : DataStore<Preferences>,
    private val firebaseCrashlytics: FirebaseCrashlytics
)
{
    companion object{
        const val TAG = "AppDataRepository"
    }

    fun getAppSettingData() = flow{
        try {
            val locale = appDataStore.data.first()[LOCALE]
            emit(locale)
        }catch (e:Exception){
            firebaseCrashlytics.log(TAG + " : ${e.message?: "I don't know why"}")
        }
    }

    suspend fun setLocale(locale : String) {
        try {
            appDataStore.edit { preferences ->
                preferences[LOCALE] = locale
            }
        }catch (e:Exception){

        }
    }
}