package com.vlm.wonjoonpotfolio.ui.setting

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.ui.AppUserStateViewModel
import com.vlm.wonjoonpotfolio.ui.LoginViewStates
import com.vlm.wonjoonpotfolio.ui.component.SettingMenuText
import com.vlm.wonjoonpotfolio.ui.component.TextWithSubTile


@Composable
fun SettingRoute(
    appUserStateViewModel: AppUserStateViewModel,
    settingViewModel : SettingViewModel
) {
    val appUserViewState by appUserStateViewModel.loginViewState.collectAsState()
    val settingViewState by settingViewModel.settingViewState.collectAsState()
    SettingRoute(
        loginViewStates = appUserViewState,
        settingViewState = settingViewState
    )
}



@Composable
fun SettingRoute(
    loginViewStates: LoginViewStates,
    settingViewState: SettingViewState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        UserMainInfo(
            uri =  null,
            email = loginViewStates.user?.eid
        ) {

        }
        Spacer(modifier = Modifier.height(15.dp))
        Divider()
        SettingMenu(logined = loginViewStates.loginComplete,
            onClickLogin = { /*TODO*/ },
            onClickLocale = { /*TODO*/ },
            onClickVersion = { /*TODO*/ }) {

        }
    }
}

@Composable
fun UserMainInfo(uri: Uri? ,email : String?, onClickImg : () -> Unit){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Column() {
            AsyncImage(
                model = uri,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable { }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextWithSubTile(text = email?: stringResource(id = R.string.not_in_log_in))
        }
    }
}



@Composable
fun SettingMenu(
    logined : Boolean,
    onClickLogin : () -> Unit,
    onClickLocale : ()-> Unit,
    onClickVersion : () -> Unit,
    onClickSignOut : () -> Unit
){
    val logButton = if(logined) stringResource(id = R.string.log_out) else stringResource(id = R.string.log_in)
    Column() {
        SettingMenuText(icon = Icons.Default.Settings, text = logButton, onClick = onClickLogin)
        SettingMenuText(icon = Icons.Rounded.LocationOn, text = stringResource(id = R.string.locale), onClick = onClickLocale)
        SettingMenuText(icon = Icons.Default.Settings, text = stringResource(id = R.string.version_check), onClick = onClickVersion)
        if(logined) SettingMenuText(icon = null, text = stringResource(id = R.string.sign_out), onClick = onClickSignOut)
    }


}