package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.emoji.text.EmojiCompat
import tj.task.graphqlleadsapp.databinding.ItemCountryBinding
import tj.task.graphqlleadsapp.domain.model.Country

class CountryItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ItemCountryBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setItem(item: Country) {
        binding.tvTitle.text = item.title
        binding.tvPhoneCode.text = item.phoneCode
        item.emoji?.let {
            binding.tvEmoji.text = EmojiCompat.get().process(it)
        }
    }
}