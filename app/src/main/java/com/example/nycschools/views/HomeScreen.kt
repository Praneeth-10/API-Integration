package com.example.nycschools.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Parcelable
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nycschools.models.schools.SchoolListItem
import com.example.nycschools.utils.Utils
import com.example.nycschools.viewmodel.SchoolsViewModel

@Composable
fun HomeScreen(navController: NavHostController,schoolsViewModel: SchoolsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val checkInternet = checkInternetConnectivity(context)
    if(checkInternet) {
        LaunchedEffect(Unit) {
            schoolsViewModel.getNYCSchoolsList()

        }
    }

    Surface {
        Column {

           Header()
            Spacer(modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(1f))
            Column(modifier = Modifier.fillMaxHeight(1f)) {
                LazyColumn{
                    items(schoolsViewModel.schoolList.size) {index ->

                        SchoolListItem2(schoolsViewModel.schoolList[index],{
                            //To do a phone call

                            navController.context.startActivity(Intent.createChooser(Intent(Intent.ACTION_CALL,Uri.parse("tel:+1$it")), "NYC Schools"))
                        }){ item ->
                            navController.currentBackStackEntry?.savedStateHandle?.let {
                                it.set(Utils.NavUtils.SCHOOL_DBN,item.dbn)
                            }
                            navController.navigate(Utils.ScreenUtils.SCHOOL_INFO)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Spacer(modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth(1f))
            }
            //IndeterminateCircularIndicator(schoolsViewModel.loadingIndicatorState)
        }
    }
}

@Composable
fun SchoolListItem(schoolItem: SchoolListItem, onPhoneNumberClick:(phoneNumber:String) ->Unit,
                   onSchoolItemClick:(schoolItem:SchoolListItem) -> Unit) {

    Card(modifier = Modifier
        .fillMaxWidth(1f)
        .padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(1f)
            .clickable {
                onSchoolItemClick.invoke(schoolItem)
            }
            .padding(horizontal = 4.dp)) {
            Text(text = schoolItem.school_name, fontWeight = FontWeight.Bold,

                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(1f)
                    .align(alignment = Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
                )
            Text(text = " ${schoolItem.city}")
            Text(text = "${schoolItem.city}, ${schoolItem.state_code}")
            Text(text = " ${schoolItem.zip} ")
            Text(text = " ${schoolItem.school_email} ")
            Text(text = " ${schoolItem.phone_number} ", modifier = Modifier.clickable {
                onPhoneNumberClick.invoke(schoolItem.phone_number)

            })

            //Text(text = schoolItem.state)
        }
    }



}


@Composable
fun SchoolListItem2(schoolItem: SchoolListItem, onPhoneNumberClick:(phoneNumber:String) ->Unit,
                   onSchoolItemClick:(schoolItem:SchoolListItem) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .clickable {
                    onSchoolItemClick.invoke(schoolItem)
                }
                .padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color.Blue
            )
            Text(
                text = schoolItem.school_name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(1f)
                    .align(alignment = Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color.Green
            )
            Text(
                text = " ${schoolItem.school_email}",
                color = Color.Black,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                tint = Color.Red
            )
            Text(
                text = " ${schoolItem.phone_number}",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    onPhoneNumberClick.invoke(schoolItem.phone_number)
                }
            )
        }
    }

}




@Composable
fun IndeterminateCircularIndicator(loadingIndicatorState: MutableState<Boolean>) {

    if (!loadingIndicatorState.value) return

    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
    )
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

