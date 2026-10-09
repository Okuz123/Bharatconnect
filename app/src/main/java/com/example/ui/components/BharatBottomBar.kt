package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.AppScreen
import com.example.ui.theme.*

data class NavigationItem(
    val screen: AppScreen,
    val title: String,
    val icon: ImageVector,
    val tag: String
)

@Composable
fun BharatBottomBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(AppScreen.HOME, "Services", Icons.Default.Handyman, "nav_services"),
        NavigationItem(AppScreen.TRANSIT, "Cab & Bus", Icons.Default.DirectionsCar, "nav_transit"),
        NavigationItem(AppScreen.SCHEMES, "Yojanas", Icons.Default.AccountBalance, "nav_schemes"),
        NavigationItem(AppScreen.WALLET, "Wallet", Icons.Default.AccountBalanceWallet, "nav_wallet"),
        NavigationItem(AppScreen.SUPPORT, "Support", Icons.Default.HeadsetMic, "nav_support")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) BharatNavy else BharatTextSecondary
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) BharatNavy else BharatTextSecondary,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = BharatSaffronContainer
                ),
                modifier = Modifier.testTag(item.tag)
            )
        }
    }
}
