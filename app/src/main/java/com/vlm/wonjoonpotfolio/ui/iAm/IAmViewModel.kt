package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.data.useCase.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class IAmMainViewState(
    val img : Uri? = null,
    val name: String = "",
    val birthday: String = "",
    val introduce: String = "",
    val school: String = "",
    val phone: String = "",
    val eid: String = "",
    val isLoading : Boolean = true,
    val projectList : List<ProjectData> = listOf(
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플"),
        ProjectData("나이만", "2021.03~","위치기반 소개팅 어플")
    ),
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
            name = "",
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
            when(result){
                is ResultState.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is ResultState.Success -> {
                    _uiState.value = _uiState.value.copy(
                        img = result.data.img,
                        name = result.data.name,
                        birthday = result.data.birthday,
                        introduce = result.data.introduce,
                        school = result.data.school,
                        phone = result.data.phone,
                        eid = result.data.eid,
                        isLoading = result.data.isLoading,
                        projectList = result.data.projectList,
                        menu = result.data.menu,
                        testImgList = result.data.testImgList,
                        imgLoading = result.data.imgLoading
                    )
                }
                is ResultState.Error -> {
                    _uiState.value = _uiState.value.copy(name = result.message , isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}