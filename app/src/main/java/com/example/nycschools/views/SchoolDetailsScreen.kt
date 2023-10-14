package com.example.nycschools.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    
    Surface {

        LazyColumn{
            items(schoolsViewModel.schoolScores.size){
                schoolInfo(schoolsViewModel.schoolScores[it])
            }
        }
        Text(text = "This is school Detail screen !!")


    }

}

@Composable
fun schoolInfo(schoolScoresItem: SchoolScoresItem) {
    Column {
        Text(text = "${schoolScoresItem.school_name}")
    }
}