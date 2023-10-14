package com.example.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschools.ui.theme.NYCSchoolsTheme
import com.example.nycschools.views.HomeScreen
import com.example.nycschools.views.SchoolDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NYCNavigator()
                }
            }
        }
    }
}

@Composable
fun NYCNavigator(navController:NavHostController = rememberNavController()){

    NavHost(navController = navController, startDestination = "Home"){

        composable("Home"){
            HomeScreen(navController)
        }

        composable("SchoolDetails"){
            SchoolDetailsScreen(navHostController = navController)
        }

    }

}
