package id.ac.ui.cs.mobileprogramming.hira.lifechecker.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
const val ACTION_FOO = "id.ac.ui.cs.mobileprogramming.hira.lifechecker.service.action.FOO"
const val ACTION_BAZ = "id.ac.ui.cs.mobileprogramming.hira.lifechecker.service.action.BAZ"

// TODO: Rename parameters
const val EXTRA_PARAM1 = "id.ac.ui.cs.mobileprogramming.hira.lifechecker.service.extra.PARAM1"
const val EXTRA_PARAM2 = "id.ac.ui.cs.mobileprogramming.hira.lifechecker.service.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class LifecheckerService : IntentService("LifecheckerService") {
    private val TAG = "LifecheckerService"

    private var mLocationManager: LocationManager? = null
    private var mLocation: Location? = null
    private val repository: EmergencyRepository


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        if (repository.isLifecheckRunning()) {
            try {
                mLocation = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } catch (ex: SecurityException) {
                Log.i(TAG, "fail to request location update, ignore", ex)
            } catch (ex: IllegalArgumentException) {
                Log.d(TAG, "gps provider does not exist " + ex.message)
            }
            val runningLifecheck: Emergency? = repository.getActiveLifecheck()
            runningLifecheck?.latFinish = mLocation?.latitude
            runningLifecheck?.lngFinish = mLocation?.longitude
            runningLifecheck?.timestampFinish = System.currentTimeMillis()
            runningLifecheck?.isActive = false
            if (runningLifecheck != null) {
                repository.update(runningLifecheck)
            }
        }

    }

    init {
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        repository =
            EmergencyRepository(
                emergencyDao
            )
    }

    override fun onCreate() {
        super.onCreate()
        initializeLocationManager()
        Log.e(TAG, "onCreate")

    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        TODO("Handle action Baz")
    }

    private fun initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager")
        if (mLocationManager == null) {
            mLocationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }
}