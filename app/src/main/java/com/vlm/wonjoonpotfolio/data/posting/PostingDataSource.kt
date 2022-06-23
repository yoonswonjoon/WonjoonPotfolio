package com.vlm.wonjoonpotfolio.data.posting

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostingDataSource
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore
){
    suspend fun upLoadPosting(posting: PostingDto){
        firebaseFirestore.collection("posting").add(posting).await()
    }
}