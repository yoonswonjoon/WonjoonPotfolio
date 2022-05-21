package com.vlm.wonjoonpotfolio.data.iAm.iAmTextData

import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface IAmTextDataRemoteDataSource {
//    fun test() : String
    suspend fun getIam() : IAmTextData//Flow<ResultState<IAmTextData>>

    suspend fun getList(list: List<String>) : List<IAmTextData>
}