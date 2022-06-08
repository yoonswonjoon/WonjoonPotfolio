package com.vlm.wonjoonpotfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.AppDataRepository
import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.domain.LoginErrorType
import com.vlm.wonjoonpotfolio.domain.PortfolioError
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
    val loginDialogView : Boolean = false,
    val user : User? = null,
    val userLoginFailedDialog : Boolean = false,
    val mainErrorList : List<PortfolioError> = listOf()
)

@HiltViewModel
class AppMainViewModel
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

    private var temperLoginData = "" to ""

    init {
        appDataRepository.getAppSettingData().onEach {
            if(it != null) _localeData.value = it
        }.catch {
            _localeData.value = "en"
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            val autoUser = loginRepository.autoLoginV2()
            if(autoUser != null){
                _loginViewState.value = _loginViewState.value.copy(
                    isLoading = false,
                    name = autoUser.eid,
                    email = autoUser.eid,
                    loginComplete =true,
                    user = autoUser
                )
            }
        }
    }

    fun login(id: String?, password: String?) {
        if(id != null && password !=null){
            temperLoginData = id to password

            viewModelScope.launch {
                val loginResult =loginRepository.loginV2(id,password)
                if(loginResult.data != null){
                    _loginViewState.value = _loginViewState.value.copy(
                        isLoading = false,
                        name = loginResult.data.eid,
                        email = loginResult.data.eid,
                        loginComplete =true,
                        user = loginResult.data
                    )
                }else if(loginResult.error!= null){
                    when(loginResult.error){
                        LoginErrorType.NotSignedIn-> {
                            _loginViewState.value = _loginViewState.value.copy(
                                isLoading = false,
                                newUser = true,
                                loginDialogView = false,
                            )
                        }
                        LoginErrorType.OtherError-> {
                            addErrors(PortfolioError(msg = "알 수 없는 문제로 로그인에 실패하였습니다"))
                        }
                        LoginErrorType.SettingBasicDataFailed-> {
                            addErrors(PortfolioError(msg = "서버에 데이터 저장이 되지 않았습니다\n 다시 로그인 해주세요"))
                        }
                    }
                }
            }
        }else{
            addErrors(PortfolioError(msg = "다시 로그인 해주세요"))
        }
        loginClose()
    }

    fun signIn(/*id: String, password: String*/) {
        viewModelScope.launch {
            if(loginRepository.signIn(temperLoginData.first , temperLoginData.second)){
                login(temperLoginData.first , temperLoginData.second)
            }else{
                addErrors(PortfolioError(msg = "알 수 없는 이유로 회원가입에 실패하였습니다. 개발자에게 연락주세요"))
            }

            _loginViewState.value = _loginViewState.value.copy(
                newUser = false
            )
        }
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

    fun loginController() {
        if(_loginViewState.value.loginComplete){
            viewModelScope.launch {
                loginRepository.logOutV2()
                _loginViewState.value = _loginViewState.value.copy(
                    loginComplete = false,
                    user=null,
                    email = null,
                    name = null
                )
            }
        }else{
            onLoginOpen()
        }
    }

    fun dismissLoginFailedD(){
        _loginViewState.value = _loginViewState.value.copy(
            userLoginFailedDialog = false,
        )
    }


    fun addErrors(error: PortfolioError) {
        val errors = _loginViewState.value.mainErrorList.toMutableList()
        errors.add(error)
        _loginViewState.value = _loginViewState.value.copy(mainErrorList = listOf())
        _loginViewState.value = _loginViewState.value.copy(mainErrorList = errors)
    }

    fun dismissError(error: PortfolioError) {
        val errors = _loginViewState.value.mainErrorList.toMutableList()
        errors.removeFirst()
        _loginViewState.value = _loginViewState.value.copy(mainErrorList = listOf())
        _loginViewState.value = _loginViewState.value.copy(mainErrorList = errors)
    }
}
