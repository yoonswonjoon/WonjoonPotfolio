package com.vlm.wonjoonpotfolio.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vlm.wonjoonpotfolio.GraphList
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.graph.iAmNavGraph

@Composable
fun PortfolioNavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    modifier : Modifier =  Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PortfolioDestination.I_AM,
        route = GraphList.RootGraph.route,
        modifier = modifier,
    ){
        iAmNavGraph(
            navHostController = navController,
            scaffoldState = scaffoldState,
            startDestination = Screen.IAmMain.route,
            route = PortfolioDestination.I_AM
        )
        composable(Screen.HistoryMain.route){
            Text(text = "@")
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