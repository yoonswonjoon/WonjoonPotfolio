
package com.vlm.wonjoonpotfolio.ui.iAm.projectDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.HORIZONTAL_SPACE
import com.vlm.wonjoonpotfolio.domain.PortfolioError
import com.vlm.wonjoonpotfolio.domain.ProjectStringType
import com.vlm.wonjoonpotfolio.ui.component.*
import com.vlm.wonjoonpotfolio.ui.iAm.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProjectDetailRoute(
    viewModel: IAmViewModel,
    projectViewModel: ProjectViewModel,
    appState: PortfolioAppState,
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

    LaunchedEffect(true) {
        projectViewModel.initProjectView(
            userListOperator!!,
            uiState?.projectStatecks?.stacks
        )
        projectViewModel.amIin(uiState?.uid?: "")

    }

    if (viewState.errors.isNotEmpty()) {
        val error = viewState.errors.first()
        DialogBasic(
            onDismiss = { projectViewModel.dismissError(error) },
            onOk = { projectViewModel.dismissError(error) },
            justOkBtn = true,
            okText = stringResource(id = R.string.confirm),
            noText = stringResource(id = R.string.cancel)
        ) {
            Text(text = error.msg)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    if(viewState.evaluationDialogShow){
        EvaluateWithPoint(
            viewState.evaluationData.point,
            onDismiss = projectViewModel::evaluationDialogDismiss,
            onOk = {
                try {
                    projectViewModel.proceedEvaluation(uiState!!.uid)
                }catch (e:Exception){

                }
            }
        )
    }

    Scaffold(
        topBar = {
            ProjectTopBar(
                route = viewState.currentDetailRoute,
                appState = appState,
                projectViewModel = projectViewModel
            )
        }
    ) {
        when(viewState.currentDetailRoute){
            ProjectDetailPage.DetailPage ->{
                ProjectDetailMain(
                    context = context,
                    viewState = viewState,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    verticalScrollState = scrollState,
                    uiState = uiState,
                    appState = appState,
                    coroutineScope = coroutineScope,
                    selectUser = projectViewModel::selectUser,
                    openEvaluate = projectViewModel::openEvaluation,
                    onClickQ = {
                        projectViewModel.addErrors(PortfolioError(msg = context.resources.getString(R.string.not_yet_support)))
//                        throw RuntimeException("Test Crash")
                    }
                )
            }
            ProjectDetailPage.EvaluationPage ->{
                ProjectEvaluationRoute(
                    haveEvaluated = viewState.amIin?: false,
                    evaluate = projectViewModel::evaluation,
                    onCloseEvaluate = projectViewModel::closeEvaluation,
                    processing  = viewState.evaluateProcess,
                    evaluateList = viewState.evaluateList,
                    totalPoint = viewState.totalPoint,
                    reLoading = {

                    },
                    myEvaluate = viewState.myEvaluateData,
                    participantCount = viewState.totalParticipant
                )
            }
            ProjectDetailPage.EditEvaluationPage ->{

            }
        }
    }


}


@Composable
fun ProjectInteractButtons(
    onClickEvaluate: () -> Unit,
    onClickQ: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        Button(onClick = { onClickEvaluate() }) {
            Text(text = stringResource(id = R.string.do_evaluate))
        }

        Button(onClick = { onClickQ() }) {
            Text(text = stringResource(id = R.string.question))
        }
    }
}

@Composable
fun UserItem(user: UserForUi, onClick: (UserForUi) -> Unit) {
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
fun ProjectBottomSheet(user: UserForUi?, eid: (String) -> Unit, phone: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = HORIZONTAL_SPACE),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(3.dp))
        TextWithSubTile(text = user?.nickname ?: "None",
            color = BlueColor,
            modifier = Modifier.padding(10.dp))
        Divider()
        TextWithSubTile(text = stringResource(id = R.string.call), modifier = Modifier
            .fillMaxWidth()
            .clickable { phone(user?.phone ?: "") }
            .padding(10.dp))
        Divider()
        TextWithSubTile(text = stringResource(id = R.string.send_email), modifier = Modifier
            .fillMaxWidth()
            .clickable { eid(user?.eid ?: "") }
            .padding(10.dp))
        Divider()
    }
}

