package com.vlm.wonjoonpotfolio.data.posting

import javax.inject.Inject

class UpLoadPostingUseCase
@Inject
constructor(
    private val postingRepository: PostingRepository
)
{
    operator suspend fun invoke(posting: PostingDto) {
        postingRepository.upLoadPosting(posting)
    }
}