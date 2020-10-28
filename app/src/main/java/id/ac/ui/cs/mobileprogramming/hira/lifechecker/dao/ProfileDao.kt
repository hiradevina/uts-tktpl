package id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun get() : LiveData<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(vararg profile: Profile)
}