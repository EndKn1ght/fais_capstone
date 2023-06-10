package com.bangkit.bangkitcapstone.model.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class WorkoutEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "workoutName")
    val workoutName: String,

    @ColumnInfo(name = "workoutDesc")
    val workoutDesc: String,

    @ColumnInfo(name = "workoutCal")
    val workoutCal: String,

) : Parcelable