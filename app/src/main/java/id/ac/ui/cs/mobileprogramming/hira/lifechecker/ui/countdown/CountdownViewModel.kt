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
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.Coroutines
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import kotlinx.coroutines.Job
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneId
import java.util.*

class CountdownViewModel(application: Application) : AndroidViewModel(application) {
    var duration = MutableLiveData<Long>()
    private val TAG: String = "CountdownViewModel"

    private val repository: EmergencyRepository
    private lateinit var job: Job

    var emergency = MutableLiveData<Emergency>()



    init {
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        repository =
            EmergencyRepository(
                emergencyDao
            )
        getEmergencyActive()

    }

    fun getEmergencyActive() {
        job = Coroutines.ioThenMain(
            {repository.getActiveLifecheck()},
            {
                if (it != null) {
                    emergency.value = it
                    Log.d(TAG, "emergency lifecheck detected: $it")
                    val finishTimeInEpoch: Long? = it.duration!!.times(1000).plus(it.timestampStart!!)
                    Log.d(TAG, "timestamp start ${it.timestampStart}")
                    Log.d(TAG, "finishtime in epoch ${finishTimeInEpoch}")
                    duration.value = finishTimeInEpoch?.minus(System.currentTimeMillis())?.div(1000)
                    Log.d(TAG, "current time millis ${System.currentTimeMillis()}")
                    Log.d(TAG, "duration ${duration.value}")
                }

            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun finishLifechecker(mLocation: Location?) {
        val runningLifecheck: Emergency? = emergency.value
        runningLifecheck?.latFinish = mLocation?.latitude
        runningLifecheck?.lngFinish = mLocation?.longitude
        runningLifecheck?.timestampFinish = System.currentTimeMillis()
        runningLifecheck?.isActive = false
        if (runningLifecheck != null) {
            repository.update(runningLifecheck)
        }
    }


}