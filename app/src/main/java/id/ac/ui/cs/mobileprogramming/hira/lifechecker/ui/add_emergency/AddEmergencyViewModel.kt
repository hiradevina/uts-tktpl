package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya

class AddEmergencyViewModel (application: Application) : AndroidViewModel(application){
    var hour = MutableLiveData<String>("00")
    var minute = MutableLiveData<String>("00")
    var second = MutableLiveData<String>("00")
    var trustedPeople = MutableLiveData<OrangTerpercaya>()
    var fotoUri = MutableLiveData<String>()
    var latitude = MutableLiveData<Double>()
    var longitude = MutableLiveData<Double>()

    private fun formatTime(time: String): String {
        return String.format("%02d", time)
    }

    private fun duration(): Int {
        var h = hour.value?.toInt()?.times(3600)
        var m = minute.value?.toInt()?.times(60)
        var s = second.value?.toInt()
        return h!! + m!! + s!!
    }
}