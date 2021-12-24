package com.zero.neon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zero.neon.common.theme.NeonTheme
import com.zero.neon.navigation.Game
import com.zero.neon.navigation.GamePause
import com.zero.neon.gamepause.GamePauseDialog

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NeonTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = Color.Transparent)

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Game.route
                ) {
                    composable(route = Game.route) {
                        GameScreen(onGamePause = { navController.navigate(GamePause.route) })
                    }
                    dialog(route = GamePause.route) {
                        GamePauseDialog(onRestartGame = {
                            navController.navigate(Game.route) {
                                launchSingleTop = true
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onBackPressed() {}
}