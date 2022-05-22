package com.vlm.wonjoonpotfolio.data.project

import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectDataSource : ProjectDataSourceImpl) {
    suspend fun getAllProject(): List<ProjectDao> {
         return projectDataSource.getAllProject()
    }
}