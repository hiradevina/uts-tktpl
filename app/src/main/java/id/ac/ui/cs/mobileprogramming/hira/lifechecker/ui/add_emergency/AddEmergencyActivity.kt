package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.constants.AppConstants
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.ActivityAddEmergencyBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.receiver.AlertReceiver
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_orang_terpercaya.AddOrangTerpercayaActivity
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.countdown.CountDownActivity
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya.OrangTerpercayaActivity
import kotlinx.android.synthetic.main.activity_add_emergency.*
import java.security.AccessController.getContext

class AddEmergencyActivity : AppCompatActivity() {
    private val TAG: String = "AddEmergencyActivity"
    var imageUri: Uri? = null

    private val CAMERA_PERMISSION_CODE = 1000
    private val IMAGE_INTENT_CODE = 1001
    private val LOCATION_PERMISSION_CODE = 34
    private val ORANGTERPERCAYA_INTENT_CODE = 888

    private lateinit var viewModel: AddEmergencyViewModel
    private lateinit var binding: ActivityAddEmergencyBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEmergencyViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_emergency)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        addemergency_submit_button.setOnClickListener {
            if (!checkPermissions()) {
                requestLocationPermission()
            } else {
                getLastLocation()
            }

        }

        addemergency_foto_button.setOnClickListener {
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, CAMERA_PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }

        addemergency_selectorangterpercaya_button.setOnClickListener {
            var intent = Intent(this, OrangTerpercayaActivity::class.java)
            startActivityForResult(intent, ORANGTERPERCAYA_INTENT_CODE)
        }

        fab_tambahorangterpercaya.setOnClickListener {
            var intent = Intent(this, AddOrangTerpercayaActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (viewModel.latitude.value == null && viewModel.longitude.value == null) {
                if (location != null) {
                    viewModel.longitude.value = location.longitude
                    viewModel.latitude.value = location.latitude
                    viewModel.insert()
                    Log.d("SetAlarm", "before call alarm")
                    setAlarm()
                    //val intent = Intent(this, CountDownActivity::class.java)
                    //startActivity(intent)
                }
            }


        }

    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val permissionlagi = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED && permissionlagi == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_CODE
        )
    }

    private fun requestLocationPermission() {

        startLocationPermissionRequest()

    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_INTENT_CODE)
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {

            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                //permission from popup was granted
                openCamera()
            } else {
                //permission from popup was denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm() {
        Log.d("SetAlarm", "call set alarm")
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra(AppConstants.notificationTitleIntentKey, "Lifechecker")
        intent.putExtra(
            AppConstants.notificationDescriptionIntentKey,
            viewModel.notificationDescriptionText()
        )
        val notificationReceiverIntent = PendingIntent.getBroadcast(
            this, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            System.currentTimeMillis() + (viewModel.duration() * 1000),
            notificationReceiverIntent
        )
        Log.d("SetAlarm", "set alarm done")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_INTENT_CODE) {
                //set image captured to image view
                addemergency_foto_placeholder.setImageURI(imageUri)
                viewModel.fotoUri.value = imageUri.toString()
            } else if (requestCode == ORANGTERPERCAYA_INTENT_CODE) {
                if (data != null) {
                    if (data.extras != null) {
                        viewModel.trustedPeople.value =
                            data.extras!!["orang_terpercaya"] as String?
                    }
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}