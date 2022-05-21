package com.vlm.wonjoonpotfolio.data.iAm.iAmTextData

import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IAmTextDataRepository @Inject constructor(private val remoteDataSource: IAmTextDataRemoteDataSource) {
    suspend fun getMainView() : IAmTextData/*Flow<ResultState<IAmTextData>>*/ = remoteDataSource.getIam()
    suspend fun getListMainView(list: List<String>) : List<IAmTextData> = remoteDataSource.getList(list)
}