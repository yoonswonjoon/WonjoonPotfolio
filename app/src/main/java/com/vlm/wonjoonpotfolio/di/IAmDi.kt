package com.vlm.wonjoonpotfolio.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
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

    @Provides
    fun provideUserDataSource(
        firebase : FirebaseFirestore
    ) = UserDataSource(firebase)

    @Provides
    fun providesUserRepository(
        userDataSource: UserDataSource
    ) = UserRepository(userDataSource)

    @Provides
    fun providesGetUserUseCase(
        userRepository: UserRepository,
        imgDataRepository: ImgDataRepository
    ) = GetUserUseCase(userRepository,imgDataRepository)

    @Provides
    fun provideProjectDataSource(
        firebase : FirebaseFirestore
    ) = ProjectDataSourceImpl(firebase)

    @Provides
    fun providesProjectRepository(
        projectDataSourceImpl: ProjectDataSourceImpl
    ) = ProjectRepository(projectDataSourceImpl)

    @Provides
    fun providesGetAllProjects(
        projectRepository: ProjectRepository,
        imgDataRepository: ImgDataRepository
    ) = GetAllProjects(projectRepository,imgDataRepository)

    @Provides
    fun providesLoginDataSource(
        firebaseAuth: FirebaseAuth
    ) = LoginDataSource(firebaseAuth)

    @Provides
    fun providesLoginRepository(
        loginDataSource: LoginDataSource,
        loginDataStore: DataStore<Preferences>
    ) = LoginRepository(loginDataSource,loginDataStore)


    @Provides
    fun providesProjectEvaluateRepository(
        projectEvaluateDataSource: ProjectEvaluateDataSourceImpl
    ) = ProjectEvaluateRepository(projectEvaluateDataSource)

    @Provides
    fun providesGetProject(
        projectRepository: ProjectRepository
    ) = GetProject(projectRepository)
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

    @Binds
    abstract fun bindProjectEvaluateDataSource(
        projectEvaluateDataSource : ProjectEvaluateDataSourceImpl
    ) : ProjectEvaluateDataSource
}

