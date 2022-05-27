package com.vlm.wonjoonpotfolio.ui.iAm

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.HORIZONTAL_SPACE
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.TOOL_ELEVATION
import com.vlm.wonjoonpotfolio.ui.component.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun IAmRoute(
    viewModel : IAmViewModel,
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    toProject : (ProjectData) -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()
    val (rememberLoading,onShowed) = remember{
        mutableStateOf(viewState.isLoading)
    }
    val coroutineScope = rememberCoroutineScope()
//    if(rememberLoading){
//        coroutineScope.launch {
//            scaffoldState.snackbarHostState.showSnackbar(
//                "로딩중입니다"
//            )
//        }
//        onShowed(false)
//    }

    val lazyListState = rememberLazyListState()
    IAmRoute(viewState = viewState, lazyListState = lazyListState,toProject = toProject )
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun IAmRoute(
    viewState: IAmMainViewState,
    lazyListState: LazyListState,
    toProject : (ProjectData) -> Unit
) {

    val shouldShowSimpleTop  = lazyListState.firstVisibleItemIndex > 0
    val projectState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(HORIZONTAL_SPACE),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        item {
            TopMainView(
                img = viewState.img,
                name = viewState.name,
                birthDay = viewState.birthday,
                school = viewState.school,
                phone = viewState.phone,
                email = viewState.eid,
            )
        }

        item {

            ItemForText(
                modifier = Modifier.defaultMinSize(150.dp),
                title = "경력",
                contents = viewState.before.replace("\\n","\n")
            )

        }

        item {

                ItemForText(
                    modifier = Modifier.defaultMinSize(150.dp),
                    title = "소개",
                    contents = viewState.introduce.replace("\\n","\n")
                )

        }


        item {
            TextWithSubTile(
                text = "프로젝트",
                modifier = Modifier.padding(vertical = 5.dp),
                color = RedColor
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(HORIZONTAL_SPACE),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = projectState
            ){
                items(viewState.projectList){ project ->
                    ProjectItem( project = project, toProject = toProject)
                }
            }
        }

        item {
            ItemForText(
                title = "TMI",
                contents = ""
            )
        }
        items(listOf("Tmi #1","Tmi #2","Tmi #3","Tmi #4","Tmi #5","Tmi #6","Tmi #7","Tmi #8")){
            TMIButton(it)
        }

    }


    TopMainViewCollapsed(
        shouldShowSimpleTop,
        viewState.img,
        viewState.name
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(HORIZONTAL_SPACE),
    ){
        FloatingAction(
            visibility = shouldShowSimpleTop,
            modifier = Modifier.align(Alignment.BottomEnd),
            icon = Icons.Rounded.KeyboardArrowUp
        ) {
            coroutineScope.launch {
                lazyListState.scrollToItem(0)
            }
        }
    }

    
    


}


@Composable
fun TopMainView(
    img : Uri?,
    name: String,
    birthDay : String,
    school : String,
    phone : String,
    email : String,
    ){
    Row(
        modifier = Modifier
            .height(150.dp)
            .padding(10.dp)
            .defaultMinSize(150.dp)
    ) {

        Spacer(modifier = Modifier.width(HORIZONTAL_SPACE))
        AsyncImage(
            model = img,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.width(HORIZONTAL_SPACE))

        Column() {
            Text(text = name)
            Text(text = birthDay)
            Text(text = school)
            Text(text = phone)
            Text(text = email)
        }
    }
}

@Composable
fun TopMainViewCollapsed(
    show : Boolean,
    uri : Uri?,
    name : String
){
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            elevation = TOOL_ELEVATION,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(HORIZONTAL_SPACE))
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(HORIZONTAL_SPACE))
                Text(text = name, style = MaterialTheme.typography.subtitle1, )
            }

        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectItem(
    project : ProjectData,
    toProject : (ProjectData) -> Unit
){
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Card(
            onClick = {
                      toProject(project)
            },
        ) {
            Row(
                Modifier
                    .height(90.dp)
                    .width(200.dp)) {

                AsyncImage(model = project.uri,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(90.dp)
                        .width(70.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = project.name, style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = project.long, style = MaterialTheme.typography.caption)
                    Text(text = project.briefEx ,style = MaterialTheme.typography.caption)
                }
            }
        }

    }
}

@Composable
fun TMIButton(
    title :String = "TMI # 1"
){
    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)) {
            Text(text = title)
        }
    }
}

@Composable
fun ItemForText(
    modifier : Modifier = Modifier,
    title: String,
    contents : String,
    titleColor: Color = RedColor,
    contentsColor: Color = BlackColor,
    titleStyle : TextStyle = MaterialTheme.typography.h6,
    contentsStyle: TextStyle = MaterialTheme.typography.body1,
    visibility : Boolean = true
){
    if (visibility){
        Column(
            modifier= modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextWithSubTile(
                text = title,
                color = titleColor,
                modifier = Modifier,
                style = titleStyle
            )
            Row() {
                Spacer(modifier = Modifier.width(10.dp))
                TextWithMainBody(
                    text = contents,
                    color = contentsColor,
                    style = contentsStyle
                )
            }


        }
    }
}



@Preview
@Composable
fun TopMainCollapsePreview(){
    TopMainViewCollapsed(show = true, uri = null, name = "윤원준")
}

@Preview()
@Composable
fun TopMainViewPreView(){
    TopMainView(img = null, name = "윤원준", birthDay = "1992.08.01", school = "한양대(서울) 건설환경공학과", phone = "01029771536", email = "doojoons@naver.com" )
}

@Preview
@Composable
fun ProjectItemPreview(){
    ProjectItem(ProjectData()){}
}