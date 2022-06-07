package com.vlm.wonjoonpotfolio.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataSource
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataSourceImpl
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRemoteDataSourceImpl
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRemoteDataSource
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.data.login.LoginDataSource
import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.data.project.ProjectDataSourceImpl
import com.vlm.wonjoonpotfolio.data.project.ProjectRepository
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateDataSource
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateDataSourceImpl
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateRepository
import com.vlm.wonjoonpotfolio.data.useCase.*
import com.vlm.wonjoonpotfolio.data.user.UserDataSource
import com.vlm.wonjoonpotfolio.data.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object IAmDi{
    @JvmStatic
    @Singleton
    @Provides
    fun provideIamRepository(
        remoteDataSource: IAmTextDataRemoteDataSourceImpl
    ) : IAmTextDataRepository {
        return IAmTextDataRepository(remoteDataSource)
    }
    @JvmStatic
    @Singleton
    @Provides
    fun provideImgRepository(
        imgDataSource: ImgDataSourceImpl
    ) : ImgDataRepository{
        return ImgDataRepository(imgDataSource)
    }
    @JvmStatic
    @Singleton
    @Provides
    fun provideGetIAmDataUseCase(
        iAmRepository: IAmTextDataRepository,
        imgDataRepository: ImgDataRepository,
        firebaseCrashlytics: FirebaseCrashlytics
    ) : GetIAmDataUseCase {
        return GetIAmDataUseCase(iAmRepository,imgDataRepository,firebaseCrashlytics)
    }
    @JvmStatic
    @Singleton
    @Provides
    fun provideGetMultiIAmDataUseCase(
        iAmRepository: IAmTextDataRepository,
        imgDataRepository: ImgDataRepository
    ) : GetMutlipleIAmDataUsecase {
        return GetMutlipleIAmDataUsecase(iAmRepository,imgDataRepository)
    }
    @JvmStatic
    @Singleton
    @Provides
    fun provideUserDataSource(
        firebase : FirebaseFirestore
    ) = UserDataSource(firebase)
    @JvmStatic
    @Singleton
    @Provides
    fun providesUserRepository(
        userDataSource: UserDataSource
    ) = UserRepository(userDataSource)
    @JvmStatic
    @Singleton
    @Provides
    fun providesGetUserUseCase(
        userRepository: UserRepository,
        imgDataRepository: ImgDataRepository,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = GetUserUseCase(userRepository,imgDataRepository,firebaseCrashlytics)
    @JvmStatic
    @Singleton
    @Provides
    fun provideProjectDataSource(
        firebase : FirebaseFirestore
    ) = ProjectDataSourceImpl(firebase)
    @JvmStatic
    @Singleton
    @Provides
    fun providesProjectRepository(
        projectDataSourceImpl: ProjectDataSourceImpl
    ) = ProjectRepository(projectDataSourceImpl)
    @JvmStatic
    @Singleton
    @Provides
    fun providesGetAllProjects(
        projectRepository: ProjectRepository,
        imgDataRepository: ImgDataRepository,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = GetAllProjects(projectRepository,imgDataRepository,firebaseCrashlytics)
    @JvmStatic
    @Singleton
    @Provides
    fun providesLoginDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ) = LoginDataSource(firebaseAuth,firebaseFirestore)
    @JvmStatic
    @Singleton
    @Provides
    fun providesLoginRepository(
        loginDataSource: LoginDataSource,
        loginDataStore: DataStore<Preferences>,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = LoginRepository(loginDataSource, loginDataStore, firebaseCrashlytics)

    @JvmStatic
    @Singleton
    @Provides
    fun providesProjectEvaluateRepository(
        projectEvaluateDataSource: ProjectEvaluateDataSourceImpl
    ) = ProjectEvaluateRepository(projectEvaluateDataSource)
    @JvmStatic
    @Singleton
    @Provides
    fun providesGetProject(
        projectRepository: ProjectRepository,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = GetProject(projectRepository,firebaseCrashlytics)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class IAmDataSourceModule{

//    @Singleton
    @Binds
    abstract fun bindIAmDataSource(
        iAmFirebaseApi: IAmTextDataRemoteDataSourceImpl
    ) : IAmTextDataRemoteDataSource

//    @Singleton
    @Binds
    abstract fun bindImgDataSource(
        imgDataSource: ImgDataSourceImpl
    ) : ImgDataSource
//    @Singleton
    @Binds
    abstract fun bindProjectEvaluateDataSource(
        projectEvaluateDataSource : ProjectEvaluateDataSourceImpl
    ) : ProjectEvaluateDataSource
}

//https://medium.com/androiddevelopers/using-hilts-viewmodelcomponent-53b46515c4f4