import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.R
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.databinding.EmergencyItemListBinding
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency

class EmergencyAdapter() :
    RecyclerView.Adapter<EmergencyAdapter.EmergencyViewHolder>() {
    private var emergencies = emptyList<Emergency>()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class EmergencyViewHolder(val emergencyBinding: EmergencyItemListBinding) :
        RecyclerView.ViewHolder(emergencyBinding.root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmergencyAdapter.EmergencyViewHolder {
        // create a new view

        return EmergencyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.emergency_item_list,
                parent,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.emergencyBinding.emergency = emergencies[position]
    }

    internal fun setEmergencies(emergencies: List<Emergency>) {
        this.emergencies = emergencies
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = emergencies.size
}