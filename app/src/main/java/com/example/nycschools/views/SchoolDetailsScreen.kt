package com.example.nycschools.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card

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
        Surface(modifier = Modifier.fillMaxSize(1f)) {
            Column {
                Header()
                schoolInfo(schoolScoresItem = schoolsViewModel.schoolDetails.value)
            }

        }
    }else {

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
    Column ( modifier = Modifier.fillMaxWidth(1f)){
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = " ${schoolScoresItem.school_name} ")
        }

        Spacer(modifier = Modifier
            .fillMaxHeight(0.02f)
            .fillMaxWidth(1f))

        Column {

            Text(text = annotatedStringBuilder("No Of TestTaker :",schoolScoresItem.num_of_sat_test_takers))
            Text(text = annotatedStringBuilder("Avg. Critical Reading : ",schoolScoresItem.sat_critical_reading_avg_score))
            Text(text = annotatedStringBuilder("Avg. Math :", schoolScoresItem.sat_math_avg_score))
            Text(text = annotatedStringBuilder("Avg. Writing : ",schoolScoresItem.sat_writing_avg_score))

        }
    }

}


