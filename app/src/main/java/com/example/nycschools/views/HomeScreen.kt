package com.example.nycschools.views

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nycschools.models.schools.SchoolListItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel
import kotlinx.parcelize.Parcelize

@Composable
fun HomeScreen(navController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()) {

    LaunchedEffect(Unit){
        schoolsViewModel.getNYCSchoolsList()

    }

    Surface {
        Column {

           Header()


            Column {
                LazyColumn{
                    items(schoolsViewModel.schoolList.size) {index ->
                        SchoolListItem(schoolsViewModel.schoolList[index]){ item ->
                            navController.currentBackStackEntry?.savedStateHandle?.let {
                                it.set(Utils.NavUtils.SCHOOL_DBN,item.dbn)
                            }
                            navController.navigate(Utils.ScreenUtils.SCHOOL_INFO)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolListItem(schoolItem: SchoolListItem, onSchoolItemClick:(schoolItem:SchoolListItem) -> Unit) {

    Column(modifier = Modifier.clickable {
        onSchoolItemClick.invoke(schoolItem)

    }) {
        Text(text = schoolItem.school_name)
        Text(text = schoolItem.zip)
        //Text(text = schoolItem.state)
    }

}
@Composable
fun Header(){
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "NYC Schools")
    }
}