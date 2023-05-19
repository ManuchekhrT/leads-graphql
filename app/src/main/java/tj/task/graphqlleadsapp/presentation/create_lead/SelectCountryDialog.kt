package tj.task.graphqlleadsapp.presentation.create_lead

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import tj.task.graphqlleadsapp.databinding.DialogSelectCountryBinding
import tj.task.graphqlleadsapp.domain.model.Country

@AndroidEntryPoint
class SelectCountryDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSelectCountryBinding

    private val viewModel: CreateNewLeadViewModel by viewModels()
    private var selectCountryAdapter: SelectCountryAdapter? = null

    private var countries = mutableListOf<Country>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                BottomSheetBehavior.from(parent).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

        }
        // Adjust window flags to show the dialog above the keyboard
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSelectCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCountries()
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        selectCountryAdapter = SelectCountryAdapter {
            onCountryItemClick(it)
        }
        rvCountries.adapter = selectCountryAdapter

        textInputSearch.doAfterTextChanged { text ->
            if (text.toString().isNotBlank()) {
                selectCountryAdapter?.filter(text.toString())
            } else {
                selectCountryAdapter?.submitList(countries)
            }
        }
    }

    private fun onCountryItemClick(item: Country) {
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_COUNTRY to item))
        dismiss()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countriesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.rvCountries.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.rvCountries.isVisible = true
                        if (it.countries.isNotEmpty()) {
                            prepareCountries(it.countries)
                        }
                    }
                }
            }
        }
    }

    private fun prepareCountries(countriesList: List<Country>) {
        countries.addAll(countriesList)
        selectCountryAdapter?.submitList(countries)
    }

    companion object {
        const val REQUEST_KEY = "select_country_key"
        const val ARG_COUNTRY = "arg_country_item"
        const val TAG_SELECT_COUNTRY_DIALOG = "TAG_SelectCountryDialog"
    }
}