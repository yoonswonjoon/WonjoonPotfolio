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
import com.vlm.wonjoonpotfolio.ui.component.BlackColor
import com.vlm.wonjoonpotfolio.ui.component.RedColor
import com.vlm.wonjoonpotfolio.ui.component.TextWithMainBody
import com.vlm.wonjoonpotfolio.ui.component.TextWithSubTile
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProjectDetailRoute(
    viewModel: IAmViewModel,
    projectViewModel: ProjectViewModel,
    appState: PortfolioAppState
) {
    val uiState = viewModel.selectedProject
    val userList by projectViewModel.userList.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val userListOperator by rememberUpdatedState(newValue = uiState?.participant)
    
    LaunchedEffect(true){
        projectViewModel.getUserUriList(userListOperator!!)
    }
    
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
                    userList.forEach {
                        UserItem(it) {
//                            val intent = Intent(Intent.ACTION_DIAL).apply {
//                                data = Uri.parse("tel:" + "01029771536")
//                            }
//                            context.startActivity(intent)

//                            val intent = Intent(Intent.ACTION_SENDTO).apply {
//                                data = Uri.parse("mailto:"+it.eid)
//                            }
//                            context.startActivity(intent)
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