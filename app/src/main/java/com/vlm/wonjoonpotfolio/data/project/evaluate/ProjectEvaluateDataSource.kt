package com.vlm.wonjoonpotfolio.data.project.evaluate

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface ProjectEvaluateDataSource {
    suspend fun loadEvaluate(
                                       projectId: String,) : List<ProjectEvaluateData>
    suspend fun writeEvaluate(uid : String, projectId : String,evaluate : ProjectEvaluateData) : Boolean
    suspend fun deleteEvaluate(uid : String, projectId : String)
    suspend fun modifyEvaluate(uid : String, projectId : String)
    suspend fun amIin(userUid : String, projectId : String) : Boolean
    suspend fun updateProjectEvaluate(amIin : Boolean)
}