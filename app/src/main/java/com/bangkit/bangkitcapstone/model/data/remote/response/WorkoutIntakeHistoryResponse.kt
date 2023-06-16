package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class WorkoutIntakeHistoryResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("workout_date")
	val workoutDate: String? = null,

	@field:SerializedName("duration_minutes")
	val durationMinutes: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("calories_burned")
	val caloriesBurned: String? = null,

	@field:SerializedName("history_id")
	val historyId: Int? = null,

	@field:SerializedName("workout_id")
	val workoutId: String? = null
)
