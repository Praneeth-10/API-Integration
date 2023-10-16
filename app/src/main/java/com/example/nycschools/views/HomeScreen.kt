package com.example.nycschools.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
    val checkInternet = remember { mutableStateOf(checkInternetConnectivity(context)) }
    val retryTrigger = remember { mutableIntStateOf(0) }
    LaunchedEffect(retryTrigger.intValue) {
        checkInternet.value = checkInternetConnectivity(context)
        if(checkInternet.value) {
            schoolsViewModel.getNYCSchoolsList()
        }
    }
    if(checkInternet.value) {
        Surface {
            Column {
                Header()
                Spacer(modifier = Modifier.height(4.dp).fillMaxWidth(1f))
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    LazyColumn{
                        items(schoolsViewModel.schoolList.size) {index ->
                            SchoolListItem(schoolsViewModel.schoolList[index],{
                                //To do a phone call
                                navController.context.startActivity(Intent.createChooser(Intent(Intent.ACTION_CALL,Uri.parse("tel:+1$it")), "NYC Schools"))
                            }){ item ->
                                navController.currentBackStackEntry?.savedStateHandle?.set(Utils.NavUtils.SCHOOL_DBN,item.dbn)
                                navController.navigate(Utils.ScreenUtils.SCHOOL_INFO)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp).fillMaxWidth(1f))
                }
                IndeterminateCircularIndicator(schoolsViewModel.loadingIndicatorState)
            }
        }
    }else{
        Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp),
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
                Button(onClick = { retryTrigger.intValue++ }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}
@Composable
fun SchoolListItem(schoolItem: SchoolListItem, onPhoneNumberClick:(phoneNumber:String) ->Unit,
                   onSchoolItemClick:(schoolItem:SchoolListItem) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val paddingWidth = screenWidth * 0.05f

    Card(
        modifier = Modifier.fillMaxWidth(1f).padding(horizontal = paddingWidth, vertical = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(1f).clickable {
                    onSchoolItemClick.invoke(schoolItem)
                }
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.Blue
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = if(schoolItem.school_name.isEmpty()) "No School Found" else " ${schoolItem.school_name}" ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = if(schoolItem.school_name.isEmpty()) Color.Red else Color.Black,
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(1f),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.Green
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text =  if(schoolItem.school_email.isNullOrEmpty()) "No Email Found" else " ${schoolItem.school_email}",
                    color = if(schoolItem.school_email.isNullOrEmpty()) Color.Red else Color.Black,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = Color.Red
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = if(schoolItem.phone_number.isNullOrEmpty()) "No Phone Found" else " ${schoolItem.phone_number}",
                    color = Color.Blue,
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = if(schoolItem.phone_number.isNullOrEmpty())Modifier
                        .border(2.dp, Color.Red, RoundedCornerShape(5.dp))
                        .padding(horizontal = 3.dp, vertical = 2.dp)
                        .background(Color(0xFFFFFFFF))
                    else Modifier
                        .clickable { onPhoneNumberClick.invoke(schoolItem.phone_number) }
                        .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                        .padding(horizontal = 3.dp, vertical = 2.dp)
                        .background(Color(0xFFB2C6D5))
                )
            }
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
fun checkInternetConnectivity(context: Context): Boolean {
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