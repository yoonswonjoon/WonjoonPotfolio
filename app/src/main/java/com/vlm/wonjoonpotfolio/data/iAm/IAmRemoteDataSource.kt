package com.vlm.wonjoonpotfolio.data.iAm

import kotlinx.coroutines.flow.Flow

interface IAmRemoteDataSource {
    suspend fun getIam() : Flow<IAm>
}