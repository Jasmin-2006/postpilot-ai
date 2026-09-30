package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.NotificationsDialog
import com.example.ui.components.PostPilotBottomNav
import com.example.ui.components.PostPilotDrawerContent
import com.example.ui.components.PostPilotToast
import com.example.ui.components.PostPilotTopBar
import com.example.ui.components.PostSearchDialog
import com.example.ui.screens.AIAssistantScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.CreatePostScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MyPostsScreen
import com.example.ui.screens.PostDetailScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceLight
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: PostPilotViewModel = viewModel()
                PostPilotApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PostPilotApp(viewModel: PostPilotViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var showSearchDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }

    // Android back handler
    BackHandler(enabled = true) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else {
            val handled = viewModel.navigateBack()
            if (!handled) {
                // At root, let system handle exit
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                PostPilotDrawerContent(
                    currentScreen = currentScreen,
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                    },
                    onClose = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                PostPilotTopBar(
                    onOpenDrawer = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onOpenSearch = { showSearchDialog = true },
                    onOpenNotifications = { showNotificationsDialog = true },
                    onOpenProfile = { viewModel.navigateTo(Screen.SETTINGS) },
                    currentSectionLabel = when (currentScreen) {
                        Screen.DASHBOARD -> "Dashboard"
                        Screen.CREATE_POST_STAGE1, Screen.CREATE_POST_STAGE2 -> "Composer"
                        Screen.CALENDAR -> "Calendar"
                        Screen.MY_POSTS -> "Posts"
                        Screen.AI_ASSISTANT -> "AI Studio"
                        Screen.ANALYTICS -> "Analytics"
                        Screen.SETTINGS -> "Settings"
                        Screen.POST_DETAIL -> "Campaign"
                    }
                )
            },
            bottomBar = {
                PostPilotBottomNav(
                    currentScreen = currentScreen,
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SurfaceLight)
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "ScreenTransition"
                ) { screen ->
                    when (screen) {
                        Screen.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                        Screen.CREATE_POST_STAGE1 -> CreatePostScreen(viewModel = viewModel, stage = 1)
                        Screen.CREATE_POST_STAGE2 -> CreatePostScreen(viewModel = viewModel, stage = 2)
                        Screen.CALENDAR -> CalendarScreen(viewModel = viewModel)
                        Screen.MY_POSTS -> MyPostsScreen(viewModel = viewModel)
                        Screen.AI_ASSISTANT -> AIAssistantScreen(viewModel = viewModel)
                        Screen.ANALYTICS -> AnalyticsScreen(viewModel = viewModel)
                        Screen.SETTINGS -> SettingsScreen(viewModel = viewModel)
                        Screen.POST_DETAIL -> PostDetailScreen(viewModel = viewModel)
                    }
                }

                // Global Toast banner
                PostPilotToast(
                    message = toastMessage,
                    onDismiss = { viewModel.clearToast() }
                )
            }
        }
    }

    if (showSearchDialog) {
        PostSearchDialog(
            posts = posts,
            onDismiss = { showSearchDialog = false },
            onSelectPost = { id ->
                viewModel.openPostDetail(id)
            }
        )
    }

    if (showNotificationsDialog) {
        NotificationsDialog(
            onDismiss = { showNotificationsDialog = false }
        )
    }
}
