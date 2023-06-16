package com.bangkit.bangkitcapstone.model.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutrientsInfo(

    @field:SerializedName("VITB6A")
    val vitB6: NutrientsUnit,

    @field:SerializedName("VITC")
    val vitC: NutrientsUnit,

    @field:SerializedName("CHOCDF.net")
    val carbs: NutrientsUnit,

    @field:SerializedName("FATRN")
    val fat: NutrientsUnit,

    @field:SerializedName("CA")
    val calcium: NutrientsUnit,

    @field:SerializedName("WATER")
    val water: NutrientsUnit,

    @field:SerializedName("SUGAR")
    val sugar: NutrientsUnit,

    @field:SerializedName("PROCNT")
    val prot: NutrientsUnit,

    @field:SerializedName("NA")
    val sodium: NutrientsUnit,

    @field:SerializedName("ZN")
    val zinc: NutrientsUnit,

    @field:SerializedName("VITA_RAE")
    val vitA: NutrientsUnit,

    @field:SerializedName("FE")
    val iron: NutrientsUnit
) : Parcelable

@Parcelize
data class NutrientsUnit(

    @field:SerializedName("unit")
    val unit: String,

    @field:SerializedName("quantity")
    val quantity: Float,

    @field:SerializedName("label")
    val label: String
) : Parcelable

