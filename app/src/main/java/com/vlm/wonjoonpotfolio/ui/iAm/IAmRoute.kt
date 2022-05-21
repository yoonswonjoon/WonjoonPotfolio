package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.HORIZONTAL_SPACE
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.TOOL_ELEVATION

@Composable
fun IAmRoute(
    viewModel : IAmViewModel,
    navHostController: NavHostController,
) {
    val viewState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    IAmRoute(viewState = viewState, lazyListState = lazyListState)
}


@Composable
fun IAmRoute(
    viewState: IAmMainViewState,
    lazyListState: LazyListState,
) {

    val shouldShowSimpleTop  = lazyListState.firstVisibleItemIndex > 0

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(HORIZONTAL_SPACE)
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
            Text(text = viewState.introduce.replace("\\n","\n"), style = MaterialTheme.typography.body1)
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(HORIZONTAL_SPACE),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(viewState.projectList){ project ->
                    ProjectItem(project)
                }
            }
        }

        item {
            Text("티엠아이 정보 ",style = MaterialTheme.typography.h2)
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

@Composable
fun ProjectItem(
    project : ProjectData
){
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(70.dp)
                .width(150.dp)
        ) {

            AsyncImage(
                model = project.uri,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(70.dp)
                    .width(60.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column() {
                Text(text = project.name)
                Text(text = project.long)
                Text(text = project.briefEx)
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
    ProjectItem(ProjectData())
}