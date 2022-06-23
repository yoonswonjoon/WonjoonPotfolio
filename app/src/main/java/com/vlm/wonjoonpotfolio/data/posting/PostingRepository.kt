package com.vlm.wonjoonpotfolio.data.posting

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostingRepository
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val postingDataSource: PostingDataSource
) {
    fun getPostings() = Pager(
        config = PagingConfig(pageSize = 4, enablePlaceholders = false),
        pagingSourceFactory = { PostingPagingSource(firebase = firebaseFirestore)}
    )
        .flow

    suspend fun upLoadPosting(posting: PostingDto) = postingDataSource.upLoadPosting(posting = posting)

}