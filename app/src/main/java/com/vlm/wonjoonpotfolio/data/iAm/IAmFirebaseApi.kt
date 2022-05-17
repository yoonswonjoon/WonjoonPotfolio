package com.vlm.wonjoonpotfolio.data.iAm

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow


class IAmFirebaseApi(private val firebase : FirebaseFirestore) : IAmRemoteDataSource {
    override suspend fun getIam(): Flow<IAm> {
        val mainDoc = firebase
    }
}