package com.example.nycschools.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nycschools.models.schools.SchoolScoresItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel

@Composable
fun SchoolDetailsScreen(navHostController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()){

    val context = LocalContext.current
    val checkInternet = checkInternetConnectivity(context)
    if(checkInternet){
        LaunchedEffect(Unit){
            val dbn = navHostController.previousBackStackEntry?.savedStateHandle?.get<String>(Utils.NavUtils.SCHOOL_DBN)
            schoolsViewModel.getSchoolDetails(dbn?:"")
        }

        Surface {

            LazyColumn{
                items(schoolsViewModel.schoolScores.size){
                    schoolInfo(schoolsViewModel.schoolScores[it])
                }
            }
            Text(text = "This is school Detail screen !!")


        }
    }
    else{
        Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Internet connection",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { /*TODO: Retry action*/ }) {
                    Text(text = "Retry")
                }
            }
        }

    }


}

@Composable
fun schoolInfo(schoolScoresItem: SchoolScoresItem) {
    Column {
        Text(text = "${schoolScoresItem.school_name}")
    }
}