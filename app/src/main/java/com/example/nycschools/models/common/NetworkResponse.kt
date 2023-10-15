package com.example.nycschools.models.common

sealed class NetworkResponse

class onSuccessResponse<T>(val data:T):NetworkResponse()
class onFailure(val message:String):NetworkResponse()
