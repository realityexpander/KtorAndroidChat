package com.realityexpander.ktorandroidchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.realityexpander.ktorandroidchat.presentation.chat.ChatScreen
import com.realityexpander.ktorandroidchat.presentation.username.UsernameScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "username_screen"
            ) {
                composable("username_screen") {
                    UsernameScreen(onNavigate = navController::navigate)
                }
                composable(
                    route = "chat_screen/{username}/{id}",  // note: `id` is not used. Just for demo purposes.
                    arguments = listOf(
                        navArgument(name = "username") {
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(name = "id") {
                            type = NavType.IntType
                            //nullable = true
                        }
                    )
                ) { navBackStackEntry ->
                    val username = navBackStackEntry.arguments?.getString("username")
                    val id = navBackStackEntry.arguments?.getInt("id")

                    // note: the navArguments are passed as a bundle to the viewModel via the SavedStateHandle.
                    // We are also passing the same arguments to the ChatScreen composable (necessary?)

                    ChatScreen(username = username, id = id)
                }
            }
        }
    }
}