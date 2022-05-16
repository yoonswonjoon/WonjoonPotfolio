package com.vlm.wonjoonpotfolio.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vlm.wonjoonpotfolio.ui.history.iAmNavGraph

@Composable
fun PortfolioNavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    modifier : Modifier =  Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PortfolioDestination.I_AM,
        modifier = modifier
    ){
        iAmNavGraph(
            navHostController = navController,
            scaffoldState = scaffoldState
        )
        composable(PortfolioDestination.HISTORY){
            Text(text = "@")
        }
        composable(PortfolioDestination.CHAT){
            Text(text = "3")
        }
        composable(PortfolioDestination.EVALUATE){
            Text(text = "4")
        }
        composable(PortfolioDestination.SETTING){
            Text(text = "5")
        }
    }
}