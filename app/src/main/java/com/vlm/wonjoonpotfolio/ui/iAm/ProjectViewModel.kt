package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.rpc.ErrorDetailsProto
import com.vlm.wonjoonpotfolio.data.useCase.GetUserUseCase
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.domain.PortfolioError
import com.vlm.wonjoonpotfolio.domain.ProjectStringType
import com.vlm.wonjoonpotfolio.domain.addUriToString
import com.vlm.wonjoonpotfolio.domain.commaSplit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

data class ProjectViewState(
    val list : List<UserForUi> = listOf(),
    val selectedUser : UserForUi? = null,
    val stackMap : Map<String,List<ProjectStringType>>? = null,
    val isLoading : Boolean = true,
    val errors : List<PortfolioError> = listOf(),
    val isInEvaluation : Boolean= false
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


    fun initProjectView(list : List<String>,stacks : Map<String,String>? = null){
        val map = mutableMapOf<String,List<ProjectStringType>>()
        stacks?.forEach { key, value ->
            map[key] = value.commaSplit().map { it.addUriToString() }
        }

        getUserUseCase(list).onEach { users->
            _viewState.value = _viewState.value.copy(
                list = users,
                stackMap = map,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun selectUser(userForUi: UserForUi){
        _viewState.value = _viewState.value.copy(
            selectedUser = userForUi
        )
    }

    fun evaluation(point : Int){
        if(point <= 0){
            addErrors(error = PortfolioError(
                showNoBtn = true,
                msg = "1점 이상을 줘야합니다"
            )
            )
        }
    }


    fun addErrors(error : PortfolioError){
        val errors = _viewState.value.errors.toMutableList()
        errors.add(error)
        _viewState.value = _viewState.value.copy(errors = listOf())
        _viewState.value = _viewState.value.copy(errors = errors)
    }
    fun dismissError(error: PortfolioError){
        val errors = _viewState.value.errors.toMutableList()
        errors.removeFirst()
        _viewState.value = _viewState.value.copy(errors = listOf())
        _viewState.value = _viewState.value.copy(errors = errors)
    }
    fun openEvaluation() {
        _viewState.value = _viewState.value.copy(isInEvaluation =  true)
    }

    fun closeEvaluation() {
        _viewState.value = _viewState.value.copy(isInEvaluation =  false)
    }
}