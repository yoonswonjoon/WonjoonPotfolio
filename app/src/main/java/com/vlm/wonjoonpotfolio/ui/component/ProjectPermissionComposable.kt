package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ProjectPermissionComposable(
//    permissionState: PermissionState,
//    onActive : () -> Unit,
//    fistTimeString : String,
//    noneFirstTimeString : String,
//    composable: @Composable ColumnScope.() -> Unit
//) {
//    when(permissionState.status){
//        is PermissionStatus.Granted -> {
//            onActive()
//        }
//        is PermissionStatus.Denied->{
//            permissionState.launchPermissionRequest()
//        }
//    }
//}