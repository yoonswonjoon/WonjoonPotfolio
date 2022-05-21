package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.useCase.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class IAmMainViewState(
    val img : Uri? = null,
    val basicIntro : String = "",
    val isLoading : Boolean = true,
    val projectList : List<String> = listOf(),
    val menu : List<String> = listOf(),
    val testImgList : List<Uri?> = listOf(),
    val imgLoading : Boolean = true
)

@HiltViewModel
class IAmViewModel
@Inject
constructor(
    private val getIAmDataUseCase: GetIAmDataUseCase,
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(
        IAmMainViewState(
            img = null,
            basicIntro = "",
            isLoading = true,
            projectList = listOf(),
            menu = listOf()
        )
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    init {
        getIAmDataUseCase("wj.png").onEach { result ->
                when(val text = result.first){
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uiState.value = _uiState.value.copy(basicIntro = text.data.name, isLoading = false)
                    }
                    is ResultState.Error -> {
                        _uiState.value = _uiState.value.copy(basicIntro = text.message , isLoading = true)
                    }
                }

            when(val img = result.second) {
                is ResultState.Loading -> {
                    _uiState.value = _uiState.value.copy(imgLoading = true)
                }
                is ResultState.Success -> {
                    _uiState.value = _uiState.value.copy(img = img.data)
                }
                is ResultState.Error -> {
                    _uiState.value = _uiState.value.copy()
                }
            }
        }.launchIn(viewModelScope)
    }

}