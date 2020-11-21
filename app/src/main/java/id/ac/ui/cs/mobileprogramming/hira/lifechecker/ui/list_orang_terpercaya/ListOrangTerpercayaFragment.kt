package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.adapter.OrangTerpercayaAdapter
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.RecyclerviewOnClickListener
import kotlinx.android.synthetic.main.list_orang_terpercaya_fragment.*


class ListOrangTerpercayaFragment : Fragment(), RecyclerviewOnClickListener {
    var mCallback: OnOrangTerpercayaSelectedListener? = null

    // The container Activity must implement this interface so the frag can deliver messages
    interface OnOrangTerpercayaSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected  */
        fun onOrangTerpercayaSelected(position: Int)
    }

    companion object {
        fun newInstance() =
            ListOrangTerpercayaFragment()
    }

    private lateinit var viewModel: ListOrangTerpercayaViewModel
    private lateinit var viewAdapter: OrangTerpercayaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_orang_terpercaya_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListOrangTerpercayaViewModel::class.java)
        viewAdapter =
            OrangTerpercayaAdapter(this)
        my_recycler_view.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = viewAdapter
        }
        viewModel.allOrangTerpercaya.observe(viewLifecycleOwner, Observer {
                orangTerpercaya ->
            orangTerpercaya?.let {
                viewAdapter.setOrangTerpercaya(it)
            }
        })


    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        mCallback = try {
            activity as OnOrangTerpercayaSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement OnOrangTerpercayaSelectedListener"
            )
        }
    }

    override fun recyclerviewClick(position: Int) {
        mCallback?.onOrangTerpercayaSelected(position)
    }


}
