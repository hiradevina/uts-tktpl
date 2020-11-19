package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_orang_terpercaya

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import kotlinx.android.synthetic.main.activity_add_orang_terpercaya.*
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.ActivityAddOrangTerpercayaBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.countdown.CountDownActivity
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya.OrangTerpercayaActivity

class AddOrangTerpercayaActivity : AppCompatActivity() {
    private lateinit var addOrangTerpercayaViewModel: AddOrangTerpercayaViewModel
    private lateinit var addOrangTerpercayaBinding: ActivityAddOrangTerpercayaBinding
    private val imagePickCode = 999
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addOrangTerpercayaViewModel =
            ViewModelProvider(this).get(AddOrangTerpercayaViewModel::class.java)
        addOrangTerpercayaBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_orang_terpercaya)

        addOrangTerpercayaBinding.lifecycleOwner = this
        addOrangTerpercayaBinding.orangTerpercayaForm = addOrangTerpercayaViewModel
        foto_button.setOnClickListener {
            launchGallery()
        }
        addorangterpercaya_button_submit.setOnClickListener {
            addOrangTerpercayaViewModel.insert()
            val intent = Intent(this, CountDownActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imagePickCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == imagePickCode) {
            val uri = data?.data
            if (uri != null) {
                addOrangTerpercayaViewModel.foto.value = uri.toString()
                foto_placeholder.setImageURI(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}