package com.vlm.wonjoonpotfolio.ui.graph.childGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vlm.wonjoonpotfolio.PortfolioAppState
import com.vlm.wonjoonpotfolio.Screen
import com.vlm.wonjoonpotfolio.ui.iAm.IAmRoute
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.iAm.projectDetail.ProjectDetailRoute
import com.vlm.wonjoonpotfolio.ui.iAm.ProjectViewModel

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.iAmNavGraph(
    appState: PortfolioAppState,
    viewModel: IAmViewModel,
    startDestination : String,
    iamListState: LazyListState,
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
                iamListState = iamListState,
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
            ProjectDetailRoute(
                viewModel = viewModel,
                appState = appState,
                projectViewModel = projectViewModel
            )
        }
    }
}