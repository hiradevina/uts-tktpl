package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import java.io.Serializable


class OrangTerpercayaActivity : FragmentActivity(), ListOrangTerpercayaFragment.OnOrangTerpercayaSelectedListener, DetailOrangTerpercaya.onOrangTerpercayaChoosenListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_orang_terpercaya)

        if (findViewById<View?>(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return
            }

            // Create an instance of ExampleFragment
            val listFragment = ListOrangTerpercayaFragment()

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            listFragment.arguments = intent.extras

            // Add the fragment to the 'fragment_container' FrameLayout
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, listFragment).commit()
        }
    }

    override fun onOrangTerpercayaSelected(position: Int) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout

        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        val detailFragment: DetailOrangTerpercaya? =
            supportFragmentManager.findFragmentById(R.id.detail_orang_terpercaya_fragment) as DetailOrangTerpercaya?

        if (detailFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            detailFragment.updateOrangTerpercaya(position)
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            val newFragment = DetailOrangTerpercaya()
            val args = Bundle()
            args.putInt(DetailOrangTerpercaya.ARG_POSITION, position)
            newFragment.arguments = args
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment)
            transaction.addToBackStack(null)

            // Commit the transaction
            transaction.commit()
        }
    }

    override fun onOrangTerpercayaChoosen(orangTerpercaya: OrangTerpercaya) {
        setResult(Activity.RESULT_OK,
            Intent().putExtra("orang_terpercaya", orangTerpercaya as Serializable))
        finish()
    }
}