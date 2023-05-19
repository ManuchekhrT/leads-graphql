package tj.task.graphqlleadsapp.presentation.lead

import android.app.Dialog
import android.os.Bundle
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
import tj.task.graphqlleadsapp.databinding.DialogLeadStatusTypesBinding
import tj.task.graphqlleadsapp.domain.model.LeadStatusType
import tj.task.graphqlleadsapp.presentation.widget.LeadStatusTypeItemView

@AndroidEntryPoint
class LeadStatusTypesDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogLeadStatusTypesBinding

    private val viewModel: LeadViewModel by viewModels()

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
        binding = DialogLeadStatusTypesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLeadStatusTypes()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leadStatusTypesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.llLeadStatusTypesContainer.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.llLeadStatusTypesContainer.isVisible = true
                        if (it.leadStatusTypeList.isNotEmpty()) {
                            prepareLeadStatusTypes(it.leadStatusTypeList)
                        }
                    }
                }
            }
        }
    }

    private fun prepareLeadStatusTypes(leadStatusTypeList: List<LeadStatusType>) {
        leadStatusTypeList.forEach { item ->
            val view = LeadStatusTypeItemView(requireContext())
            view.title = item.title
            view.tag = item.id
            view.onItemClickListener = View.OnClickListener {
                selectedView(item)
            }
            binding.llLeadStatusTypesContainer.addView(view)
        }
    }

    private fun selectedView(item: LeadStatusType) {
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_LEAD_STATUS_TYPE_ITEM to item))
        dismiss()
    }

    companion object {
        const val REQUEST_KEY = "lead_status_type_item_key"
        const val ARG_LEAD_STATUS_TYPE_ITEM = "arg_lead_status_type_item"
        const val TAG_LEAD_STATUS_TYPE_DIALOG = "TAG_LeadStatusTypeDialog"
    }
}