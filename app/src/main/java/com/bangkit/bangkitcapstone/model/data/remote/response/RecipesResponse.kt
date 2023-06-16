package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecipesResponse(

    @field:SerializedName("hits")
    val hits: List<HitsItem?>? = null,

    )

data class Recipe(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("dietLabels")
    val dietLabels: List<String?>? = null,

    @field:SerializedName("healthLabels")
    val healthLabels: List<String?>? = null,

    @field:SerializedName("mealType")
    val mealType: List<String?>? = null,

    @field:SerializedName("externalId")
    val externalId: String? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("calories")
    val calories: Float? = null,

    @field:SerializedName("ingredientLines")
    val ingredientLines: List<String?>? = null,

    @field:SerializedName("totalNutrients")
    val totalNutrients: NutrientsInfo? = null
)

data class HitsItem(

    @field:SerializedName("recipe")
    val recipe: Recipe? = null
)
