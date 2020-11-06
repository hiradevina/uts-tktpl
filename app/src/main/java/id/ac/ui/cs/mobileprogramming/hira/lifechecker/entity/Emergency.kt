package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Duration

@Entity(tableName = "emergency")
class Emergency(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timestampStart: Timestamp,
    val latStart: Double,
    val lngStart: Double,
    val timestampFinish: Timestamp,
    val latFinish: Double,
    val lngFinish: Double,
    val duration: Int,
    val orangTerpercaya: OrangTerpercaya,
    val isActive: Boolean
) {
    private fun formatTime(timestamp: Timestamp): String {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(timestamp);
    }

    val startTimeFormatted: String = formatTime(timestampStart)
    val finishTimeFormatted: String = formatTime(timestampFinish)
}