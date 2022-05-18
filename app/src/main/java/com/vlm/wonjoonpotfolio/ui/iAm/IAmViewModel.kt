package com.vlm.wonjoonpotfolio.ui.iAm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.iAm.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class IAmMainViewState(
    val img : String?,
    val basicIntro : String,
    val isLoading : Boolean,
    val projectList : List<String>,
    val menu : List<String>
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
        getIAmDataUseCase().onEach { result ->
            when(result){
                is ResultState.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is ResultState.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, basicIntro = result.data.name)
                }
                is ResultState.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, basicIntro = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}