package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.countdown

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneId
import java.util.*

class CountdownViewModel(application: Application) : AndroidViewModel(application) {
    var duration:Long? = 0

    private val repository: EmergencyRepository



    init {
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        repository =
            EmergencyRepository(
                emergencyDao
            )
        val emergency = repository.getActiveLifecheck()
        val finishTimeInEpoch: Long? = emergency?.duration?.times(1000)?.plus(emergency.timestampStart ?: 0)
        duration = finishTimeInEpoch?.minus(System.currentTimeMillis())?.div(1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun finishLifechecker(mLocation: Location?) {
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