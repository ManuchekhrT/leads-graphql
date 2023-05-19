package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import tj.task.graphqlleadsapp.databinding.ItemLeadStatusTypeBinding

class LeadStatusTypeItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ItemLeadStatusTypeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    var title: CharSequence?
        get() = binding.tvLeadStatusTitle.text
        set(value) {
            binding.tvLeadStatusTitle.text = value
        }

    var onItemClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.clLeadStatusTypeItem.setOnClickListener(value)
            binding.clLeadStatusTypeItem.isVisible = value != null
        }
}