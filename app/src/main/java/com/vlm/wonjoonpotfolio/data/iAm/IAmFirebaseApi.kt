package com.vlm.wonjoonpotfolio.data.iAm

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class IAmFirebaseApi(private val firebase : FirebaseFirestore) : IAmRemoteDataSource {
    override suspend fun getIam(): Flow<IAm> = flow{
        val mainDoc = firebase

    }
}