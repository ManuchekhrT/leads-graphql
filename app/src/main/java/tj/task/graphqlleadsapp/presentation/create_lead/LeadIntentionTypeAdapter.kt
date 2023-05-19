package tj.task.graphqlleadsapp.presentation.create_lead

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType
import tj.task.graphqlleadsapp.presentation.widget.LeadIntentionTypeItemView

class LeadIntentionTypeAdapter(private val itemClickListener: (LeadIntentionType) -> Unit) : ListAdapter<LeadIntentionType,
        LeadIntentionTypeAdapter.LeadIntentionTypeViewHolder>(
    object : DiffUtil.ItemCallback<LeadIntentionType>() {
        override fun areItemsTheSame(oldItem: LeadIntentionType, newItem: LeadIntentionType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LeadIntentionType, newItem: LeadIntentionType): Boolean {
            return oldItem == newItem
        }
    }
) {

    class LeadIntentionTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadIntentionTypeViewHolder {
        return LeadIntentionTypeViewHolder(LeadIntentionTypeItemView(parent.context))
    }

    override fun onBindViewHolder(holder: LeadIntentionTypeViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as LeadIntentionTypeItemView).apply {
            setItem(item)
            onItemClickListener = View.OnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}