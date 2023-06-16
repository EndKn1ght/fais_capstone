package com.bangkit.bangkitcapstone.model.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.bangkitcapstone.model.data.remote.response.NutrientsInfo
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class FoodEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "foodImage")
    val foodImage: String,

    @ColumnInfo(name = "foodName")
    val foodName: String,

    @ColumnInfo(name = "foodhealth")
    val foodHealth: List<String>,

    @ColumnInfo(name = "foodCal")
    val foodCal: String,

    @ColumnInfo(name = "foodRecipe")
    val foodRecipe: List<String>,

    @ColumnInfo(name = "vitB6")
    val vitB6: Float,

    @ColumnInfo(name = "vitC")
    val vitC: Float,

    @ColumnInfo(name = "carbs")
    val carbs: Float,

    @ColumnInfo(name = "fat")
    val fat: Float,

    @ColumnInfo(name = "calcium")
    val calcium: Float,

    @ColumnInfo(name = "water")
    val water: Float,

    @ColumnInfo(name = "sugar")
    val sugar: Float,

    @ColumnInfo(name = "prot")
    val prot: Float,

    @ColumnInfo(name = "sodium")
    val sodium: Float,

    @ColumnInfo(name = "zinc")
    val zinc: Float,

    @ColumnInfo(name = "vitA")
    val vitA: Float,

    @ColumnInfo(name = "iron")
    val iron: Float
) : Parcelable