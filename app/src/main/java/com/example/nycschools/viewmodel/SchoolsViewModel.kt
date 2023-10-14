package com.example.nycschools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.models.SchoolListItem
import com.example.nycschools.models.SchoolScoresItem
import com.example.nycschools.models.repository.SchoolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolsViewModel(private val repo: SchoolRepository) : ViewModel(){
    init {
        viewModelScope.launch (Dispatchers.IO){
            repo.getSchools()
        }
    }

    val fetchedSchools : LiveData<SchoolListItem>
        get() = repo.liveSchools

    val fetchScores : LiveData<SchoolScoresItem>
        get() = repo.liveScores
}