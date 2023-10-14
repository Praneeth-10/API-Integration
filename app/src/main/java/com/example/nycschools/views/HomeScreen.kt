package com.example.nycschools.views

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.parcelize.Parcelize

@Composable
fun HomeScreen(navController: NavHostController) {

    LaunchedEffect(Unit){

    }

    val schoolList = arrayListOf<SchoolItem>(
        SchoolItem("School 1","S","S"),
        SchoolItem("School 2","S","S"),
        SchoolItem("School 3","S","S")
    )

    Surface {
        Column {
            Text(text = "NYC Schools")
            Column {
                LazyColumn{
                    items(schoolList.size) {index ->
                        SchoolListItem(schoolList[index]){ item ->
                            navController.currentBackStackEntry?.savedStateHandle?.let {
                                it.set("SchoolItem",item)
                            }
                            navController.navigate("SchoolDetails")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolListItem(schoolItem: SchoolItem,onSchoolItemClick:(schoolItem:SchoolItem) -> Unit) {

    Column(modifier = Modifier.clickable {
        onSchoolItemClick.invoke(schoolItem)

    }) {
        Text(text = schoolItem.schoolName)
        Text(text = schoolItem.city)
        Text(text = schoolItem.state)
    }

}

@Parcelize
data class SchoolItem(val schoolName:String,val city:String,val state:String):Parcelable