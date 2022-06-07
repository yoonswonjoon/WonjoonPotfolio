package com.vlm.wonjoonpotfolio.ui.setting

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class SettingViewState(
    val login: Boolean = false,
    val email: String = "",
    val img: Uri? = null,
    val localeDialogVisible : Boolean = false,
    val versionControl : Boolean = false,
    val imgChangeDialogVisible : Boolean = false,
)


@HiltViewModel
class SettingViewModel @Inject
constructor(

):ViewModel(){

    private val _settingViewState = MutableStateFlow(
        SettingViewState()
    )

    val settingViewState get() = _settingViewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _settingViewState.value
    )
}