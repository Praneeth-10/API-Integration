package com.example.nycschools.models.apiservice

import com.example.nycschools.models.SchoolListItem
import com.example.nycschools.models.SchoolScoresItem
import retrofit2.Response
import retrofit2.http.GET

interface SchoolService {

    //Declaring the function for consuming the Schools API data and returning it as Response
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchools() : Response<SchoolListItem>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSatScores() : Response<SchoolScoresItem>
}