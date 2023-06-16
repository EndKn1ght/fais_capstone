package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class WorkoutHistory(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("workout_date")
	val workoutDate: String? = null,

	@field:SerializedName("duration_minutes")
	val durationMinutes: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("calories_burned")
	val caloriesBurned: Int? = null,

	@field:SerializedName("history_id")
	val historyId: Int? = null,

	@field:SerializedName("workout_id")
	val workoutId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
