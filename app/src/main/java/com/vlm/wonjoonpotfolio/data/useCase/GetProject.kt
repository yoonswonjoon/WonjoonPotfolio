package com.vlm.wonjoonpotfolio.data.useCase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.data.project.ProjectRepository
import java.lang.Exception
import javax.inject.Inject

class GetProject
@Inject
constructor(
    private val projectRepository: ProjectRepository,
    private val firebaseCrashlytics: FirebaseCrashlytics
){
    companion object {
        const val TAG = "GetProject"
    }
    suspend operator fun invoke(id: String): ProjectData? {
        return try {
            val projects = projectRepository.getProject(id)
            projects.toProjectData(null)
        }catch (e: Exception){
            firebaseCrashlytics.log("$TAG : ${e.message?: "unknown"}")
            null
        }
    }
}