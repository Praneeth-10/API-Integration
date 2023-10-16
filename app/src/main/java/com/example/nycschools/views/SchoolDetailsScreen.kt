package com.example.nycschools.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nycschools.models.schools.SchoolScoresItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel

@Composable
fun SchoolDetailsScreen(navHostController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()){


    val context = LocalContext.current
    val checkInternet = remember { mutableStateOf(checkInternetConnectivity(context)) }
    val retryTrigger = remember { mutableIntStateOf(0) }

    LaunchedEffect(retryTrigger.value){
        checkInternet.value = checkInternetConnectivity(context)
        if(checkInternet.value) {
            val dbn =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<String>(Utils.NavUtils.SCHOOL_DBN)
            schoolsViewModel.getSchoolDetails(dbn ?: "")
        }
    }
    if(checkInternet.value){
        if (schoolsViewModel.errorState.value != null) {
            Text(text = "An error occurred: ${schoolsViewModel.errorState.value?.message}")
        }
        else{
            Surface(modifier = Modifier.fillMaxSize(1f)) {
                Column {
                    Header()
                    SchoolInfo(schoolScoresItem = schoolsViewModel.schoolDetails.value)
                }
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
                Button(onClick = {retryTrigger.value++}) {
                    Text(text = "Retry")
                }
            }
        }
    }

}

@Composable
fun SchoolInfo(schoolScoresItem: SchoolScoresItem) {
    // Get the screen dimensions
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Customizing the screen width and height
    val paddingWidth = screenWidth * 0.07f
    val paddingHeight = screenHeight * 0.1f
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingWidth, paddingHeight)
            .background(Color(0xFF8BBCD6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {
            Text(
                text = schoolScoresItem.school_name,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)

            Column(modifier = Modifier.padding(top = 16.dp)) {
                TextWithLabel("No Of TestTaker :", schoolScoresItem.num_of_sat_test_takers)
                Spacer(Modifier.height(8.dp))
                TextWithLabel("Avg. Critical Reading :", schoolScoresItem.sat_critical_reading_avg_score)
                Spacer(Modifier.height(8.dp))
                TextWithLabel("Avg. Math :", schoolScoresItem.sat_math_avg_score)
                Spacer(Modifier.height(8.dp))
                TextWithLabel("Avg. Writing :", schoolScoresItem.sat_writing_avg_score)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewSchoolInfo() {
//    // Create a mock SchoolScoresItem
//    val mockSchoolScoresItem = SchoolScoresItem(
//        school_name = "Mock School",
//        num_of_sat_test_takers = "100",
//        sat_critical_reading_avg_score = "600",
//        sat_math_avg_score = "650",
//        sat_writing_avg_score = "700"
//    )
//
//    // Use your theme if you have one
//
//        SchoolInfo(mockSchoolScoresItem)
//
//}


@Composable
fun TextWithLabel(label: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            color = if (text == "No Data Found") Color.Red else MaterialTheme.colorScheme.secondary
        )
    }
}