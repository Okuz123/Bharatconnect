package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.BharatViewModel
import com.example.ui.components.BharatBottomBar
import com.example.ui.components.BharatTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: BharatViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()

                // Hardware & predictive back button handling
                BackHandler {
                    viewModel.handleBackPress()
                }

                Scaffold(
                    topBar = {
                        BharatTopAppBar(viewModel = viewModel)
                    },
                    bottomBar = {
                        // Show bottom navigation bar on non-admin screens or all screens
                        BharatBottomBar(
                            currentScreen = currentScreen,
                            onNavigate = { screen -> viewModel.navigateTo(screen) }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
                            when (screen) {
                                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                                AppScreen.TRANSIT -> TransitScreen(viewModel = viewModel)
                                AppScreen.SCHEMES -> SchemesScreen(viewModel = viewModel)
                                AppScreen.WALLET -> WalletScreen(viewModel = viewModel)
                                AppScreen.SUPPORT -> SupportScreen(viewModel = viewModel)
                                AppScreen.VERIFICATION -> VerificationScreen(viewModel = viewModel)
                                AppScreen.ADMIN -> AdminPortalScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
