package com.android.favoritemakes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.android.favoritemakes.data.source.remote.SyncManager
import com.android.favoritemakes.home.FavoriteMakesScreen
import com.android.favoritemakes.ui.NavHost
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMakesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FavoriteMakes()
        }
    }
}

@Composable
fun FavoriteMakes() {
    FavoriteMakesTheme {
        val navController = rememberNavController()
        NavHost(
            Modifier.systemBarsPadding(),
            navController = navController,
        )
    }
}