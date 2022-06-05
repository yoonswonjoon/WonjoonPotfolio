package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.data.project.ProjectRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetProject
@Inject
constructor(
    private val projectRepository: ProjectRepository
){
    suspend operator fun invoke(id: String): ProjectData? {
        return try {
            val projects = projectRepository.getProject(id)
            projects.toProjectData(null)
        }catch (e: Exception){
            null
        }
    }
}