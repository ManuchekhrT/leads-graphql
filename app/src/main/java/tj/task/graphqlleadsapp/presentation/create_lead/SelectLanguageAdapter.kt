package tj.task.graphqlleadsapp.presentation.create_lead

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.task.graphqlleadsapp.domain.model.Language
import tj.task.graphqlleadsapp.presentation.widget.LanguageItemView

class SelectLanguageAdapter(private val itemClickListener: (Language) -> Unit) :
    ListAdapter<Language,
            SelectLanguageAdapter.LanguageViewHolder>(
        object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private var filterList: List<Language> = emptyList()

    class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun filter(query: String) {
        filterList = if (query.isEmpty()) {
            currentList
        } else {
            currentList.filter { item ->
                // Apply your search logic here, e.g., item.name.contains(filterPattern)
                item.title.contains(query, ignoreCase = true)
            }
        }
        submitList(filterList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(LanguageItemView(parent.context))
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as LanguageItemView).apply {
            setItem(item)
            setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}