package com.vlm.wonjoonpotfolio.ui.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import kotlinx.coroutines.flow.asStateFlow

fun NavGraphBuilder.iAmNavGraph(
    navHostController: NavHostController,
    viewModel: IAmViewModel,
    scaffoldState: ScaffoldState,
    startDestination : String,
    route: String
) {
    navigation(
        startDestination = startDestination,
        route = route
    ){
        composable(Screen.IAmMain.route){

            val uiState by viewModel.uiState.collectAsState()

            Column() {
                Text(text = "I_AM Main")
                if(uiState.isLoading){
                    Text(text = "로딩중입니당")
                }else{
                    Text(text = uiState.basicIntro)
                }
                TextButton(onClick = { navHostController.navigate(Screen.Project.route) }) {
                    Text(text = "To_DETAIL")
                }
            }
        }
        composable(Screen.Project.route){
            Text(text = "Project Detail")
        }
    }
}