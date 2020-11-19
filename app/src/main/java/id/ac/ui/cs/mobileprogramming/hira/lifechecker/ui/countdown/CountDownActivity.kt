package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.countdown

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import kotlinx.android.synthetic.main.activity_count_down.*
import java.util.*


class CountDownActivity : AppCompatActivity() {
    private var running = true
    private val TAG = "CountdownActivity"
    private lateinit var viewModel: CountdownViewModel
    private var mLocationManager: LocationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLocationManager()
        viewModel = ViewModelProvider(this).get(CountdownViewModel::class.java)
        setContentView(R.layout.activity_count_down)
        button_stop.setOnClickListener {
            try {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                }
                running = false
                viewModel.finishLifechecker(mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER))
            } catch (ex: SecurityException) {
                Log.i(TAG, "fail to request location update, ignore", ex)
            } catch (ex: IllegalArgumentException) {
                Log.d(TAG, "gps provider does not exist " + ex.message)
            }
        }
        runTimer()
    }

    private fun initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    fun runTimer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                var second = viewModel.duration
                val hours = second?.div(3600)
                val minutes = (second?.rem(3600) ?: 0) / 60
                val sec = second?.rem(60)
                val timeFormat: String = java.lang.String.format(
                    Locale.getDefault(),
                    "%d:%02d:%02d",
                    hours,
                    minutes,
                    sec
                )
                text_second.text = timeFormat
                if (running) {
                    if (second != null) {
                        second -= 1
                    }
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}