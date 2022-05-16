package com.vlm.wonjoonpotfolio.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
        composable(PortfolioDestination.I_AM){
            Text(text = "1")
        }
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