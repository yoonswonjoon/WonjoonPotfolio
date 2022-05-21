package com.vlm.wonjoonpotfolio.ui.iAm

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.TOOL_ELEVATION

@Composable
fun IAmRoute(
    viewModel : IAmViewModel,
    navHostController: NavHostController
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
        state = lazyListState
    ){
        item {
            CollapseControlTopMain(
                img = viewState.img,
                name = viewState.basicIntro,
                birthDay = viewState.basicIntro,
                school = viewState.basicIntro,
                phone = viewState.basicIntro,
                email = viewState.basicIntro,
            )
        }

        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
        item {
            Text(text = "asasdasdads", style = MaterialTheme.typography.h1)
        }
    }

    AnimatedVisibility(
        visible = shouldShowSimpleTop,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            elevation = TOOL_ELEVATION,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {
            Row() {
                AsyncImage(
                    model = viewState.img,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "원주니", style = MaterialTheme.typography.subtitle1, )
            }

        }

    }
}


@Composable
fun CollapseControlTopMain(
    img : Uri?,
    name: String,
    birthDay : String,
    school : String,
    phone : String,
    email : String,
    ){
    Row(
        modifier = Modifier.height(150.dp)
    ) {

        AsyncImage(
            model = img,
            contentDescription = null
        )

        Column() {
            Text(text = name)
            Text(text = birthDay)
            Text(text = school)
            Text(text = school)
            Text(text = school)
            Text(text = school)
            Text(text = school)
            Text(text = school)

        }
    }
}