package com.example.nycschools.models.schools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchoolListItem(
    val dbn:String,
    val school_name:String,
    val phone_number:String,
    val school_email:String,
    val primary_address_line_1:String,
    val city:String,
    val state_code:String,
    val zip: String
):Parcelable