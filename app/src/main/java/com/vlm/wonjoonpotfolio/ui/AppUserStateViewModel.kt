package com.vlm.wonjoonpotfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class LoginViewStates(
    val isLoading: Boolean = true,
    val name: String? = null,
    val email: String? = null,
    val newUser: Boolean = false,
    val loginComplete : Boolean = false,
) {

}

@HiltViewModel
class AppUserStateViewModel
@Inject
constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {



    private val _loginViewState = MutableStateFlow(
        LoginViewStates()
    )

    val loginViewState
        get() = _loginViewState.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _loginViewState.value
        )

    init {
        loginRepository.autoLogin().onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        name = resultState.data,
                        email = resultState.data,
                        loginComplete =true
                    )
                }
                is ResultState.Error -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        email = resultState.message
                    )
                }
                is ResultState.Loading -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = true,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login(id: String, password: String) {
        loginRepository.firebaseLogin(id, password).onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        resultState.data,
                        email = resultState.data,
                        loginComplete = true
                    )
                }
                is ResultState.Error -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        email = resultState.message,
                        newUser = true
                    )
                }
                is ResultState.Loading -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = true,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun signIn(/*id: String, password: String*/) {
        loginRepository.firebaseSignIn(/*id,password*/).onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        name = resultState.data,
                        email = resultState.data,
                        newUser = false,
                        loginComplete = true
                    )
                }
                is ResultState.Error -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        email = resultState.message,
                        newUser = false
                    )
                }
                is ResultState.Loading -> {
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = true,
                        newUser = false
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun cancelSignIn() {
        _loginViewState.value = _loginViewState.value.copy(
            newUser = false
        )
    }


}