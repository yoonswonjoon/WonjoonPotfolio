@file:OptIn(ExperimentalPermissionsApi::class)

package com.vlm.wonjoonpotfolio.ui.iAm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.HORIZONTAL_SPACE
import com.vlm.wonjoonpotfolio.domain.ProjectStringType
import com.vlm.wonjoonpotfolio.ui.component.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProjectDetailRoute(
    viewModel: IAmViewModel,
    projectViewModel: ProjectViewModel,
    appState: PortfolioAppState
) {
    val uiState = viewModel.selectedProject
    val viewState by projectViewModel.viewState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val userListOperator by rememberUpdatedState(newValue = uiState?.participant)
    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val phoneNumberPermissionState = rememberPermissionState(permission = android.Manifest.permission.READ_PHONE_STATE)

    LaunchedEffect(true) {
        projectViewModel.initProjectView(userListOperator!!,uiState?.projectStatecks?.stacks)
    }

    ModalBottomSheetLayout(
        sheetContent = {
            ProjectBottomSheet(
                eid = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$it")
                    }
                    context.startActivity(intent)
                },
                phone = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$it")
                    }
                    context.startActivity(intent)
                },
                user = viewState.selectedUser
            )
        },
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(100f, 100f, 0f, 0f)
    ) {
        Column(
            Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(horizontal = HORIZONTAL_SPACE, vertical = HORIZONTAL_SPACE),
        ) {
            Row() { // 사진 , 기본기능
                Surface(
                    modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    AsyncImage(
                        model = uiState?.uri?: painterResource(id = coil.base.R.drawable.notification_bg),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(){
                        OutLinedUriClickableView(
                            uri = uiState?.downloadUri,
                            context = context,
                            scaffoldState =  appState.scaffoldState,
                            coroutineScope = coroutineScope,
                        ){
                            TextWithMainBody(text = "다운로드")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        OutLinedUriClickableView(
                            uri = uiState?.git,
                            context = context,
                            scaffoldState =  appState.scaffoldState,
                            coroutineScope = coroutineScope,
                        ){
                            TextWithMainBody(text = "git")
                        }
                    }

                    TextWithMainBody(text = uiState?.long ?: "알 수 없음.")

                    TextWithMainBody(text = "제작인원")
                    if (!viewState.isLoading) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            viewState.list.forEach {
                                UserItem(it) {
                                    projectViewModel.selectUser(it)
                                    coroutineScope.launch {
                                        bottomSheetScaffoldState.show()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(viewState.isLoading){
                Box(modifier = Modifier.fillMaxSize(),  ){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }else{

                ProjectInteractButtons(
                    onClickEvaluate = {
                        when(phoneNumberPermissionState.status){
                            is PermissionStatus.Granted ->{
                                coroutineScope.launch {

                                    val nb :TelephonyManager= context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                                    appState.scaffoldState.snackbarHostState.showSnackbar(nb.line1Number)

                                }
                            }
                            is PermissionStatus.Denied ->{
                                phoneNumberPermissionState.launchPermissionRequest()
                            }
                        }
                    },
                    onClickQ = {
                        
                    }
                )
                
                TextTitleWithBodyVertical(title = "Stack", titleColor = RedColor, modifier = Modifier.padding(5.dp), body = uiState?.projectDetail){
                    ProjectStackView(context, viewState.stackMap,appState,coroutineScope,viewState.selectedUser?.eid?:"...")
                }

                TextTitleWithBodyVertical(title = "프로젝트 소개", titleColor = RedColor, modifier = Modifier.padding(5.dp), body = uiState?.projectDetail){
                    Text(
                        text = uiState?.projectDetail!!,
                        style = MaterialTheme.typography.body1
                    )
                }
                TextWithSubTile(text = "관련 사진", color = RedColor, modifier = Modifier.padding(5.dp))
                TextTitleWithBodyVertical(title = "What I earn", titleColor = RedColor, modifier = Modifier.padding(5.dp), body = uiState?.projectDetail){
                    Column() {
                        uiState?.difficult?.map { ti ->
                            ItemForText(
                                title = ti.key,
                                contents = ti.value,
                                titleStyle = MaterialTheme.typography.subtitle2,
                                titleColor = BlackColor
                            )
                        }
                    }
                }


                Divider()
                TextWithSubTile(text = "관련 질문", color = RedColor, modifier = Modifier.padding(5.dp))
            }
        }
    }
}


@Composable
fun ProjectInteractButtons(
    onClickEvaluate : () -> Unit,
    onClickQ : ()->Unit
){
    Row(modifier = Modifier.fillMaxWidth()) {
        
        Button(onClick = { onClickEvaluate() }) {
            Text(text = "평가하기")
        }
        
        Button(onClick = { onClickQ() }) {
            Text(text = "질문하기")
        }
    }
}

@Composable
fun UserItem(user :UserForUi, onClick : (UserForUi) ->Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.clickable { onClick(user) }) {
        AsyncImage(
            model = user.uri,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Text(text = user.name)
    }
}


@Composable
fun ProjectBottomSheet(user: UserForUi?, eid: (String) -> Unit, phone : (String) ->Unit){
    Column(modifier = Modifier.padding(horizontal = HORIZONTAL_SPACE), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(3.dp))
        TextWithSubTile(text = user?.nickname?: "None", color = BlueColor,modifier = Modifier.padding(10.dp))
        Divider()
        TextWithSubTile(text = "전화하기", modifier = Modifier
            .fillMaxWidth()
            .clickable { phone(user?.phone ?: "") }
            .padding(10.dp))
        Divider()
        TextWithSubTile(text = "메일 보내기", modifier = Modifier
            .fillMaxWidth()
            .clickable { eid(user?.eid ?: "") }
            .padding(10.dp))
        Divider()
    }
}

@Composable
fun ProjectStackView(
    context: Context,
    stacks : Map<String,List<ProjectStringType>>?,
    appState: PortfolioAppState,
    scope : CoroutineScope,
    eid : String
){
    TextDescribeStacksApp(
        modifier = Modifier.padding(3.dp),
        text = stacks?: mapOf(),
        onClick = {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        it
                    )
                }
                context.startActivity(intent)
            }catch (e:Exception){
                scope.launch {
                    appState.scaffoldState.snackbarHostState.showSnackbar("URI주소가 잘못된 것 같아요. 개발자에게 연락주세요","메일").apply {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$eid")
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    )
}


@Composable
fun OutLinedUriClickableView(
    uri : String? = null,
    context : Context,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    composable : @Composable RowScope.() -> Unit
){
    OutlinedButton(
        modifier = Modifier.defaultMinSize(100.dp),
        onClick = {
            if (uri == null || uri == "this") {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "현재 제공하지 않거나, 해당 어플입니다."
                    )
                }
            } else {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        uri
                    )
                }
                context.startActivity(intent)
            }

        }) {
        composable()
    }
}