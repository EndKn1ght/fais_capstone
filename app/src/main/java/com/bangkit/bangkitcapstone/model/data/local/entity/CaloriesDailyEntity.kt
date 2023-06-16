package com.bangkit.bangkitcapstone.model.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CaloriesDailyEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "intake")
    val intake: String,

    @ColumnInfo(name = "type", defaultValue = "0")
    val intakeType: IntakeType = IntakeType.FOOD,

    @ColumnInfo(name = "duration")
    val duration: String? = null,

    @ColumnInfo(name = "date")
    val dateIntake: String,

    ) : Parcelable