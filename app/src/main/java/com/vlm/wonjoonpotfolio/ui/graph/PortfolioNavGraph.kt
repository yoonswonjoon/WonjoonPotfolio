package com.vlm.wonjoonpotfolio.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.GraphList
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.graph.childGraph.iAmNavGraph
import com.vlm.wonjoonpotfolio.ui.history.HistoryViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.posting.PostingRoute
import com.vlm.wonjoonpotfolio.ui.posting.PostingViewModel
import com.vlm.wonjoonpotfolio.ui.setting.SettingRoute
import com.vlm.wonjoonpotfolio.ui.setting.SettingViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PortfolioNavGraph(
    userStateViewModel: AppMainViewModel,
    appState: PortfolioAppState,
    selectCountry : (String) -> Unit,
    modifier : Modifier =  Modifier
) {
    val iamViewModel : IAmViewModel = viewModel()
    val settingViewModel : SettingViewModel = viewModel()
    val postingViewModel : PostingViewModel = viewModel()

    val lazyListStatePosting = rememberLazyListState()

    val lazyListStateIam = rememberLazyListState()
    NavHost(
        navController = appState.navHostController,
        startDestination = PortfolioDestination.I_AM,
        route = GraphList.RootGraph.route,
        modifier = modifier,
    ){
        iAmNavGraph(
            appState = appState,
            viewModel = iamViewModel,
            startDestination = Screen.IAmMain.route,
            route = PortfolioDestination.I_AM,
            iamListState = lazyListStateIam
        )

        composable(Screen.HistoryMain.route){
            PostingRoute(postingViewModel,lazyListStatePosting)
        }
        composable(Screen.SettingMain.route){
            SettingRoute(
                userStateViewModel,
                settingViewModel
            )
        }
    }
}