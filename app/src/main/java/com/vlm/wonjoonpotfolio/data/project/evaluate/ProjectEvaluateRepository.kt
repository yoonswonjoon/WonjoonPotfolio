package com.vlm.wonjoonpotfolio.data.project.evaluate

import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProjectEvaluateRepository
@Inject
constructor(
    private val projectEvaluateDataSource: ProjectEvaluateDataSource
){
    fun writeEvaluate(uid: String, projectId: String, evaluate : ProjectEvaluateData) = flow {
        try {
            emit(ResultState.loading())
            var result = false
            kotlinx.coroutines.withTimeout(3000L) {
                result = projectEvaluateDataSource.writeEvaluate(
                    uid, projectId, evaluate
                )
            }
            emit(ResultState.success(result))
        }catch (e:Exception){
            println(e.message)
//            emit(ResultState.error(e.message ?: "I Don't Know"))
        }
    }

    fun amIin(userUid : String, projectId: String) = flow{
        val amIin = projectEvaluateDataSource.amIin(userUid,projectId)
        emit(amIin)
    }

    fun getAllEvaluate(projectId: String)=  flow {
        emit(ResultState.loading())
        val list = projectEvaluateDataSource.loadEvaluate(projectId)
        emit(ResultState.success(list))
    }
}