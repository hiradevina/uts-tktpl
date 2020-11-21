package id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency

@Dao
interface EmergencyDao {
    @Query("SELECT * FROM emergency")
    fun getAll(): LiveData<List<Emergency>>

    @Update
    fun update(emergency: Emergency)

    @Query("SELECT * FROM emergency where id= :id")
    suspend fun get(vararg id: Int): Emergency

    @Query("SELECT * FROM emergency where isActive= 1")
    suspend fun getActiveLifecheck(): List<Emergency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg emergency: Emergency)
}