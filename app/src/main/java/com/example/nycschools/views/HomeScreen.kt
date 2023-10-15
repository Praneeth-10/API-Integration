package com.example.nycschools.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.nycschools.models.schools.SchoolListItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel
import kotlinx.parcelize.Parcelize

@Composable
fun HomeScreen(navController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val checkInternet = checkInternetConnectivity(context)
    if(checkInternet)
    {
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

inline fun checkInternetConnectivity(context: Context): Boolean {
    // register activity with the connectivity manager service
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // Returns a Network object corresponding to the currently active default data network.
    val network = connectivityManager.activeNetwork ?: return false
    // Representation of the capabilities of an active network.
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        // Indicates this network uses a Wi-Fi transport, or WiFi has network connectivity
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        // Indicates this network uses a Cellular transport. or Cellular has network connectivity
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        // else return false
        else -> false
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