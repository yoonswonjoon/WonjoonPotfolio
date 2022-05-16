package com.vlm.wonjoonpotfolio

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vlm.wonjoonpotfolio.ui.IamDestination.I_AM_MAIN
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.CHAT
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.EVALUATE
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.HISTORY
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.I_AM
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.SETTING
import com.vlm.wonjoonpotfolio.ui.PortfolioNavGraph
import com.vlm.wonjoonpotfolio.ui.theme.WonjoonPotfolioTheme

@Composable
fun PortfolioMain() {
    WonjoonPotfolioTheme{
        val appState = rememberPortfolioAppState()

        val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: I_AM

        Scaffold(
            modifier = Modifier,
            topBar = { Text(text = currentRoute) },
            bottomBar = {
                if(appState.shouldShowBar){
                    PortfolioBottomNav(
                        currentRoute,
                        appState = appState,
                        navToRoute = appState::moveByBottomNavigation
                    )
                }
            },
            scaffoldState = appState.scaffoldState
        ) {
            PortfolioNavGraph(
                appState.navHostController,
                appState.scaffoldState
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
        appState.bottomItems.map {
            BottomNavigationItem(
                selected = current == it,
                onClick = { navToRoute(it) },
                icon = { Text(text = it) },
            )
        }
    }
}

class PortfolioAppState(
    val scaffoldState: ScaffoldState,
    val navHostController: NavHostController,
){
    val bottomItems = listOf<String>(I_AM,HISTORY, CHAT, EVALUATE, SETTING)
    val MainNavPage = listOf<String>(I_AM_MAIN,HISTORY, CHAT, EVALUATE, SETTING)

    val currentRoute  : String
    get() = navHostController.currentDestination?.route?: I_AM_MAIN

    val shouldShowBar : Boolean
         get() = currentRoute in MainNavPage

    fun moveByBottomNavigation(route : String){
        if(route != currentRoute) {
            navHostController.navigate(route){
                popUpTo(I_AM_MAIN){
                    saveState = true
                }
            }
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