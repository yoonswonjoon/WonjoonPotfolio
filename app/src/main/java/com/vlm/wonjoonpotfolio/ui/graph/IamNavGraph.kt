package com.vlm.wonjoonpotfolio.ui.graph

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.iAm.IAmRoute
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.ProjectDetailRoute
import kotlinx.coroutines.flow.asStateFlow

fun NavGraphBuilder.iAmNavGraph(
    appState: PortfolioAppState,
    viewModel: IAmViewModel,
    startDestination : String,
    route: String,
) {
    navigation(
        startDestination = startDestination,
        route = route
    ){
        composable(Screen.IAmMain.route){
            IAmRoute(
                viewModel = viewModel,
                navHostController = appState.navHostController,
                scaffoldState = appState.scaffoldState,
                toProject = {
                    viewModel.selectProject(it)
                    appState.navigateToProjectDetail(it)
                }
            )

        }
        composable(Screen.Project.route){
            ProjectDetailRoute(viewModel = viewModel)
        }
    }
}