package com.vlm.wonjoonpotfolio.di

import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.data.useCase.LoginCheckUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object LoginDi {

    @Provides
    fun providesLoginCheckUseCase(
        loginRepository: LoginRepository
    ) : LoginCheckUserCase{
        return LoginCheckUserCase(loginRepository)
    }
}