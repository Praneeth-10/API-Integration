package com.example.nycschools.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.models.schools.SchoolListItem
import com.example.nycschools.models.schools.SchoolScores
import com.example.nycschools.models.schools.SchoolScoresItem
import com.example.nycschools.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(private val repo: SchoolRepository) : ViewModel(){

    val schoolList = mutableStateListOf<SchoolListItem>()

    val schoolScores = mutableStateListOf<SchoolScoresItem>()



    fun getNYCSchoolsList(){
        viewModelScope.launch {
           repo.getSchools().collect{
               schoolList.addAll(it)
           }
        }
    }

    fun getSchoolDetails(dbn:String){
        viewModelScope.launch {
            repo.getSATScores(dbn).collect{
                schoolScores.addAll(it)
            }
        }
    }
}