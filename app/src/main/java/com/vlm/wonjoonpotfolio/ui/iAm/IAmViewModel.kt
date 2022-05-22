package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.data.useCase.GetAllProjects
import com.vlm.wonjoonpotfolio.data.useCase.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class IAmMainViewState(
    val img: Uri? = null,
    val name: String = "",
    val birthday: String = "",
    val introduce: String = "",
    val school: String = "",
    val phone: String = "",
    val before: String = "",
    val eid: String = "",
    val isLoading: Boolean = true,
    val projectList: List<ProjectData> = listOf(),
    val menu: List<String> = listOf(),
    val testImgList: List<Uri?> = listOf(),
    val imgLoading: Boolean = true,
    val scrollListener: Int = 0,
    val selectedProject: ProjectData? = null
)

@HiltViewModel
class IAmViewModel
@Inject
constructor(
    private val getIAmDataUseCase: GetIAmDataUseCase,
    private val getAllProjects: GetAllProjects
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

    private var _selectedProject: ProjectData? = null
    val selectedProject get() = _selectedProject

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    init {
        getAllProjects().onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    _uiState.value = _uiState.value.copy(projectList = resultState.data)
                }
            }
        }.launchIn(viewModelScope)

        getIAmDataUseCase("wj.png").onEach { result ->
            when (result) {
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
                        before = result.data.before,
                        isLoading = false,
                        projectList = result.data.projectList,
                        menu = result.data.menu,
                        testImgList = result.data.testImgList,
                        imgLoading = result.data.imgLoading
                    )
                }
                is ResultState.Error -> {
                    _uiState.value = _uiState.value.copy(name = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectProject(projectData: ProjectData) {
        _selectedProject = projectData
        _uiState.value = _uiState.value.copy(
            selectedProject = projectData
        )
    }
}