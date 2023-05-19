package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import tj.task.graphqlleadsapp.databinding.ItemLanguageBinding
import tj.task.graphqlleadsapp.domain.model.Language

class LanguageItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ItemLanguageBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setItem(item: Language) {
        binding.tvTitle.text = item.title
        binding.ivCheck.isVisible = item.isSelected
    }
}