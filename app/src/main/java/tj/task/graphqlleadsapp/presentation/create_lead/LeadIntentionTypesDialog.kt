package tj.task.graphqlleadsapp.presentation.create_lead

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.databinding.DialogLeadIntentionTypesBinding
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType
import tj.task.graphqlleadsapp.presentation.lead.LeadViewModel

@AndroidEntryPoint
class LeadIntentionTypesDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogLeadIntentionTypesBinding
    private val viewModel: LeadViewModel by viewModels()

    private var leadIntentionTypeAdapter: LeadIntentionTypeAdapter? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                BottomSheetBehavior.from(parent).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLeadIntentionTypesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLeadIntentionTypes()

        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        leadIntentionTypeAdapter = LeadIntentionTypeAdapter {
            onLeadIntentionTypeItemClickListener(it)
        }
        rvLeadIntentionTypes.adapter = leadIntentionTypeAdapter
    }

    private fun onLeadIntentionTypeItemClickListener(item: LeadIntentionType) {
        leadIntentionTypeAdapter?.currentList?.let {
            it.forEachIndexed { index, aLeadIntentionType ->
                aLeadIntentionType.isSelected = false
                leadIntentionTypeAdapter?.notifyItemChanged(index)
            }
            item.isSelected = !item.isSelected
            leadIntentionTypeAdapter?.notifyItemChanged(it.indexOf(item))
        }
        Log.d("INTENTION_TYPE: ", "===$item")
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_LEAD_INTENTION_TYPE to item))
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leadIntentionTypesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.rvLeadIntentionTypes.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.rvLeadIntentionTypes.isVisible = true
                        if (it.leadIntentionTypeList.isNotEmpty()) {
                            leadIntentionTypeAdapter?.submitList(it.leadIntentionTypeList)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "lead_intention_type_item_key"
        const val ARG_LEAD_INTENTION_TYPE = "arg_lead_intention_type_item"
        const val TAG_LEAD_INTENTION_TYPE_DIALOG = "TAG_LeadIntentionTypeDialog"
    }
}