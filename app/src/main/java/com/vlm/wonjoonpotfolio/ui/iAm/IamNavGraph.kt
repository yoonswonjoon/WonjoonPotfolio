package com.vlm.wonjoonpotfolio.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.ui.IamDestination
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination

fun NavGraphBuilder.iAmNavGraph(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState
) {
    navigation(
        startDestination = IamDestination.I_AM_MAIN,
        route = PortfolioDestination.I_AM
    ){
        composable(IamDestination.I_AM_MAIN){
            Column() {
                Text(text = "I_AM Main")
                TextButton(onClick = { navHostController.navigate(IamDestination.PROJECT_DETAIL) }) {
                    Text(text = "To_DETAIL")
                }
            }
        }
        composable(IamDestination.PROJECT_DETAIL){
            Text(text = "Project Detail")
        }
    }
}