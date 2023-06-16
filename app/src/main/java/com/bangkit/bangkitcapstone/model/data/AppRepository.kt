package com.bangkit.bangkitcapstone.model.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit.bangkitcapstone.helper.Helper
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity
import com.bangkit.bangkitcapstone.model.data.local.room.daily.DailyDao
import com.bangkit.bangkitcapstone.model.data.local.room.food.FoodDao
import com.bangkit.bangkitcapstone.model.data.local.room.workout.WorkoutDao
import com.bangkit.bangkitcapstone.model.data.local.store.DataStoreManager
import com.bangkit.bangkitcapstone.model.data.remote.response.*
import com.bangkit.bangkitcapstone.model.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppRepository(
    private val dataStore: DataStoreManager,
    private val apiService: ApiService,
    private val dailyDao: DailyDao,
    private val workoutDao: WorkoutDao,
    private val foodDao: FoodDao
) {

    fun loginAuth(username: String, password: String): LiveData<UiState<LoginResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.loginUser(username, password)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun registerAuth(
        username: String,
        password: String,
        email: String,
        date: String,
        height: Int,
        weight: Int
    ): LiveData<UiState<RegisterResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.registerUser(username, password, email, date, height, weight)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getAllWorkoutList(): LiveData<UiState<List<WorkoutEntity>>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.getAllWorksout()
            val data = response.map {
                val cal = when (it.level) {
                    "Beginner" -> 5
                    "Intermediate" -> 10
                    "Expert" -> 20
                    else -> 25
                }
                WorkoutEntity(
                    it.workoutId.toString().toInt() + 1,
                    it.workoutName.toString(),
                    it.description.toString(),
                    cal.toString(),
                    it.level.toString(),
                    it.equipment.toString(),
                    it.type.toString(),
                    it.bodyPart.toString()
                )
            }
            data.let {
                workoutDao.insertWorkout(it)
            }
            val dataList = workoutDao.getAllDailyWorkout()
            emit(UiState.Success(dataList))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun insertIntake(
        name: String,
        intake: String,
        type: IntakeType,
        duration: String? = null
    ): LiveData<UiState<Float>> =
        liveData {
            emit(UiState.Loading)
            try {
                val currentDate = Helper.getToday()
                dailyDao.insertDaily(
                    CaloriesDailyEntity(
                        name = name,
                        intake = intake,
                        duration = duration,
                        dateIntake = currentDate,
                        intakeType = type
                    )
                )
                emit(UiState.Success(intake.toFloat()))
            } catch (e: Exception) {
                emit(UiState.Error(e.toString()))
            }
        }

    fun getIntakeData(intakeType: IntakeType, date: String): LiveData<UiState<List<String>>> =
        liveData {
            emit(UiState.Loading)
            try {
                val data = dailyDao.getIntakeData(intakeType, date)
                emit(UiState.Success(data))
            } catch (e: Exception) {
                emit(UiState.Error(e.toString()))
            }
        }

    fun getUserDetail(token: String): LiveData<UiState<RegisterResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.getUserDetails(token)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun saveUserData(data: Map<String, String>): LiveData<UiState<Boolean>> = liveData {
        emit(UiState.Loading)
        try {
            dataStore.saveMapData(data)
            emit(UiState.Success(true))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getAllDaily(): LiveData<UiState<List<CaloriesDailyEntity>>> = liveData {
        emit(UiState.Loading)
        try {
            val data = dailyDao.getAllDaily()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getAllDailyWorkout(): LiveData<UiState<List<CaloriesDailyEntity>>> = liveData {
        emit(UiState.Loading)
        try {
            val data = dailyDao.getAllDailyWorkout()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun uploadCaloriesIntake(
        token: String,
        date: String,
        desc: String,
        cal: Float
    ): LiveData<UiState<CaloriesIntakeHistoryResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.uploadCalorieIntake(token, token, date, desc, cal)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getRecipesList(): LiveData<UiState<List<FoodEntity>>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.getRecipes()
            val result = response.hits?.map {
                it?.recipe
            }
            val recipes = result?.map {
                val vitB6 = it?.totalNutrients?.vitB6?.quantity
                val vitC = it?.totalNutrients?.vitC?.quantity
                val carbs = it?.totalNutrients?.carbs?.quantity
                val fat = it?.totalNutrients?.fat?.quantity
                val calcium = it?.totalNutrients?.calcium?.quantity
                val water = it?.totalNutrients?.water?.quantity
                val sugar = it?.totalNutrients?.sugar?.quantity
                val prot = it?.totalNutrients?.prot?.quantity
                val sodium = it?.totalNutrients?.sodium?.quantity
                val zinc = it?.totalNutrients?.zinc?.quantity
                val vitA = it?.totalNutrients?.vitA?.quantity
                val iron = it?.totalNutrients?.iron?.quantity
                @Suppress("UNCHECKED_CAST")
                FoodEntity(
                    foodName = it?.label.toString(),
                    foodImage = it?.image.toString(),
                    foodHealth = it?.healthLabels as List<String>,
                    foodCal = it.calories.toString(),
                    foodRecipe = it.ingredientLines as List<String>,
                    vitB6 = vitB6?: 0f,
                    vitC = vitC?: 0f,
                    carbs = carbs?: 0f,
                    fat = fat?: 0f,
                    calcium = calcium?: 0f,
                    water = water?: 0f,
                    sugar = sugar?: 0f,
                    prot = prot?: 0f,
                    sodium = sodium?: 0f,
                    zinc = zinc?: 0f,
                    vitA = vitA?: 0f,
                    iron = iron?: 0f,
                )
            }
            recipes?.let { foodDao.insertFood(it) }
            Log.e("Haedh", "$recipes")
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
        val localData : LiveData<UiState<List<FoodEntity>>> = foodDao.getAllFood().map {
            UiState.Success(it)
        }
        emitSource(localData)
    }

    fun uploadWorkoutIntake(
        token: String,
        id: String,
        date: String,
        duration: Float,
        cal: Float,
    ): LiveData<UiState<WorkoutIntakeHistoryResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.uploadWorkoutIntake(token, id, date, duration, cal)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun deleteAllHistory(): LiveData<UiState<String>> = liveData {
        emit(UiState.Loading)
        try {
            dailyDao.deleteAll()
            emit(UiState.Success("Success"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getAllWorkout(): LiveData<UiState<List<WorkoutEntity>>> = liveData {
        emit(UiState.Loading)
        try {
           val data = workoutDao.getAllDailyWorkout()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun downloadHistoryCalories(token: String): LiveData<UiState<List<CaloriesDailyEntity>>> =
        liveData {
            emit(UiState.Loading)
            try {
                val response = apiService.getHistoryCalories(token)
                val data = response.map {
                    CaloriesDailyEntity(
                        name = it.mealDescription.toString(),
                        intake = it.caloriesConsumed.toString(),
                        intakeType = IntakeType.FOOD,
                        dateIntake = it.intakeDate.toString()
                    )
                }
                emit(UiState.Success(data))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
            }
        }

    fun downloadHistory(token: String): LiveData<UiState<List<CaloriesDailyEntity>>> =
        liveData {
            emit(UiState.Loading)
            try {
                val response1 = apiService.getHistoryWorkout(token)
                val response2 = apiService.getHistoryCalories(token)
                val namesList = apiService.getAllWorksout()
                val data1 = response1.map { data ->
                    val matchingNames: List<String> = namesList
                        .filter { it1 -> it1.workoutId == data.workoutId }
                        .mapNotNull { response -> response.workoutName }

                    CaloriesDailyEntity(
                        name = matchingNames.firstOrNull() ?: "",
                        intake = data.caloriesBurned.toString(),
                        intakeType = IntakeType.WORKOUT,
                        duration = (data.durationMinutes?.div(60)).toString(),
                        dateIntake = Helper.convertDateFormat(data.workoutDate.toString())
                    )
                }

                val data2 = response2.map {
                    CaloriesDailyEntity(
                        name = it.mealDescription.toString(),
                        intake = it.caloriesConsumed.toString(),
                        intakeType = if (it.mealDescription!!.contains("Water")) IntakeType.WATER else IntakeType.FOOD,
                        dateIntake = Helper.convertDateFormat(it.intakeDate.toString())
                    )
                }
                val realData = data1 + data2
                Log.e("Hasjdfhasjk", "$realData")
                emit(UiState.Success(realData))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
            }
        }

    fun inserListDaily(list: List<CaloriesDailyEntity>): LiveData<UiState<String>> = liveData {
        emit(UiState.Loading)
        try {
            list.forEach {
                dailyDao.insertDaily(it)
            }
            emit(UiState.Success("Success"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getUserData(key: String): LiveData<String> {
        return dataStore.getData(key).asLiveData()
    }

    fun getAllUserData() = dataStore.getAllData()

    fun getThemeSettings(): LiveData<Boolean> {
        return dataStore.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(state: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            dataStore.saveThemeSetting(state)
        }
    }

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            dataStore: DataStoreManager,
            apiService: ApiService,
            dailyDao: DailyDao,
            workoutDao: WorkoutDao,
            foodDao: FoodDao
        ): AppRepository = instance ?: synchronized(this) {
            instance ?: AppRepository(dataStore, apiService, dailyDao, workoutDao, foodDao)
        }.also { instance = it }
    }
}