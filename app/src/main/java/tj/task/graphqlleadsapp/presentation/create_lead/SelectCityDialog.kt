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
import tj.task.graphqlleadsapp.databinding.DialogSelectCityBinding
import tj.task.graphqlleadsapp.domain.model.City

@AndroidEntryPoint
class SelectCityDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSelectCityBinding

    private val viewModel: CreateNewLeadViewModel by viewModels()
    private var selectCityAdapter: SelectCityAdapter? = null

    private var countryId: Int = 0
    private var cities = mutableListOf<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countryId = it.getInt(ARG_COUNTRY_ID)
        }
    }

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

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSelectCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCities(countryId)

        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        selectCityAdapter = SelectCityAdapter {
            onSelectCityItemClick(it)
        }
        rvCities.adapter = selectCityAdapter

        textInputSearch.doAfterTextChanged { text ->
            if (text.toString().isNotBlank()) {
                selectCityAdapter?.filter(text.toString())
            } else {
                selectCityAdapter?.submitList(cities)
            }
        }
    }

    private fun onSelectCityItemClick(item: City) {
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_CITY to item))
        dismiss()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.citiesState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.rvCities.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.rvCities.isVisible = true
                        if (it.cities.isNotEmpty()) {
                            prepareCities(it.cities)
                        }
                    }
                }
            }
        }
    }

    private fun prepareCities(cities: List<City>) {
        selectCityAdapter?.submitList(cities)
    }

    companion object {
        const val REQUEST_KEY = "select_city_key"
        const val ARG_CITY = "arg_city_item"
        const val ARG_COUNTRY_ID = "arg_country_id"
        const val TAG_SELECT_CITY_DIALOG = "TAG_SelectCityDialog"

        fun newInstance(countryId: Int) = SelectCityDialog().apply {
            arguments = Bundle().apply {
                putInt(ARG_COUNTRY_ID, countryId)
            }
        }
    }
}