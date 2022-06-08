package com.vlm.wonjoonpotfolio

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.vlm.wonjoonpotfolio.data.project.ProjectData
import com.vlm.wonjoonpotfolio.ui.AppMainViewModel
import com.vlm.wonjoonpotfolio.ui.IamDestination.I_AM_MAIN
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.CHAT
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.EVALUATE
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.HISTORY
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.I_AM
import com.vlm.wonjoonpotfolio.ui.PortfolioDestination.SETTING
import com.vlm.wonjoonpotfolio.ui.PortfolioNavGraph
import com.vlm.wonjoonpotfolio.ui.component.CircularProcessingDialog
import com.vlm.wonjoonpotfolio.ui.component.DialogBasic
import com.vlm.wonjoonpotfolio.ui.theme.WonjoonPotfolioTheme
import java.lang.Exception
import java.util.*


sealed class Screen(val route: String, val res: Int) {
    object IAmMain : Screen("IamMain", R.string.home)
    object Project : Screen("Project", R.string.home)

    object HistoryMain : Screen("HistoryMain",R.string.history)

    object ChatMain : Screen("ChatMain",R.string.home)

    object EvaluateMain : Screen("EvaluateMain",R.string.home)
    object SettingMain : Screen("SettingMain",R.string.setting)
}

