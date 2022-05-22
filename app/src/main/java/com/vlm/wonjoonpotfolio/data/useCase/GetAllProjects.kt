package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.project.ProjectRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAllProjects
@Inject
constructor(
    private val projectRepository: ProjectRepository,
    private val imgDataRepository: ImgDataRepository
    ){

    operator fun invoke() = flow {
        try {
            val projects = projectRepository.getAllProject()

            val lists = projects.map {
                val uri = imgDataRepository.getImageUpgrade(it.uri)
                it.toProjectData(uri)
            }
            emit(ResultState.success(lists))
        }catch (e:Exception){
            emit(ResultState.error(e.message?: "i don't know why"))
        }
    }
}