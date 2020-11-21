package id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class Profile(
    @PrimaryKey val nama: String,
    val alamat: String? = null,
    val golonganDarah: String? = null,
    val notelp: String? = null
)