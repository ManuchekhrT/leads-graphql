package tj.task.graphqlleadsapp.presentation.lead

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.task.graphqlleadsapp.domain.model.Lead
import tj.task.graphqlleadsapp.presentation.widget.LeadItemView

class LeadAdapter : ListAdapter<Lead, LeadAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LeadItemView(parent.context))
    }

    var onItemClickListener: ((item: Lead) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as LeadItemView).apply {
            setItem(item)
            setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lead>() {
            override fun areItemsTheSame(
                oldItem: Lead,
                newItem: Lead
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Lead,
                newItem: Lead
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}