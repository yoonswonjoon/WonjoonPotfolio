package com.vlm.wonjoonpotfolio.ui.iAm

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.HORIZONTAL_SPACE
import com.vlm.wonjoonpotfolio.ui.component.*
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
    LaunchedEffect(true){
        projectViewModel.getUserUriList(userListOperator!!)
    }

    ModalBottomSheetLayout(
        sheetContent = { ProjectBottomSheet(
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
        ) },
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(100f,100f,0f,0f)
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
                        model = uiState?.uri,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column() {

                    OutlinedButton(
                        onClick = {
                            if(uiState?.downloadUri =="this"){
                                coroutineScope.launch {
                                    appState.scaffoldState.snackbarHostState.showSnackbar(
                                        "현재어플입니다."
                                    )
                                }
                            }else if(uiState?.downloadUri == null){

                            }else{
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(
                                        uiState.downloadUri
                                    )
                                }
                                context.startActivity(intent)
                            }

                        }) {
                        TextWithMainBody(text = "다운로드")

                    }
                    TextWithMainBody(text = uiState?.long ?: "알 수 없음.")
                    TextWithMainBody(text = "적용 스텍")
                    TextWithMainBody(text = "제작인원")
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
            TextWithSubTile(text = "프로젝트 소개", color = RedColor, modifier = Modifier.padding(5.dp))
            TextWithSubTile(text = "관련 사진", color = RedColor, modifier = Modifier.padding(5.dp))
            TextWithSubTile(text = "힘들었던 점", color = RedColor, modifier = Modifier.padding(5.dp))
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
            Divider()
            TextWithSubTile(text = "관련 질문", color = RedColor, modifier = Modifier.padding(5.dp))

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
        TextWithSubTile(text = "전화하기", modifier = Modifier.fillMaxWidth().clickable { phone(user?.phone?:"") }.padding(10.dp))
        Divider()
        TextWithSubTile(text = "메일 보내기", modifier = Modifier.fillMaxWidth().clickable { eid(user?.eid?:"") }.padding(10.dp))
        Divider()
    }
}