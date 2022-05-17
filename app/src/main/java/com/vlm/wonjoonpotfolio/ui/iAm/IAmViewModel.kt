package com.vlm.wonjoonpotfolio.ui.iAm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import javax.inject.Inject


data class IAmMainViewState(
    val img : String?,
    val basicIntro : String,
    val isLoading : Boolean,
    val projectList : List<String>,
    val menu : List<String>
)


class IAmViewModel @ViewModelInject constructor()
    : ViewModel()
{

}