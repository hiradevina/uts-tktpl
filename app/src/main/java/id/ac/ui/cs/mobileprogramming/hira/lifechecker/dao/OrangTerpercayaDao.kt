package id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya

@Dao
interface OrangTerpercayaDao {
    @Query("SELECT * from orang_terpercaya_table")
    fun getAll(): LiveData<List<OrangTerpercaya>>

    @Query("SELECT * from orang_terpercaya_table where id= :id")
    fun getOrangTerpercaya(vararg id: Int) : OrangTerpercaya

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrangTerpercaya(vararg orangTerpercaya: OrangTerpercaya)
}