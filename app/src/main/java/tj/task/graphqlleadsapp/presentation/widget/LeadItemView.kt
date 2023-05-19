package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.emoji.text.EmojiCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.ItemLeadBinding
import tj.task.graphqlleadsapp.domain.model.Lead
import tj.task.graphqlleadsapp.extension.toDateFormattedString

class LeadItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ItemLeadBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setItem(item: Lead) = with(binding) {
        Glide.with(context)
            .load(item.avatar?.path)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .into(ivAvatar)

        tvDisplayName.text = item.displayName

        item.country?.emoji?.let {
            ivEmoji.text = EmojiCompat.get().process(it)
        }

        llChipsContainer.removeAllViews()
        item.intention?.let {
            addChip(llChipsContainer, it.title)
        }
        item.status?.let {
            addChip(llChipsContainer, it.title)
        }
        item.adSource?.let {
            addChip(llChipsContainer, it.title)
        }
        item.channelSource?.let {
            addChip(llChipsContainer, it.title)
        }
        tvCreatedDate.text = String.format(
            resources.getString(R.string.created_date),
            item.createdAt?.toString()?.toDateFormattedString() ?: ""
        )
        tvUpdatedDate.text = String.format(
            resources.getString(R.string.updated_date),
            item.updatedAt?.toString()?.toDateFormattedString() ?: ""
        )
    }


    private fun addChip(llContainer: ViewGroup, title: String) {
        val chip = Chip(context)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val marginEndInPixels = resources.getDimensionPixelSize(R.dimen.chip_margin_end)
        layoutParams.setMargins(0, 0, marginEndInPixels, 0)
        chip.chipCornerRadius = 50.0f

        chip.text = title
        chip.tag = title
        val textSizePx = resources.getDimensionPixelSize(R.dimen.chip_text_size)
        chip.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx.toFloat())
        chip.setEnsureMinTouchTargetSize(false)
        chip.chipStrokeColor = ContextCompat.getColorStateList(context, R.color.grey_5)
        val customTypeface = ResourcesCompat.getFont(context, R.font.sf_ui_display_medium)
        chip.typeface = customTypeface

        chip.setChipBackgroundColorResource(R.color.grey_5)
        chip.setTextColor(ContextCompat.getColor(context, R.color.grey))
        chip.isClickable = false
        chip.isCheckable = false
        chip.isEnabled = false
        chip.isFocusable = false
        chip.layoutParams = layoutParams
        llContainer.addView(chip)
    }
}
