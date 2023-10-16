package com.example.nycschools.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.models.common.onSuccessResponse
import com.example.nycschools.models.schools.SchoolList
import com.example.nycschools.models.schools.SchoolListItem
import com.example.nycschools.models.schools.SchoolScores
import com.example.nycschools.models.schools.SchoolScoresItem
import com.example.nycschools.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(private val repo: SchoolRepository) : ViewModel(){

    val schoolList = mutableStateListOf<SchoolListItem>()

    val schoolDetails = mutableStateOf(SchoolScoresItem())

    val loadingIndicatorState = mutableStateOf(true)

    val errorState = mutableStateOf<Exception?>(null)



    fun getNYCSchoolsList(){
        viewModelScope.launch(Dispatchers.IO) {
           repo.getSchools().collect{
               when(it){
                   is onSuccessResponse<*> -> {
                       schoolList.addAll(
                           ((it as onSuccessResponse<*>).data) as SchoolList
                       )
                       loadingIndicatorState.value = false
                   }
                   else -> {
                       // show failure to user
                       throw Exception("No data found")

                   }
               }

           }
        }
    }

    fun getSchoolDetails(dbn:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getSATScores(dbn).collect{
                    loadingIndicatorState.value = false
                    when(it){
                        is onSuccessResponse<*> -> {
                            val scoresList = (((it as onSuccessResponse<*>).data) as SchoolScores)
                            //checking for the size
                            if(scoresList.size>=1){
                                schoolDetails.value = scoresList[0]
                            }else{
                                // throw no data found
                                schoolDetails.value = SchoolScoresItem(dbn,"No Data Found","No Data Found","No Data Found","No Data Found","No Data Found")
                            }
                        }
                        else -> {
                            // throw exception
                            throw Exception("An error occurred while fetching school details")
                        }
                    }
                }
            } catch (e: Exception) {
                errorState.value = e
            }
        }
    }
}