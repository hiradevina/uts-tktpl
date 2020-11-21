package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "orang_terpercaya_table")
class OrangTerpercaya(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nama: String? = null,
    val notelp: String? = null,
    val relasi: String? = null,
    val foto: String? = null
)