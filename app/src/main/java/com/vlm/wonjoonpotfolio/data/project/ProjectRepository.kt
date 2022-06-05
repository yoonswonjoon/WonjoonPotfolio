package com.vlm.wonjoonpotfolio.data.project

import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectDataSource : ProjectDataSourceImpl) {
    suspend fun getAllProject(): List<ProjectDto> {
         return projectDataSource.getAllProject()
    }

    suspend fun getProject(id : String) : ProjectDto {
        return projectDataSource.getProject(id)
    }
}