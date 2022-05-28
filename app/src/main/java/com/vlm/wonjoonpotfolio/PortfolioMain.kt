package com.vlm.wonjoonpotfolio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.domain.ModifierSetting.TOOL_ELEVATION
import com.vlm.wonjoonpotfolio.ui.IamDestination.I_AM_MAIN
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.CHAT
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.EVALUATE
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.HISTORY
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.I_AM
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.SETTING
import com.vlm.wonjoonpotfolio.ui.PortfolioNavGraph
import com.vlm.wonjoonpotfolio.ui.theme.WonjoonPotfolioTheme


sealed class Screen(val route: String, ){
    object IAmMain :Screen("IamMain")
    object Project :Screen("Project")

    object HistoryMain :Screen("HistoryMain")

    object ChatMain :Screen("ChatMain")

    object EvaluateMain :Screen("EvaluateMain")

    object SettingMain :Screen("SettingMain")
}
//
sealed class GraphList(val route : String){
    object RootGraph : GraphList("Root")
    object IAmGraph :GraphList("IAm")
    object HistoryGraph :GraphList("History")
    object ChatGraph :GraphList("Chat")
    object EvaluateGraph :GraphList("Evaluate")
    object SettingGraph :GraphList("Setting")
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PortfolioMain() {
    WonjoonPotfolioTheme{
        val appState = rememberPortfolioAppState()

        val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: I_AM

        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    if(!appState.shouldShowBar){
                        Icon(Icons.Rounded.ArrowBack, null,modifier = Modifier.clickable {
                            appState.navHostController.popBackStack()
                        })
                    }
                    Text(text = currentRoute)
                    if(currentRoute == Screen.Project.route){
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = appState.detailProject)
                    }
                }

                     },
            bottomBar = {
                if(appState.shouldShowBar){
                    PortfolioBottomNav(
                        currentRoute,
                        appState = appState,
                        navToRoute = appState::moveByBottomNavigation
                    )
                }
            },
            scaffoldState = appState.scaffoldState,
        ) {

            PortfolioNavGraph(
                appState,
                modifier = Modifier.padding(it)
            )
        }
    }
}



@Composable
fun PortfolioBottomNav(
    current : String,
    appState: PortfolioAppState,
    navToRoute: (String) ->Unit
){
    BottomNavigation() {
        appState.mainNavScreen.map {
            BottomNavigationItem(
                selected = current == it.route,
                onClick = { navToRoute(it.route) },
                icon = { Text(text = it.route) },
            )
        }
    }
}

class PortfolioAppState(
    val scaffoldState: ScaffoldState,
    val navHostController: NavHostController,
){

    var detailProject = ""
    fun setDetailProjectName(s : String) { detailProject = s}

    val rootNav = listOf<String>(I_AM,HISTORY, CHAT, EVALUATE, SETTING)
    val mainNavScreen = listOf(
        Screen.IAmMain,
        Screen.HistoryMain,
      //  Screen.ChatMain,
     //   Screen.EvaluateMain,
        Screen.SettingMain
    )

    val currentRoute  : String
    get() = navHostController.currentDestination?.route?: I_AM_MAIN

    val shouldShowBar : Boolean
         get() = currentRoute in mainNavScreen.map { it.route }

    fun moveByBottomNavigation(route : String){
        if(route != currentRoute) {
            navHostController.navigate(route){
                popUpTo(Screen.IAmMain.route){
                    saveState = true
                }
            }
        }
    }

    fun navigateToProjectDetail(projectData: ProjectData){
        setDetailProjectName(projectData.name)
        navHostController.navigate(Screen.Project.route){
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberPortfolioAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navHostController: NavHostController = rememberNavController()
) = remember(scaffoldState,navHostController){
    PortfolioAppState(scaffoldState,navHostController)
}