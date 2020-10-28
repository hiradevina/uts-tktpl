package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.adapter.OrangTerpercayaAdapter
import kotlinx.android.synthetic.main.list_orang_terpercaya_fragment.*


class ListOrangTerpercayaFragment : Fragment() {

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
        viewModel = ViewModelProvider(this).get(ListOrangTerpercayaViewModel::class.java)
        viewAdapter =
            OrangTerpercayaAdapter()
        viewModel.allOrangTerpercaya.observe(viewLifecycleOwner, Observer {
                orangTerpercaya ->
            orangTerpercaya?.let {
                viewAdapter.setOrangTerpercaya(it)
            }
        })
        my_recycler_view.also {
            it.layoutManager = LinearLayoutManager(view?.context)
            it.setHasFixedSize(true)
            it.adapter = viewAdapter
        }

    }

}
