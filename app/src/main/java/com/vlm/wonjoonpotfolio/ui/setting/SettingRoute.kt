package com.vlm.wonjoonpotfolio.ui.setting

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.ui.AppMainViewModel
import com.vlm.wonjoonpotfolio.ui.LoginViewStates
import com.vlm.wonjoonpotfolio.ui.component.DialogBasic
import com.vlm.wonjoonpotfolio.ui.component.SettingMenuText
import com.vlm.wonjoonpotfolio.ui.component.TextWithSubTile


@Composable
fun SettingRoute(
    appMainViewModel: AppMainViewModel,
    settingViewModel : SettingViewModel
) {
    val appUserViewState by appMainViewModel.loginViewState.collectAsState()
    val settingViewState by settingViewModel.settingViewState.collectAsState()
    val locale by appMainViewModel.localeData.collectAsState()
    SettingRoute(
        loginViewStates = appUserViewState,
        settingViewState = settingViewState,
        onClickLogin = appMainViewModel::loginController,
        locale = locale,
        onClickLocaleOk = {
            appMainViewModel.setLocale(it)
            settingViewModel.clickLocaleDialog()
        },
        onClickLocaleNo = settingViewModel::clickLocaleDialog,
        onClickLocale = settingViewModel::clickLocaleDialog
    )
}



@Composable
fun SettingRoute(
    loginViewStates: LoginViewStates,
    settingViewState: SettingViewState,
    locale : String,
    onClickLogin: () -> Unit,
    onClickLocaleOk : (String)->Unit,
    onClickLocaleNo : ()->Unit,
    onClickLocale :()->Unit
) {


    if(settingViewState.localeDialogVisible){
        SelectLocaleDialog(
            selected = locale,
            onClickOk = onClickLocaleOk,
            onClickNo = onClickLocaleNo
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        UserMainInfo(
            uri =  null,
            email = loginViewStates.user?.eid
        ) {

        }
        Spacer(modifier = Modifier.height(15.dp))
        Divider()
        SettingMenu(logined = loginViewStates.loginComplete,
            onClickLogin = onClickLogin,
            onClickLocale = onClickLocale,
            onClickVersion = { /*TODO*/ }) {

        }
    }
}

@Composable
fun UserMainInfo(uri: Uri? ,email : String?, onClickImg : () -> Unit){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Column() {
            if(uri == null){
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .clickable { }
                )
            }else{
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    placeholder = rememberVectorPainter(Icons.Rounded.Person),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                TextWithSubTile(text = email?: stringResource(id = R.string.not_in_log_in))
            }
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

@Composable
fun SelectLocaleDialog(
    selected : String,
    onClickOk : (String) -> Unit,
    onClickNo : () -> Unit
){
    val (select, onSelect) = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(selected)
    }
    DialogBasic(
        onDismiss = { onClickNo() }, onOk = { onClickOk(select) },
        okText = stringResource(id = R.string.confirm),
        noText = stringResource(id = R.string.cancel),
        justOkBtn = false
    ) {
        Column() {
            Row(modifier =Modifier.fillMaxWidth().clickable { onSelect("kr") }, verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = select == "kr", onClick = { onSelect("kr") })
                Text(text = "한국어")
            }
            Row(modifier =Modifier.fillMaxWidth().clickable { onSelect("en") },verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = select == "en", onClick = { onSelect("en") })
                Text(text = "English")
            }
        }
    }
}