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

data class ProjectParticipant(
    val list : List<Uri> = listOf<Uri>()
)

@HiltViewModel
class ProjectViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel(){
    private val _userList = MutableStateFlow(
        listOf<UserForUi>()
    )

    val userList get() = _userList.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _userList.value
    )

    fun getUserUriList(list : List<String>){
        getUserUseCase(list).onEach { users->
            _userList.value = users
        }.launchIn(viewModelScope)
    }

}