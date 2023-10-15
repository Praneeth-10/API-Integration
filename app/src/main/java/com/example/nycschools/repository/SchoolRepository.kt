package com.example.nycschools.repository

import com.example.nycschools.models.apiservice.SchoolService
import com.example.nycschools.models.common.NetworkResponse
import com.example.nycschools.models.common.onFailure
import com.example.nycschools.models.common.onSuccessResponse
import com.example.nycschools.models.schools.SchoolList
import com.example.nycschools.models.schools.SchoolScores
import com.example.nycschools.utils.Utils
import kotlinx.coroutines.flow.flow
import okhttp3.internal.Util
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class SchoolRepository @Inject constructor(retrofit:Retrofit) {

    private val schoolsService: SchoolService by lazy {
        retrofit.create(SchoolService::class.java)
    }



    suspend fun getSchools() = flow<NetworkResponse> {
        try {
            val response = schoolsService.getSchools()
            if(response.isSuccessful){
                //change it to null check
                emit(onSuccessResponse(response.body()))
            }

        }catch (e:Exception){
            emit(onFailure(e.localizedMessage?: Utils.NetworkUtils.UNKOWN_EXCEPTION))
        }

    }

    suspend fun getSATScores(dbn:String) = flow<NetworkResponse>{
        try {
            val response = schoolsService.getSatScores(dbn)
            if(response.isSuccessful){
                emit(onSuccessResponse(response.body()))
            }
        }catch (e:Exception){
            emit(onFailure(e.localizedMessage?:Utils.NetworkUtils.UNKOWN_EXCEPTION))
        }

    }
}