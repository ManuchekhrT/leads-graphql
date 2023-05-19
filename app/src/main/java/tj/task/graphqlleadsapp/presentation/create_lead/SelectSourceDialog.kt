package tj.task.graphqlleadsapp.presentation.create_lead

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
import tj.task.graphqlleadsapp.databinding.DialogSelectSourceBinding
import tj.task.graphqlleadsapp.domain.model.LeadSource
import tj.task.graphqlleadsapp.presentation.widget.SourceItemView

@AndroidEntryPoint
class SelectSourceDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSelectSourceBinding
    private val viewModel: CreateNewLeadViewModel by viewModels()

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
        binding = DialogSelectSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLeadSources()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourcesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.llSourcesContainer.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.llSourcesContainer.isVisible = true
                        if (it.sources.isNotEmpty()) {
                            prepareSources(it.sources)
                        }
                    }
                }
            }
        }
    }

    private fun prepareSources(sources: List<LeadSource>) {
        sources.forEach { item ->
            val view = SourceItemView(requireContext())
            view.title = item.title
            view.tag = item.id
            view.onItemClickListener = View.OnClickListener {
                selectedView(item)
            }
            binding.llSourcesContainer.addView(view)
        }
    }

    private fun selectedView(item: LeadSource) {
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_SOURCE_ITEM to item))
        dismiss()
    }

    companion object {
        const val REQUEST_KEY = "source_item_key"
        const val ARG_SOURCE_ITEM = "arg_source_item"
        const val TAG_SELECT_SOURCE_DIALOG = "TAG_SelectSourceDialog"
    }
}