//
sealed class GraphList(val route: String) {
    object RootGraph : GraphList("Root")
    object IAmGraph : GraphList("IAm")
    object HistoryGraph : GraphList("History")
    object ChatGraph : GraphList("Chat")
    object EvaluateGraph : GraphList("Evaluate")
    object SettingGraph : GraphList("Setting")
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PortfolioMain(
) {
    WonjoonPotfolioTheme {
        val userViewModel: AppMainViewModel = viewModel()
        val userState by userViewModel.loginViewState.collectAsState()
        val context = LocalContext.current
        val appState = rememberPortfolioAppState()

        val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: I_AM

        val locale by userViewModel.localeData.collectAsState()

        SetLanguage(locale = locale)

        if(userState.isLoading){
            CircularProcessingDialog(){}
        }

        if(userState.loginDialogView){
            PortfolioLoginDialog(
                onDismiss = userViewModel::loginClose,
                onLogin = {
                    loginPortfolio(
                        context = context,
                        login = userViewModel::login
                    )
                }
            )
        }

        if (userState.mainErrorList.isNotEmpty()) {
            val error = userState.mainErrorList.first()
            DialogBasic(
                onDismiss = { userViewModel.dismissError(error) },
                onOk = { userViewModel.dismissError(error) },
                justOkBtn = true,
                okText = stringResource(id = R.string.confirm),
                noText = stringResource(id = R.string.cancel)
            ) {
                Text(text = error.msg)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        if (userState.userLoginFailedDialog) {
            DialogBasic(
                onDismiss = userViewModel::dismissLoginFailedD,
                onOk = userViewModel::dismissLoginFailedD,
                okText = stringResource(id = R.string.confirm),
                noText =stringResource(id = R.string.cancel),
                justOkBtn = true
            ) {
                Text(text = stringResource(id = R.string.login_error))
            }
        }

        Scaffold(
            modifier = Modifier,
            topBar = {
                if (appState.shouldShowBar) {
                    TopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),

                        ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        if (!appState.shouldShowBar) {
                            Icon(Icons.Rounded.ArrowBack, null, modifier = Modifier.clickable {
                                appState.navHostController.popBackStack()
                            })
                        }
                        Text(text = currentRoute)
                        if (currentRoute == Screen.Project.route) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = appState.detailProject)
                        }

                        UserStateBox(
                            userState.isLoading,
                            name = userState.name,
                            loginComplete = userState.loginComplete,
                            login = userViewModel::onLoginOpen
                        )
                    }
                }


            },
            bottomBar = {
                if (appState.shouldShowBar) {
                    PortfolioBottomNav(
                        currentRoute,
                        appState = appState,
                        navToRoute = appState::moveByBottomNavigation
                    )
                }
            },
            scaffoldState = appState.scaffoldState,
        ) {
            if (userState.newUser) {
                DialogBasic(
                    onDismiss = userViewModel::cancelSignIn,
                    onOk = userViewModel::signIn,
                    okText = stringResource(id = R.string.create_id),
                    noText =stringResource(id = R.string.cancel),
                    justOkBtn = false
                ) {
                    Text(text = stringResource(id = R.string.kakao_sign_in))
                }
            }

            PortfolioNavGraph(
                userViewModel,
                appState,
                selectCountry = userViewModel::setLocale,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
private fun SetLanguage(locale: String) {
    val configuration = LocalConfiguration.current
    configuration.setLocale(Locale(locale))
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}



@Composable
fun PortfolioBottomNav(
    current: String,
    appState: PortfolioAppState,
    navToRoute: (String) -> Unit,
) {
    BottomNavigation() {
        appState.mainNavScreen.map {
            BottomNavigationItem(
                selected = current == it.route,
                onClick = { navToRoute(it.route) },
                icon = { Text(text = stringResource(id = it.res)) },
            )
        }
    }
}

class PortfolioAppState(
    val scaffoldState: ScaffoldState,
    val navHostController: NavHostController
) {
    var detailProject = ""
    fun setDetailProjectName(s: String) {
        detailProject = s
    }

    val rootNav = listOf<String>(I_AM, HISTORY, CHAT, EVALUATE, SETTING)
    val mainNavScreen = listOf(
        Screen.IAmMain,
        Screen.HistoryMain,
        //  Screen.ChatMain,
        //   Screen.EvaluateMain,
        Screen.SettingMain
    )

    val currentRoute: String
        get() = navHostController.currentDestination?.route ?: I_AM_MAIN

    val shouldShowBar: Boolean
        get() = currentRoute in mainNavScreen.map { it.route }

    fun moveByBottomNavigation(route: String) {
        if (route != currentRoute) {
            navHostController.navigate(route) {
                popUpTo(Screen.IAmMain.route) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToProjectDetail(projectData: ProjectData) {
        setDetailProjectName(projectData.name)
        navHostController.navigate(Screen.Project.route) {
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
) = remember(scaffoldState, navHostController) {
    PortfolioAppState(scaffoldState, navHostController)
}

@Composable
fun UserStateBox(
    isLoading: Boolean,
    name: String?,
    loginComplete: Boolean,
//    context: Context,
    login: (/*String?, String?*/) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val n = if (isLoading) {
            "로딩중"
        } else name

        Row(modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically) {
            if (!loginComplete) {
                Button(onClick = {
                    login()
                }) {
                    Text(text = stringResource(id = R.string.log_in))
                }
            }

            Text(text = name ?: stringResource(id = R.string.not_in_log_in), modifier = Modifier)

            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun PortfolioLoginDialog(
    onDismiss:()->Unit,
    onLogin : () -> Unit
){
    DialogBasic(
        onDismiss = onDismiss,
        onOk = onDismiss,
        justOkBtn = true,
        okText = stringResource(id = R.string.cancel),
        noText =stringResource(id = R.string.cancel),
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_medium_narrow),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .clickable { onLogin() }
        )
    }
}

fun loginPortfolio(
    context: Context,
    login : (String?, String?) -> Unit
){
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            UserApiClient.instance.me { user, e ->
                if (e != null) {
                    login(
                        e.message ?: "none", e.message ?: "none"
                    )
                }
                try {
                    login(user?.kakaoAccount?.email, user?.id?.toString())
                } catch (e: Exception) {
                    login(e.message ?: "none", e.message ?: "none")
                }
            }
            Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        Log.e(ContentValues.TAG, "카톡 로긴")
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오톡으로 로그인 실패", error)

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context,
                    callback = callback)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
            }
        }
    } else {
        Log.e(ContentValues.TAG, "카톡 이메일 로긴")
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}