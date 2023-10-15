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

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nycschools.models.schools.SchoolScoresItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel

@Composable
fun SchoolDetailsScreen(navHostController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()){

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


