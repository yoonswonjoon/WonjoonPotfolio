package com.vlm.wonjoonpotfolio.data.project

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProjectDataSourceImpl @Inject constructor(private val firebase: FirebaseFirestore) {
    suspend fun getAllProject(): List<ProjectDto> {
        val doc = firebase.collection("project").get().await()
        return doc.documents.map {
            it.toObject(ProjectDto::class.java)!!
        }
    }

    suspend fun getProject(id : String): ProjectDto {
        val doc = firebase.collection("project").document(id).get().await()
        return doc.toObject(ProjectDto::class.java)!!
    }
}