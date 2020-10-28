package id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency

@Dao
interface EmergencyDao {
    @Query("SELECT * FROM emergency")
    fun getAll(): LiveData<List<Emergency>>

    @Update
    fun update(emergency: Emergency)

    @Query("SELECT * FROM emergency where id= :id")
    fun get(vararg id: Int): Emergency

    @Query("SELECT * FROM emergency where isActive= 1")
    fun getActiveLifecheck(): LiveData<List<Emergency>>

    @Insert
    fun insert(vararg emergency: Emergency)
}