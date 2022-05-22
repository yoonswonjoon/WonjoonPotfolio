package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.useCase.GetUserUseCase
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ProjectViewState(
    val list : List<UserForUi> = listOf(),
    val selectedUser : UserForUi? = null
)

@HiltViewModel
class ProjectViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel(){
    private val _viewState = MutableStateFlow(
        ProjectViewState()
    )

    val viewState get() = _viewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _viewState.value
    )


    fun getUserUriList(list : List<String>){
        getUserUseCase(list).onEach { users->
            _viewState.value = _viewState.value.copy(list = users)
        }.launchIn(viewModelScope)
    }

    fun selectUser(userForUi: UserForUi){
        _viewState.value = _viewState.value.copy(selectedUser = userForUi)
    }
}