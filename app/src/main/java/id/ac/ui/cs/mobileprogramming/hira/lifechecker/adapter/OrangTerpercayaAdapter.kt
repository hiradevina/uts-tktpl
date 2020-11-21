package id.ac.ui.cs.mobileprogramming.hira.lifechecker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.OrangTerpercayaItemListBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.RecyclerviewOnClickListener

class OrangTerpercayaAdapter(private var mOnClickListener: RecyclerviewOnClickListener) :
    RecyclerView.Adapter<OrangTerpercayaAdapter.OrangTerpercayaViewHolder>() {
    private var listOrangTerpercaya = emptyList<OrangTerpercaya>()
    private lateinit var context: Context
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class OrangTerpercayaViewHolder(val recyclerViewOrangTerpercayaBinding: OrangTerpercayaItemListBinding) :
        RecyclerView.ViewHolder(recyclerViewOrangTerpercayaBinding.root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrangTerpercayaViewHolder {
        // set the view's size, margins, paddings and layout parameters
        context = parent.context
        return OrangTerpercayaViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.orang_terpercaya_item_list,
                parent,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: OrangTerpercayaViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.recyclerViewOrangTerpercayaBinding.orangTerpercaya = listOrangTerpercaya[position]
        holder.itemView.setOnClickListener {
            mOnClickListener.recyclerviewClick(listOrangTerpercaya[position].id!!)
        }
    }

    fun setOrangTerpercaya(listOrangTerpercaya: List<OrangTerpercaya>) {
        this.listOrangTerpercaya = listOrangTerpercaya
        notifyDataSetChanged()
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listOrangTerpercaya.size

}