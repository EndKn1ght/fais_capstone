package com.bangkit.bangkitcapstone.helper

import android.content.Context
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun getToday(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
    }

    fun roundFloat(number: Float, decimalPlaces: Int): Float {
        val bd = BigDecimal(number.toString())
        val rounded = bd.setScale(decimalPlaces, RoundingMode.HALF_UP)
        return rounded.toFloat()
    }

    fun convertTimeStringToSeconds(timeString: String): Int {
        val format = SimpleDateFormat("mm:ss", Locale.getDefault())
        val date = format.parse(timeString)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        return calendar.get(Calendar.SECOND)
    }

    fun formatListWithNumbers(list: List<String>): String {
        val stringBuilder = StringBuilder()

        for ((index, item) in list.withIndex()) {
            stringBuilder.append("- $item\n")
        }

        return stringBuilder.toString()
    }

    fun convertDateFormat(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

    val KEYPOINT_DICT = mapOf(
        "nose" to Pair(0, 1),
        "left_eye" to Pair(2, 3),
        "right_eye" to Pair(4, 5),
        "left_ear" to Pair(6, 7),
        "right_ear" to Pair(8, 9),
        "left_shoulder" to Pair(10, 11),
        "right_shoulder" to Pair(12, 13),
        "left_elbow" to Pair(14, 15),
        "right_elbow" to Pair(16, 17),
        "left_wrist" to Pair(18, 19),
        "right_wrist" to Pair(20, 21),
        "left_hip" to Pair(22, 23),
        "right_hip" to Pair(24, 25),
        "left_knee" to Pair(26, 27),
        "right_knee" to Pair(28, 29),
        "left_ankle" to Pair(30, 31),
        "right_ankle" to Pair(32, 33)
    )

}