package id.ac.ui.cs.mobileprogramming.hira.lifechecker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency.AddEmergencyActivity
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_profile.AddProfileActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        if (viewModel.isProfileExist()) {
            val intent = Intent(this, AddEmergencyActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AddProfileActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
