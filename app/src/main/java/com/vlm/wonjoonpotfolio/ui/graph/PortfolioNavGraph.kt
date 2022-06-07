package com.vlm.wonjoonpotfolio.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.vlm.wonjoonpotfolio.GraphList
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.graph.childGraph.iAmNavGraph
import com.vlm.wonjoonpotfolio.ui.history.HistoryViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.setting.SettingRoute
import com.vlm.wonjoonpotfolio.ui.setting.SettingViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PortfolioNavGraph(
    userStateViewModel: AppUserStateViewModel,
    appState: PortfolioAppState,
    selectCountry : (String) -> Unit,
    modifier : Modifier =  Modifier
) {
    val iamViewModel : IAmViewModel = viewModel()
    val historyViewModel : HistoryViewModel = viewModel()
    val settingViewModel : SettingViewModel = viewModel()

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
//        composable(Screen.ChatMain.route){
//            Text(text = "3")
//        }
//        composable(Screen.EvaluateMain.route){
//            Text(text = "4")
//        }
        composable(Screen.SettingMain.route){

            SettingRoute(
                userStateViewModel,
                settingViewModel
            )
//            Column() {
//                Button(onClick = {
//                    selectCountry("kr")
//                }) {
//                    Text(text = "kr")
//                }
//                Button(onClick = {
//                    selectCountry("en")
//                }) {
//                    Text(text = "en")
//                }
//                Text(stringResource(id = R.string.test))
//            }

        }
    }
}