package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
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
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.constants.AppConstants
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.ActivityAddEmergencyBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.receiver.AlertReceiver
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya.OrangTerpercayaActivity
import kotlinx.android.synthetic.main.activity_add_emergency.*
import java.security.AccessController.getContext

class AddEmergencyActivity : AppCompatActivity() {
    private val TAG: String = "AddEmergencyActivity"
    private var mLastLocation: Location? = null
    var imageUri: Uri? = null

    private val CAMERA_PERMISSION_CODE = 1000;
    private val IMAGE_INTENT_CODE = 1001
    private val LOCATION_PERMISSION_CODE = 34
    private val REQUEST_SMS_PERMISSION = 101
    private val ORANGTERPERCAYA_INTENT_CODE = 888

    private lateinit var viewModel: AddEmergencyViewModel
    private lateinit var binding: ActivityAddEmergencyBinding

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEmergencyViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_emergency)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        addemergency_submit_button.setOnClickListener {
            if (!checkPermissions()) {
                requestLocationPermission()
            } else {
                getLastLocation()
            }
            viewModel.insert()
            setAlarm()
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
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    viewModel.latitude.value = mLastLocation!!.latitude
                    viewModel.longitude.value = mLastLocation!!.longitude
                }
            }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_CODE
        )
    }

    private fun requestLocationPermission() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.permission_rationale,
                Snackbar.LENGTH_LONG
            ).setAction("Ok", View.OnClickListener {
                startLocationPermissionRequest()
            })


        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
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
        } else if (requestCode == IMAGE_INTENT_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                //permission from popup was granted
                openCamera()
            } else {
                //permission from popup was denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myMessage();
            } else {
                Toast.makeText(
                    this, "You don't have required permission to send a message",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm() {
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
            SystemClock.elapsedRealtime() + (viewModel.duration() * 1000),
            notificationReceiverIntent
        )
    }

    fun sendMessage(view: View) {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myMessage()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.SEND_SMS),
                REQUEST_SMS_PERMISSION
            )
        }
    }

    private fun myMessage() {
//        val myNumber: String = editTextNumber.text.toString().trim()
//        val myMsg: String = editTextMessage.text.toString().trim()
        val myNumber: String = ""
        val myMsg: String = ""
        if (myNumber == "" || myMsg == "") {
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter the correct number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_INTENT_CODE) {
                //set image captured to image view
                addemergency_foto_placeholder.setImageURI(imageUri)
                viewModel.fotoUri.value = imageUri.toString()
            } else if (requestCode == ORANGTERPERCAYA_INTENT_CODE) {
                viewModel.trustedPeople.value =
                    data?.getSerializableExtra("orang_terpercaya") as OrangTerpercaya
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}