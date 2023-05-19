package tj.task.graphqlleadsapp.presentation.create_lead

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.task.graphqlleadsapp.domain.model.Country
import tj.task.graphqlleadsapp.presentation.widget.CountryItemView

class SelectCountryAdapter(private val itemClickListener: (Country) -> Unit) : ListAdapter<Country,
        SelectCountryAdapter.CountryViewHolder>(
    object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var filterList: List<Country> = emptyList()

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(CountryItemView(parent.context))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as CountryItemView).apply {
            setItem(item)
            setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}