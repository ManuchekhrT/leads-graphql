package tj.task.graphqlleadsapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.ViewGeneralInfoLeadProfileBinding
import tj.task.graphqlleadsapp.domain.model.DetailedLead
import tj.task.graphqlleadsapp.domain.model.Intention
import tj.task.graphqlleadsapp.extension.toDateFormattedString

class GeneralInfoLeadProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewGeneralInfoLeadProfileBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    var onIntentionInputClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.textInputEdittextLeadIntention.setOnClickListener(value)
        }

    fun setItem(item: DetailedLead) = with(binding) {
        setIntentionType(item.intention)

        item.adSource?.let {
            textInputEtAdSource.setText(it.title)
        } ?: showUnknownText(textInputEtAdSource)

        item.country?.let {
            textInputEdittextCountry.setText(it.title)
        } ?: showUnknownText(textInputEdittextCountry)

        item.webSource?.let {
            textInputWebSource.setText(it.title)
        } ?: showUnknownText(textInputWebSource)

        item.city?.let {
            textInputEdittextCityOrRegion.setText(it.title)
        } ?: showUnknownText(textInputEdittextCityOrRegion)

        item.channelSource?.let {
            textInputEtChannelSource.setText(it.title)
        } ?: showUnknownText(textInputEtChannelSource)

        if (item.languages.isNotEmpty()) {
            item.languages.firstOrNull()?.let {
                textInputEdittextLanguage.setText(it.title)
            } ?: showUnknownText(textInputEdittextLanguage)
        }

        item.propertyType?.let {
            textInputEdittextPropertyType.setText(it.title)
        } ?: showUnknownText(textInputEdittextPropertyType)

        item.nationality?.let {
            textInputEdittextPropertyType.setText(it.title)
        } ?: showUnknownText(textInputEdittextPropertyType)

        item.birthDate?.let {
            textInputEdittextBirthday.setText(it.toString().toDateFormattedString()
                ?: resources.getString(R.string.unknown))
        } ?: showUnknownText(textInputEdittextBirthday)

        item.budget?.let {
            textInputEtBudget.setText(it.toString())
        } ?: showUnknownText(textInputEtBudget)

    }

    fun setIntentionType(intention: Intention?) {
        binding.textInputEdittextLeadIntention.isFocusable = false
        binding.textInputEdittextLeadIntention.isClickable = true
        binding.textInputEdittextLeadIntention.isLongClickable = true
        binding.textInputEdittextLeadIntention.keyListener = null
        intention?.let {
            binding.textInputEdittextLeadIntention.text?.clear()
            binding.textInputEdittextLeadIntention.setText(it.title)
        } ?: showUnknownText(binding.textInputEdittextLeadIntention)
    }

    private fun showUnknownText(et: TextInputEditText) {
        et.setText(resources.getString(R.string.unknown))
        et.setTextColor(ContextCompat.getColor(context, R.color.grey_2))
    }
}