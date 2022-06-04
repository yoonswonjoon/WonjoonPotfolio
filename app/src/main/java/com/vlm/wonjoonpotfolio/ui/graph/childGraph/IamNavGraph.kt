package com.vlm.wonjoonpotfolio.ui.graph.childGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.AppUserStateViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.IAmRoute
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.ProjectDetailRoute
import com.vlm.wonjoonpotfolio.ui.iAm.ProjectViewModel

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.iAmNavGraph(
    userStateViewModel: AppUserStateViewModel,
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
        composable(
            Screen.Project.route,
        ){
            val projectViewModel = hiltViewModel<ProjectViewModel>()
//            projectViewModel.getUserList(viewModel.selectedProject?.participant?: listOf())
            ProjectDetailRoute(
                userStateViewModel= userStateViewModel,
                viewModel = viewModel,
                appState = appState,
                projectViewModel = projectViewModel
            )
        }
    }
}