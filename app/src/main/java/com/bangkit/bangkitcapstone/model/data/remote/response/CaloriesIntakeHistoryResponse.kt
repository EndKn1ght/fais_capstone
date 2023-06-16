package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class CaloriesIntakeHistoryResponse(

	@field:SerializedName("intake_id")
	val intakeId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("calories_consumed")
	val caloriesConsumed: String? = null,

	@field:SerializedName("meal_description")
	val mealDescription: String? = null,

	@field:SerializedName("intake_date")
	val intakeDate: String? = null
)
