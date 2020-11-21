package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "emergency")
@TypeConverters(MyTypeConverter::class)
class Emergency(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timestampStart: Long? = null,
    val latStart: Double? = null,
    val lngStart: Double? = null,
    var timestampFinish: Long? = null,
    var latFinish: Double? = null,
    var lngFinish: Double? = null,
    val duration: Int? = null,
    val orangTerpercaya: String? = null,
    var isActive: Boolean? = null
) {
    private fun formatTime(timestamp: Date): String {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(timestamp);
    }

    fun startTimeFormatted(): String? { return timestampStart?.let { formatTime(Date(it)) }
    }
    fun finishTimeFormatted(): String? {return timestampFinish?.let { formatTime(Date(it)) }
    }
}