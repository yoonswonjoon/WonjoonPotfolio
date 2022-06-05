package com.vlm.wonjoonpotfolio.di

import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.data.useCase.LoginCheckUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

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
}