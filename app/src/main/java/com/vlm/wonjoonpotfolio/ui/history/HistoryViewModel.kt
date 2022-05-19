package com.vlm.wonjoonpotfolio.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.history.TestHistoryClass
import com.vlm.wonjoonpotfolio.data.useCase.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.data.useCase.GetMutlipleIAmDataUsecase
import com.vlm.wonjoonpotfolio.domain.ResultState
import com.vlm.wonjoonpotfolio.ui.iAm.IAmMainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val getIAmDataUseCase: GetMutlipleIAmDataUsecase): ViewModel() {
    private val _uiState = MutableStateFlow(
        listOf<TestHistoryClass>()
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    init {
        getIAmDataUseCase.invoke(listOf("","","")).onEach {
            when(it){
                is ResultState.Success -> {
                    _uiState.value = it.data
                }
            }
        }.launchIn(viewModelScope)
    }
}