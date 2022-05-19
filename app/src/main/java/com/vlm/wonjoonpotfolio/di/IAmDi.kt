package com.vlm.wonjoonpotfolio.di

import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataSource
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataSourceImpl
import com.vlm.wonjoonpotfolio.data.useCase.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRemoteDataSourceImpl
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRemoteDataSource
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.data.useCase.GetMutlipleIAmDataUsecase
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
        remoteDataSource: IAmTextDataRemoteDataSourceImpl
    ) : IAmTextDataRepository {
        return IAmTextDataRepository(remoteDataSource)
    }

    @Provides
    fun provideImgRepository(
        imgDataSource: ImgDataSourceImpl
    ) : ImgDataRepository{
        return ImgDataRepository(imgDataSource)
    }

    @Provides
    fun provideGetIAmDataUseCase(
        iAmRepository: IAmTextDataRepository,
        imgDataRepository: ImgDataRepository
    ) : GetIAmDataUseCase {
        return GetIAmDataUseCase(iAmRepository,imgDataRepository)
    }

    @Provides
    fun provideGetMultiIAmDataUseCase(
        iAmRepository: IAmTextDataRepository,
        imgDataRepository: ImgDataRepository
    ) : GetMutlipleIAmDataUsecase {
        return GetMutlipleIAmDataUsecase(iAmRepository,imgDataRepository)
    }


}

@Module
@InstallIn(ViewModelComponent::class)
abstract class IAmDataSourceModule{
    @Binds
    abstract fun bindIAmDataSource(
        iAmFirebaseApi: IAmTextDataRemoteDataSourceImpl
    ) : IAmTextDataRemoteDataSource


    @Binds
    abstract fun bindImgDataSource(
        imgDataSource: ImgDataSourceImpl
    ) : ImgDataSource
}

