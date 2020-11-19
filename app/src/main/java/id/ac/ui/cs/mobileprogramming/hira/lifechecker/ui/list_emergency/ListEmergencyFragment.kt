package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_emergency

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.adapter.EmergencyAdapter
import kotlinx.android.synthetic.main.list_emergency_fragment.*

class ListEmergencyFragment : Fragment() {

    companion object {
        fun newInstance() = ListEmergencyFragment()
    }

    private lateinit var viewModel: ListEmergencyViewModel
    private lateinit var adapter: EmergencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_emergency_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListEmergencyViewModel::class.java)
        adapter = EmergencyAdapter()

        viewModel.emergencies.observe(
            viewLifecycleOwner,
            Observer { emergency ->
                emergency?.let {
                    adapter.setEmergencies(it)
                }
            }
        )

        emergency_recycler_view.also {
            it.layoutManager = LinearLayoutManager(view?.context)
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
    }

}