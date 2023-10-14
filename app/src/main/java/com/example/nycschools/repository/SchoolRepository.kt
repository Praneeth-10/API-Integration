package com.example.nycschools.repository

import com.example.nycschools.models.apiservice.SchoolService
import com.example.nycschools.models.schools.SchoolList
import com.example.nycschools.models.schools.SchoolScores
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class SchoolRepository @Inject constructor(retrofit:Retrofit) {

    private val schoolsService: SchoolService by lazy {
        retrofit.create(SchoolService::class.java)
    }



    suspend fun getSchools() = flow<SchoolList> {
        val response = schoolsService.getSchools()
        if(response.isSuccessful){
            //change it to null check
            emit(response.body()!!)
        }
    }

    suspend fun getSATScores(dbn:String) = flow<SchoolScores>{
        val response = schoolsService.getSatScores(dbn)
        if(response.isSuccessful){
            emit(response.body()!!)
        }
    }
}