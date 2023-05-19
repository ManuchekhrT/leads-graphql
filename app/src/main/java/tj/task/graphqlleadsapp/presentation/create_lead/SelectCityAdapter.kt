package tj.task.graphqlleadsapp.presentation.create_lead

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.task.graphqlleadsapp.domain.model.City
import tj.task.graphqlleadsapp.presentation.widget.CityItemView

class SelectCityAdapter(private val itemClickListener: (City) -> Unit) : ListAdapter<City,
        SelectCityAdapter.CityViewHolder>(
    object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var filterList: List<City> = emptyList()

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(CityItemView(parent.context))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as CityItemView).apply {
            setItem(item)
            setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}