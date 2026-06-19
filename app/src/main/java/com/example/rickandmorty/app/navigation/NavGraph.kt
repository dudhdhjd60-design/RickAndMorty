package com.example.rickandmorty.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgument
import com.example.rickandmorty.feature.details.presentation.CharacterDetailsScreen
import com.example.rickandmorty.feature.list.presentation.CharacterListScreen
import com.example.rickandmorty.feature.list.presentation.FavouritesScreen

sealed class Screen(val route: String) {
    object CharacterList : Screen("character_list")
    object Favourites : Screen("favourites")
    object CharacterDetails : Screen("character_details/{characterId}") {
        fun createRoute(characterId: Int) = "character_details/$characterId"
    }
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.CharacterList, "Characters", Icons.Default.List),
    BottomNavItem(Screen.Favourites, "Favourites", Icons.Default.Favorite)
)

@Composable
fun RickAndMortyNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in listOf(
        Screen.CharacterList.route,
        Screen.Favourites.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.CharacterList.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.CharacterList.route) {
                CharacterListScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetails.createRoute(characterId))
                    }
                )
            }
            composable(Screen.Favourites.route) {
                FavouritesScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetails.createRoute(characterId))
                    }
                )
            }
            composable(
                route = Screen.CharacterDetails.route,
                arguments = listOf(
                    navArgument("characterId") { type = NavType.IntType }
                )
            ) {
                CharacterDetailsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
