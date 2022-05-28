package com.vlm.wonjoonpotfolio.ui.graph.childGraph

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vlm.wonjoonpotfolio.Screen

//fun NavGraphBuilder.HistoryNavGraph(
//    navHostController: NavHostController,
//    scaffoldState: ScaffoldState,
//    startDestination : String,
//    route: String
//) {
//    navigation(
//        startDestination = startDestination,
//        route = route
//    ){
//        composable(Screen.IAmMain.route){
//            Column() {
//                Text(text = "I_AM Main")
//                TextButton(onClick = { navHostController.navigate(Screen.Project.route) }) {
//                    Text(text = "To_DETAIL")
//                }
//            }
//        }
//        composable(Screen.Project.route){
//            Text(text = "Project Detail")
//        }
//    }
//}