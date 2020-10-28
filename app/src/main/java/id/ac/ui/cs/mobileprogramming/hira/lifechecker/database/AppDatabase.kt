package id.ac.ui.cs.mobileprogramming.hira.lifechecker.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.EmergencyDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.OrangTerpercayaDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.ProfileDao

@Database(entities = [OrangTerpercaya::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    // daos
    abstract fun orangTerpercayaDao(): OrangTerpercayaDao
    abstract fun profileDao() : ProfileDao
    abstract fun emergencyDao() : EmergencyDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}