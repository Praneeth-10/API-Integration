package com.example.nycschools.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nycschools.models.SchoolListItem
import com.example.nycschools.models.SchoolScoresItem
import com.example.nycschools.models.apiservice.SchoolService

class SchoolRepository(private val schoolsService: SchoolService) {
    private val mutableSchools = MutableLiveData<SchoolListItem>()
    private val mutableSchoolSatScores = MutableLiveData<SchoolScoresItem>()

    val liveSchools : LiveData<SchoolListItem>
        get() =mutableSchools

    val liveScores : LiveData<SchoolScoresItem>
        get() = mutableSchoolSatScores

    suspend fun getSchools() {
        val items = schoolsService.getSchools()
        if(items.body() != null){
            mutableSchools.postValue(items.body())
        }

        val itemScores = schoolsService.getSatScores()
        if(items.body() != null){
            mutableSchoolSatScores.postValue(itemScores.body())
        }
    }
}