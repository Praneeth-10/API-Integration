package com.example.nycschools.models.schools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchoolListItem(
    val dbn:String,
    val zip: String,
    val school_name:String
):Parcelable