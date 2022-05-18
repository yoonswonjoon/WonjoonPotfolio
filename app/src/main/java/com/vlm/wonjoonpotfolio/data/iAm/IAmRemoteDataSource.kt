package com.vlm.wonjoonpotfolio.data.iAm

import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface IAmRemoteDataSource {
//    fun test() : String
    fun getIam() : Flow<ResultState<IAm>>
}