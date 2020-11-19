package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.TypeConverter
import java.util.*

class MyTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}