package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import tj.task.graphqlleadsapp.databinding.ItemSourceBinding

class SourceItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ItemSourceBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    var title: CharSequence?
        get() = binding.tvSourceTitle.text
        set(value) {
            binding.tvSourceTitle.text = value
        }

    var onItemClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.clSourceItem.setOnClickListener(value)
            binding.clSourceItem.isVisible = value != null
        }
}