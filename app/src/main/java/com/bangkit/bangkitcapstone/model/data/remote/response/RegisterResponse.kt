package com.bangkit.bangkitcapstone.model.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String? = null,

    @field:SerializedName("weight")
    val weight: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("height")
    val height: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
