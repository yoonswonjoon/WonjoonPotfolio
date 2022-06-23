package com.vlm.wonjoonpotfolio.ui.posting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlm.wonjoonpotfolio.data.posting.GetPostingPagingDataUseCase
import com.vlm.wonjoonpotfolio.data.posting.Posting
import com.vlm.wonjoonpotfolio.data.posting.PostingDto
import com.vlm.wonjoonpotfolio.data.posting.UpLoadPostingUseCase
import com.vlm.wonjoonpotfolio.data.useCase.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


data class PostingMainViewState(
    val loading : Boolean = true,
    val postingList : List<PostingDto> = listOf()
)




@HiltViewModel
class PostingViewModel
@Inject
constructor(
    private val getPostingPagingDataUseCase: GetPostingPagingDataUseCase,
    private val upLoadPostingUseCase: UpLoadPostingUseCase,
    private val getCurrentUser: GetCurrentUser
    ): ViewModel()
{

    private val _uiState = MutableStateFlow(
        PostingMainViewState(

        )
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    private val _pagingData = MutableStateFlow<PagingData<PostingDto>>(PagingData.empty())
    val pagingData get() = _pagingData

//    val items = getPostingPagingDataUseCase.data


    init {
//        viewModelScope.launch {
//            getPostingPagingDataUseCase().onEach {
//                _uiState.value = _uiState.value.copy(
//                    postingList = it
//                )
//            }
//        }
        viewModelScope.launch {
            getPostingPagingDataUseCase.data.cachedIn(viewModelScope).collect{
                _pagingData.value = it
            }
        }
        getPostingPagingDataUseCase.data.map {
//            _pagingData.value = it


            it.map {
                val tempor  = _uiState.value.postingList.toMutableList()
                tempor.add(it)
                _uiState.value = _uiState.value.copy(
                    postingList = listOf()
                )

                _uiState.value = _uiState.value.copy(
                    postingList = tempor
                )
            }
        }
    }

    fun upLoadPosting(){
        viewModelScope.launch {
            upLoadPostingUseCase(
                PostingDto(
                    pid= "asdasdasd ",
                            dataUri= null,
                            date= Date(1),
                            nickname= "a",
                            contents= "dwds ",
                            voteUp= 1,
                            voteDown= 2,
                            version= 1
                )
            )
        }

    }
}