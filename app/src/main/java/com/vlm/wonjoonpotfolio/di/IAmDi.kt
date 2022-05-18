package com.vlm.wonjoonpotfolio.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.iAm.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.data.iAm.IAmRemoteDataSourceImpl
import com.vlm.wonjoonpotfolio.data.iAm.IAmRemoteDataSource
import com.vlm.wonjoonpotfolio.data.iAm.IAmRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class)
object IAmDi{
    @Provides
    fun provideIamRepository(
        remoteDataSource: IAmRemoteDataSourceImpl
    ) : IAmRepository {
        return IAmRepository(remoteDataSource)
    }

    @Provides
    fun provideGetIAmDataUseCase(
        iAmRepository: IAmRepository,
    ) : GetIAmDataUseCase {
        return GetIAmDataUseCase(iAmRepository)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class IAmDataSourceModule{
    @Binds
    abstract fun bindIAmDataSource(
        iAmFirebaseApi:IAmRemoteDataSourceImpl
    ) : IAmRemoteDataSource
}

@InstallIn(ViewModelComponent::class)
@Module
object FirebaseModule{
    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}