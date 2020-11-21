package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.FragmentDetailOrangTerpercayaBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import kotlinx.android.synthetic.main.fragment_detail_orang_terpercaya.*


class DetailOrangTerpercaya : Fragment() {
    companion object {
        val ARG_POSITION = "position"
    }

    var mCurrentPosition = -1

    private lateinit var viewModel: DetailOrangTerpercayaViewModel
    private lateinit var binding: FragmentDetailOrangTerpercayaBinding

    var callback: onOrangTerpercayaChoosenListener? = null

    interface onOrangTerpercayaChoosenListener {
        fun onOrangTerpercayaChoosen(orangTerpercaya: OrangTerpercaya)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        viewModel = ViewModelProvider(requireActivity()).get(DetailOrangTerpercayaViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        if (args != null) {
            Log.d("detailorangterpercaya", "print args $args")
            // Set article based on argument passed in
            updateOrangTerpercaya(args.getInt(ARG_POSITION))

        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateOrangTerpercaya(mCurrentPosition)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_orang_terpercaya,container,false)
        binding.lifecycleOwner = this
        viewModel.orangTerpercaya.observe(viewLifecycleOwner, Observer {
            val ot = it
            binding.orangTerpercaya = it
            button_detailorangterpercaya.setOnClickListener {
                callback?.onOrangTerpercayaChoosen(ot)
            }
            //val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
            //orangterpercaya_foto.setImageURI(Uri.parse(ot.foto))
        })
        return binding.root
    }

    fun updateOrangTerpercaya(position: Int) {
        viewModel.getSelectedOrangTerpercaya(position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = try {
            activity as DetailOrangTerpercaya.onOrangTerpercayaChoosenListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement OnOrangTerpercayaSelectedListener"
            )
        }
    }
}