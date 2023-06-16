package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class WorkoutResponse(

    @field:SerializedName("workout_name")
    val workoutName: String? = null,

    @field:SerializedName("level")
    val level: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("equipment")
    val equipment: String? = null,

    @field:SerializedName("workout_id")
    val workoutId: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("body_part")
    val bodyPart: String? = null,
)
