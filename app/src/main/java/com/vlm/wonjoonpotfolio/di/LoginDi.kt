package com.vlm.wonjoonpotfolio.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.data.AppDataRepository
import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.data.useCase.GetCurrentUser
import com.vlm.wonjoonpotfolio.data.useCase.LoginCheckUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton
import kotlin.math.log

@Module
@InstallIn(ActivityComponent::class)
object LoginDi {
    @JvmStatic
    @Singleton
    @Provides
    fun providesLoginCheckUseCase(
        loginRepository: LoginRepository
    ) : LoginCheckUserCase{
        return LoginCheckUserCase(loginRepository)
    }

    @Singleton
    @Provides
    fun providesAppDataRepository(
        appDataStore: DataStore<Preferences>,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = AppDataRepository(appDataStore,firebaseCrashlytics)

    @Singleton
    @Provides
    fun providesGetCurrentUser(
        loginRepository: LoginRepository
    ) : GetCurrentUser = GetCurrentUser(loginRepository)
}