@Composable
fun ProjectStackView(
    context: Context,
    stacks: Map<String, List<ProjectStringType>>?,
    appState: PortfolioAppState,
    scope: CoroutineScope,
    eid: String,
) {
    TextDescribeStacksApp(
        modifier = Modifier.padding(3.dp),
        text = stacks ?: mapOf(),
        onClick = {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        it
                    )
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                scope.launch {
                    appState.scaffoldState
                        .snackbarHostState
                        .showSnackbar(context.resources.getString(R.string.uri_failed), context.resources.getString(R.string.email)).apply {
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
    uri: String? = null,
    context: Context,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    composable: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        modifier = Modifier.defaultMinSize(100.dp),
        onClick = {
            if (uri == null || uri == "this") {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        context.resources.getString(R.string.not_yet_support_or_this_app)
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

@Composable
fun EvaluateWithPoint(
    point : Int,
    onDismiss : ()-> Unit,
    onOk : () -> Unit
){
    DialogBasic(
        onDismiss = { onDismiss() },
        onOk = { onOk() },
        okText = stringResource(id = R.string.confirm),
        noText = stringResource(id = R.string.cancel)
    ) {
        Text(text = stringResource(id = R.string.evaluating,point)/*"${point}점으로 평가합니다"*/)
    }
}

@Composable
fun ProjectTopBar(
    route : ProjectDetailPage,
    appState: PortfolioAppState,
    projectViewModel: ProjectViewModel
){
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),

        ) {
        Spacer(modifier = Modifier.width(10.dp))
        Icon(Icons.Rounded.ArrowBack, null, modifier = Modifier.clickable {
            when(route){
                ProjectDetailPage.DetailPage ->{
                    appState.navHostController.popBackStack()
                }
                ProjectDetailPage.EvaluationPage ->{
                    projectViewModel.closeEvaluation()
                }
                else->{

                }
            }

        })
        Text(text = stringResource(id = R.string.project))
        Text(text = appState.detailProject)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectDetailMain(
    context: Context,
    viewState : ProjectViewState,
    bottomSheetScaffoldState: ModalBottomSheetState,
    verticalScrollState: ScrollState,
    uiState:  ProjectData?,
    appState : PortfolioAppState,
    coroutineScope: CoroutineScope,
    selectUser : (UserForUi) -> Unit,
    openEvaluate : ()->Unit,
    onClickQ: () -> Unit
){
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
        sheetShape = RoundedCornerShape(100f, 100f, 0f, 0f),
    ) {
        Column(
            Modifier
                .verticalScroll(verticalScrollState)
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
                        model = uiState?.uri
                            ?: painterResource(id = coil.base.R.drawable.notification_bg),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row() {
                        OutLinedUriClickableView(
                            uri = uiState?.downloadUri,
                            context = context,
                            scaffoldState = appState.scaffoldState,
                            coroutineScope = coroutineScope,
                        ) {
                            TextWithMainBody(text = stringResource(id = R.string.download))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        OutLinedUriClickableView(
                            uri = uiState?.git,
                            context = context,
                            scaffoldState = appState.scaffoldState,
                            coroutineScope = coroutineScope,
                        ) {
                            TextWithMainBody(text = stringResource(id = R.string.git_hub))
                        }
                    }

                    TextWithMainBody(text = uiState?.long ?: stringResource(id = R.string.dont_know))

                    TextWithMainBody(text = stringResource(id = R.string.participant))
                    if (!viewState.isLoading) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            viewState.list.forEach {
                                UserItem(it) { ui ->
                                    selectUser(ui)
                                    coroutineScope.launch {
                                        bottomSheetScaffoldState.show()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (viewState.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                ProjectInteractButtons(
                    onClickEvaluate = openEvaluate,
                    onClickQ = {
                        onClickQ()
                    }
                )

                TextTitleWithBodyVertical(title = stringResource(id = R.string.stack),
                    titleColor = RedColor,
                    modifier = Modifier.padding(5.dp),
                    body = uiState?.projectDetail) {
                    ProjectStackView(context,
                        viewState.stackMap,
                        appState,
                        coroutineScope,
                        viewState.selectedUser?.eid ?: "...")
                }

                TextTitleWithBodyVertical(title = stringResource(id = R.string.project_introduce),
                    titleColor = RedColor,
                    modifier = Modifier.padding(5.dp),
                    body = uiState?.projectDetail) {
                    Text(
                        text = uiState?.projectDetail!!,
                        style = MaterialTheme.typography.body1
                    )
                }
                TextWithSubTile(text = stringResource(id = R.string.pictures),
                    color = RedColor,
                    modifier = Modifier.padding(5.dp))

                TextTitleWithBodyVertical(title = stringResource(id = R.string.what_i_learn),
                    titleColor = RedColor,
                    modifier = Modifier.padding(5.dp),
                    body = uiState?.projectDetail) {
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
                TextWithSubTile(text = stringResource(id = R.string.related_question),
                    color = RedColor,
                    modifier = Modifier.padding(5.dp))
            }
        }
    }
}