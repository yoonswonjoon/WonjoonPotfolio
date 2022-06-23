package com.vlm.wonjoonpotfolio.data.posting

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class PostingPagingSource
(
    private val firebase : FirebaseFirestore
) : PagingSource<QuerySnapshot,PostingDto>()
{
    override fun getRefreshKey(state: PagingState<QuerySnapshot, PostingDto>): QuerySnapshot? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, PostingDto> {
        return try {
            val currentPage = params.key ?: firebase.collection("posting")
                .limit(4)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            val lastSnapshot = currentPage.documents[currentPage.size()-1]
            val nextPage = firebase.collection("posting")
                .limit(4)
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(lastSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(PostingDto::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}