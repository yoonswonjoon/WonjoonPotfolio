package com.vlm.wonjoonpotfolio.data.iAm

import com.vlm.wonjoonpotfolio.domain.Result
import kotlinx.coroutines.flow.Flow

interface IAmRemoteDataSource {
    fun test() : String
//    fun getIam() : Flow<Result<IAm>>
}