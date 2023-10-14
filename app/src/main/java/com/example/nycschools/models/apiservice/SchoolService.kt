package com.example.nycschools.models.apiservice

import com.example.nycschools.models.schools.SchoolList
import com.example.nycschools.models.schools.SchoolScores
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolService {

    //Declaring the function for consuming the Schools API data and returning it as Response
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchools() : Response<SchoolList>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSatScores(@Query("dbn") dbn:String) : Response<SchoolScores>
}