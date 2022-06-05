package com.vlm.wonjoonpotfolio.data.project.evaluate

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject

class ProjectEvaluateDataSourceImpl
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore,
) :ProjectEvaluateDataSource{

    private val projectCollection = firebaseFirestore.collection("project")
    private val userCollection = firebaseFirestore.collection("appUser")

    override suspend fun loadEvaluate(
        projectId: String,
    ): List<ProjectEvaluateData> {
        val col = projectCollection.document(projectId).collection("evaluate").orderBy("date")
        var result = listOf<ProjectEvaluateData>()
        col.get().addOnSuccessListener {
            try {
                if(!it.isEmpty){
                    result = it.documents.map {
                        it.toObject(ProjectEvaluateData::class.java)!!
                    }.toList()
                }
            }catch (e:Exception){

            }
        }.await()
        return result
    }


    override suspend fun writeEvaluate(
        uid: String,
        projectId: String,
        evaluate: ProjectEvaluateData
    ) : Boolean {
        val doc = projectCollection.document(projectId).collection("evaluate").document(uid)
        val docMemo = projectCollection.document(projectId).collection("evaluate_memo")
        var a = false
        var b = false
        doc.set(evaluate).addOnSuccessListener {
            a=true
        }.await()

        docMemo.add(evaluate).addOnSuccessListener {
            b = true
        }.await()

        firebaseFirestore.runTransaction { transaction ->
            val db = transaction.get(projectCollection.document(projectId))
            val newPoint = db.getDouble("accPoint")!! + evaluate.point
            val newCounter = db.getDouble("participantCount")!! + 1

            transaction.update(projectCollection.document(projectId),"accPoint",newPoint)
            transaction.update(projectCollection.document(projectId),"participantCount",newCounter)
        }


        return a && b
    }

    override suspend fun deleteEvaluate(uid: String, projectId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun modifyEvaluate(uid: String, projectId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun amIin(userUid: String, projectId: String) : Boolean{
        val doc = projectCollection.document(projectId).collection("evaluate").document(userUid)
        var result = false
        doc.get().addOnSuccessListener {
            if(it.exists()){
                result = true
            }
        }.await()


        return result
    }

    override suspend fun updateProjectEvaluate(amIin: Boolean) {

    }
}