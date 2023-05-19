package tj.task.graphqlleadsapp.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.ViewTopInfoLeadProfileBinding
import tj.task.graphqlleadsapp.domain.model.Contact
import tj.task.graphqlleadsapp.domain.model.DetailedLead
import tj.task.graphqlleadsapp.domain.model.Status

class TopInfoLeadProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewTopInfoLeadProfileBinding
            .inflate(LayoutInflater.from(context), this, true)

    var onContactViewClicked: ((Contact) -> Unit)? = null

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    var onLeadStatusItemClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.clLeadStatus.setOnClickListener(value)
            binding.clLeadStatus.isVisible = value != null
        }

    @SuppressLint("InflateParams")
    fun setItem(item: DetailedLead) = with(binding) {
        Glide.with(context)
            .load(item.avatar?.path)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .into(ivBgAvatar)

        tvDisplayName.text = item.displayName
        tvPersonId.text = String.format(
            resources.getString(R.string.show_person_id),
            item.personId ?: 0
        )

        item.status?.let {
            prepareStatusType(it)
        }

        if (item.contacts.isNotEmpty()) {
            llContactsContainer.removeAllViews()
            item.contacts.forEach { aContact ->
                aContact.type?.let {
                    if (it.title == ContactType.Phone.name) {
                        val contactView = LayoutInflater.from(context).inflate(R.layout.view_contact, null)
                        val icon = contactView.findViewById<ImageView>(R.id.iv_contact_icon)
                        val title = contactView.findViewById<MaterialTextView>(R.id.tv_contact_type)
                        title.text = it.title
                        contactView.setOnClickListener {
                            onContactViewClicked?.invoke(aContact)
                        }
                        icon.setBackgroundResource(R.drawable.ic_phone)
                        llContactsContainer.addView(contactView)
                    }

                    if (it.title == ContactType.Email.name) {
                        val contactView = LayoutInflater.from(context).inflate(R.layout.view_contact, null)
                        val icon = contactView.findViewById<ImageView>(R.id.iv_contact_icon)
                        val title = contactView.findViewById<MaterialTextView>(R.id.tv_contact_type)
                        title.text = it.title
                        icon.setBackgroundResource(R.drawable.ic_mail)
                        contactView.setOnClickListener {
                            onContactViewClicked?.invoke(aContact)
                        }
                        llContactsContainer.addView(contactView)
                    }                 }
            }
        }
    }


    fun prepareStatusType(it: Status) {
        binding.viewCirclePoint.setCircleColor(Color.parseColor(it.color))
        binding.tvLeadStatusTitle.text = it.title

        binding.llStepsContainer.removeAllViews()
        for(i in 1..it.stepsCount) {
            val stepView = LayoutInflater.from(context).inflate(R.layout.view_step, null)
            val step = stepView.findViewById<View>(R.id.step)
            if (i <= it.step) {
                step.setBackgroundResource(R.drawable.bg_rounded_rectangle_active)
            } else {
                step.setBackgroundResource(R.drawable.bg_rounded_rectangle_disabled)
            }
            binding.llStepsContainer.addView(stepView)
        }
    }
}