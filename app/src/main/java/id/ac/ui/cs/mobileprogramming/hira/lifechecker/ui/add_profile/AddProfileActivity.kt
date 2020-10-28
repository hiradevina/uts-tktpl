package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.ActivityAddOrangTerpercayaBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.ActivityAddProfileBinding
import kotlinx.android.synthetic.main.activity_add_profile.*

class AddProfileActivity : AppCompatActivity() {
    private lateinit var addProfileViewModel: AddProfileViewModel
    private lateinit var addProfileBinding: ActivityAddProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addProfileViewModel = ViewModelProvider(this).get(AddProfileViewModel::class.java)
        addProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_profile)
        addProfileBinding.lifecycleOwner = this
        addProfileBinding.addProfileViewModel = addProfileViewModel

        addprofile_button_submit.setOnClickListener {
            addProfileViewModel.insert()
        }
    }
}