package com.bangkit.bangkitcapstone.model.data.local.room.food
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodHealthConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(value: List<String>?): String {
        return gson.toJson(value)
    }
}
