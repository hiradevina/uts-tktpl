package id.ac.ui.cs.mobileprogramming.hira.lifechecker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_orang_terpercaya.AddOrangTerpercayaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, AddOrangTerpercayaActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
