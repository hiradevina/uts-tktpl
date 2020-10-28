package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
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
    val duration: Duration,
    val orangTerpercaya: OrangTerpercaya
)