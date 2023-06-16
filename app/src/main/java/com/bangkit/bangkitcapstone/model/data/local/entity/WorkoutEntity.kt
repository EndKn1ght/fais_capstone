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

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "desc")
    val desc: String,

    @ColumnInfo(name = "cal")
    val cal: String,

    @ColumnInfo(name = "level")
    val level: String,

    @ColumnInfo(name = "equipment")
    val equipment: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "bodyPart")
    val bodyPart: String,

    ) : Parcelable