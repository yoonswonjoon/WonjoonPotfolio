package com.vlm.wonjoonpotfolio.di

import com.vlm.wonjoonpotfolio.data.iAm.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.data.iAm.IAmFirebaseApi
import com.vlm.wonjoonpotfolio.data.iAm.IAmRemoteDataSource
import com.vlm.wonjoonpotfolio.data.iAm.IAmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

//@Module
//@InstallIn(ActivityComponent::class)
//abstract class IAmInterfaceDi {
//
//    @Binds
//    abstract fun bindIAmRemoteDataSource(
//        iAmFirebaseApi: IAmFirebaseApi
//    ): IAmRemoteDataSource
//}

@Module//(includes = [IAmDi.Test::class])
@InstallIn(ActivityComponent::class)
object IAmDi{

//    @Provides
//    fun provideFirebaseFirestore() : FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }


//    @Provides
//    fun provideIamRemoteDataSource(
//        firestore: FirebaseFirestore
//    ) : IAmFirebaseApi {
//        return IAmFirebaseApi(firestore)
//    }

    /** 여기 할때 문제가 생김 bind 사용해야하나봄
     * */
    @Provides
    fun provideIamRemoteDataSource(
        /*firestore: FirebaseFirestore*/
    ) : IAmFirebaseApi {
        return IAmFirebaseApi(/*firestore*/)
    }
//
//    @Module
//    @InstallIn(SingletonComponent::class)
//    interface Test{
//        @Binds
//        fun bindIamRemoteDataSource(iAmFirebaseApi : IAmFirebaseApi) : IAmFirebaseApi
//    }

    @Provides
    fun provideIamRepository(
        remoteDataSource: IAmRemoteDataSource
    ) : IAmRepository = IAmRepository(remoteDataSource)

    @Provides
    fun provideGetIAmDataUseCase(
        iAmRepository: IAmRepository
    ) : GetIAmDataUseCase = GetIAmDataUseCase(iAmRepository)
}