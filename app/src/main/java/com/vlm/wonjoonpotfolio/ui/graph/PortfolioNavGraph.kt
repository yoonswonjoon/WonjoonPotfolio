package com.vlm.wonjoonpotfolio.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.GraphList
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.graph.iAmNavGraph
import com.vlm.wonjoonpotfolio.ui.history.HistoryViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel

@Composable
fun PortfolioNavGraph(
    appState: PortfolioAppState,
    modifier : Modifier =  Modifier
) {
    val iamViewModel : IAmViewModel = viewModel()
    val historyViewModel : HistoryViewModel = viewModel()
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
        )

        composable(Screen.HistoryMain.route){
            val uiState by historyViewModel.uiState.collectAsState()
            Text(text = "@")
            LazyColumn{
               items(uiState) {
                   Row() {
                       Text(text = it.data)
                       AsyncImage(model = it.uri, contentDescription = null)
                   }
               }
            }
        }
        composable(Screen.ChatMain.route){
            Text(text = "3")
        }
        composable(Screen.EvaluateMain.route){
            Text(text = "4")
        }
        composable(Screen.SettingMain.route){
            Text(text = "5")
        }
    }
}