package com.vlm.wonjoonpotfolio.data.posting

import androidx.paging.map
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostingPagingDataUseCase
@Inject
constructor(
    private val postingRepository: PostingRepository,
    private val imgDataRepository: ImgDataRepository
){
    suspend operator fun invoke() =
            postingRepository.getPostings()

    val data = postingRepository.getPostings()
//                .map {
//                it.map { posting ->
//                    posting.dataUri?.let {
//                        posting.toPosting(imgDataRepository.getImageUpgrade(it))
//                    } ?: posting.toPosting(null)
//                }
//            }

}