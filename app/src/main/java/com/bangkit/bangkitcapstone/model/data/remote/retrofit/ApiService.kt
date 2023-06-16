package com.bangkit.bangkitcapstone.model.data.remote.retrofit

import com.bangkit.bangkitcapstone.model.data.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("/workouts")
    suspend fun getAllWorksout(): List<WorkoutResponse>

    @FormUrlEncoded
    @POST("/register")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("date_of_birth") date: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/login")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("/user/{token}")
    suspend fun getUserDetails(
        @Path("token") token: String
    ): RegisterResponse

    @GET("recipes")
    suspend fun getRecipes(): RecipesResponse

    @FormUrlEncoded
    @POST("/users/calorieIntake/{token}")
    suspend fun uploadCalorieIntake(
        @Path("token") token: String,
        @Field("token") token2: String,
        @Field("intake_date") date: String,
        @Field("meal_description") desc: String,
        @Field("calories_consumed") cal: Float,
    ): CaloriesIntakeHistoryResponse

    @FormUrlEncoded
    @POST("/users/workoutHistory/{token}")
    suspend fun uploadWorkoutIntake(
        @Path("token") token: String,
        @Field("workout_id") workoutId: String,
        @Field("workout_date") date: String,
        @Field("duration_minutes") duration: Float,
        @Field("calories_burned") cal: Float,
    ): WorkoutIntakeHistoryResponse

    @GET("/users/calorieIntakes/{token}")
    suspend fun getHistoryCalories(@Path("token") token: String): List<CaloriesHistory>

    @GET("/users/workoutHistory/{token}")
    suspend fun getHistoryWorkout(@Path("token") token: String): List<WorkoutHistory>
}