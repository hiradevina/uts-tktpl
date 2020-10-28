package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orang_terpercaya_table")
class OrangTerpercaya(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nama: String,
    val notelp: String,
    val relasi: String,
    val foto: String
)