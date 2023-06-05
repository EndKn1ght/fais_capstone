package com.bangkit.bangkitcapstone.model.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class FoodEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "foodName")
    val foodName: String,

    @ColumnInfo(name = "foodRecipe")
    val foodRecipe: String,

    @ColumnInfo(name = "foodDesc")
    val foodDesc: String,

    @ColumnInfo(name = "foodCal")
    val foodCal: String,
) : Parcelable