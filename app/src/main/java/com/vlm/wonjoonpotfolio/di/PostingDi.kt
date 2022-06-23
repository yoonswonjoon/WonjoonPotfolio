package com.vlm.wonjoonpotfolio.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRemoteDataSourceImpl
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.data.posting.GetPostingPagingDataUseCase
import com.vlm.wonjoonpotfolio.data.posting.PostingDataSource
import com.vlm.wonjoonpotfolio.data.posting.PostingRepository
import com.vlm.wonjoonpotfolio.data.posting.UpLoadPostingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object PostingDi {


    @Singleton
    @Provides
    fun providePostingDataSource(
        firebaseFirestore: FirebaseFirestore,
    ) : PostingDataSource {
        return PostingDataSource(
            firebaseFirestore = firebaseFirestore,
        )
    }

    @Singleton
    @Provides
    fun providePostingRepository(
        firebaseFirestore: FirebaseFirestore,
        postingDataSource: PostingDataSource
    ) : PostingRepository {
        return PostingRepository(
            firebaseFirestore = firebaseFirestore,
            postingDataSource = postingDataSource
        )
    }

    @Singleton
    @Provides
    fun provideGetPostingPagingDataUseCase(
        postingRepository: PostingRepository,
        imgDataRepository: ImgDataRepository
    ) : GetPostingPagingDataUseCase {
        return GetPostingPagingDataUseCase(
            postingRepository, imgDataRepository
        )
    }

    @Singleton
    @Provides
    fun provideUpLoadPostingUseCase(postingRepository: PostingRepository
    ) : UpLoadPostingUseCase {
        return UpLoadPostingUseCase(postingRepository)
    }

}