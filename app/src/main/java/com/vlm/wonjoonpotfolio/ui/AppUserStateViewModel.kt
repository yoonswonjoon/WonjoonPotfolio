package com.vlm.wonjoonpotfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.AppDataRepository
import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginViewStates(
    val isLoading: Boolean = false,
    val name: String? = null,
    val email: String? = null,
    val newUser: Boolean = false,
    val loginComplete : Boolean = false,
    val loginDialogView : Boolean = false
) {

}

@HiltViewModel
class AppUserStateViewModel
@Inject
constructor(
    private val loginRepository: LoginRepository,
    private val appDataRepository : AppDataRepository
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

    private val _localeData  = MutableStateFlow<String>(
        "en"
    )

    val localeData get() =  _localeData.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _localeData.value
    )

    init {
        appDataRepository.getAppSettingData().onEach {
            if(it != null) _localeData.value = it
        }.catch {
            _localeData.value = "en"
        }.launchIn(viewModelScope)



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
        }.catch {

        }.launchIn(viewModelScope)
    }

    fun login(id: String?, password: String?) {
        if(id != null && password !=null){
            loginRepository.firebaseLogin(id, password).onEach { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        _loginViewState.value = _loginViewState.value.copy(
                            isLoading = false,
                            resultState.data,
                            email = resultState.data,
                            loginComplete = true,
                            loginDialogView = false
                        )
                    }
                    is ResultState.Error -> {
                        _loginViewState.value = _loginViewState.value.copy(
                            isLoading = false,
                            email = resultState.message,
                            newUser = true,
                            loginDialogView = false
                        )
                    }
                    is ResultState.Loading -> {
                        _loginViewState.value = _loginViewState.value.copy(
                            isLoading = true,
                            loginDialogView = false
                        )
                    }
                }
            }.catch {
                _loginViewState.value = _loginViewState.value.copy(
                    isLoading = false,
                    newUser = true,
                    loginDialogView = false
                )
            }
                .launchIn(viewModelScope)
        }
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

        }.catch {

        }.launchIn(viewModelScope)
    }

    fun cancelSignIn() {
        _loginViewState.value = _loginViewState.value.copy(
            newUser = false
        )
    }

    fun setLocale(locale: String){
        viewModelScope.launch {
            appDataRepository.setLocale(locale)
            _localeData.value = locale
        }
    }

    fun onLoginOpen(){
        _loginViewState.value = _loginViewState.value.copy(
            loginDialogView = true,
        )
    }

    fun loginClose(){
        _loginViewState.value = _loginViewState.value.copy(
            loginDialogView = false,
        )
    }
}