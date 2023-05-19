package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import tj.task.graphqlleadsapp.databinding.ItemLeadIntentionTypeBinding
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType

class LeadIntentionTypeItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ItemLeadIntentionTypeBinding
            .inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    var title: CharSequence?
        get() = binding.tvTitle.text
        set(value) {
            binding.tvTitle.text = value
        }

    var onItemClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.clLeadIntentionType.setOnClickListener(value)
            binding.clLeadIntentionType.isVisible = value != null
        }

    fun setItem(item: LeadIntentionType) {
        title = item.title
        isActivated = item.isSelected
    }
}