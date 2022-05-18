package com.vlm.wonjoonpotfolio.data.iAm

import javax.inject.Inject

class IAmRepository @Inject constructor(private val remoteDataSource: IAmRemoteDataSource) {
    fun test() = "remoteDataSource.test()"
    fun getMainView() = remoteDataSource.getIam()
}