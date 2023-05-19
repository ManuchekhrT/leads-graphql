package tj.task.graphqlleadsapp.presentation.create_lead

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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
import tj.task.graphqlleadsapp.databinding.DialogSelectLanguageBinding
import tj.task.graphqlleadsapp.domain.model.Language

@AndroidEntryPoint
class SelectLanguageDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSelectLanguageBinding

    private val viewModel: CreateNewLeadViewModel by viewModels()

    private var selectLanguageAdapter: SelectLanguageAdapter? = null
    private var languages = mutableListOf<Language>()

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
        binding = DialogSelectLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLanguages()
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        selectLanguageAdapter = SelectLanguageAdapter {
            onLanguageItemClick(it)
        }
        rvLanguages.adapter = selectLanguageAdapter

        textInputSearch.doAfterTextChanged { text ->
            if (text.toString().isNotBlank()) {
                selectLanguageAdapter?.filter(text.toString())
            } else {
                selectLanguageAdapter?.submitList(languages)
            }
        }
    }

    private fun onLanguageItemClick(item: Language) {
        selectLanguageAdapter?.currentList?.let { languages ->
            item.isSelected = !item.isSelected
            languages.forEach { aLanguage ->
                if (aLanguage.id == item.id) {
                    aLanguage.isSelected = item.isSelected
                    selectLanguageAdapter?.notifyItemChanged(languages.indexOf(aLanguage))
                }
            }
            selectLanguageAdapter?.notifyItemChanged(languages.indexOf(item))
            val selectedLanguages = languages.filter { it.isSelected }
            setFragmentResult(REQUEST_KEY, bundleOf(ARG_LANGUAGE to selectedLanguages))
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.languagesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.rvLanguages.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.rvLanguages.isVisible = true
                        if (it.languages.isNotEmpty()) {
                            prepareLanguages(it.languages)
                        }
                    }
                }
            }
        }
    }

    private fun prepareLanguages(languages: List<Language>) {
        selectLanguageAdapter?.submitList(languages)
    }

    companion object {
        const val REQUEST_KEY = "select_language_key"
        const val ARG_LANGUAGE = "arg_language_item"
        const val TAG_SELECT_LANGUAGE_DIALOG = "TAG_SelectLanguageDialog"
    }
}