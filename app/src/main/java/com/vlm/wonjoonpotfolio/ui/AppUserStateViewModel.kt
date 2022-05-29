package com.vlm.wonjoonpotfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.useCase.LoginCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class LoginViewStates(
    val isLoading : Boolean = true,
    val name : String? = null,
    val email : String? = null,
){

}

@HiltViewModel
class AppUserStateViewModel
@Inject
constructor(
    private val loginCheckUseCase: LoginCheckUseCase
    ) : ViewModel(){

    private val _loginViewState = MutableStateFlow(
        LoginViewStates()
    )

    val loginViewState get() = _loginViewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _loginViewState.value
    )

    init {
        loginCheckUseCase.invoke().onEach {
            _loginViewState.value = _loginViewState.value.copy(name = it?.uid, isLoading = false)
        }.launchIn(viewModelScope)
    }


}