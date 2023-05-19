package tj.task.graphqlleadsapp.presentation.create_lead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.FragmentCreateNewLeadBinding
import tj.task.graphqlleadsapp.domain.model.*
import tj.task.graphqlleadsapp.extension.makeEnabledButClickable
import tj.task.graphqlleadsapp.extension.serializable
import tj.task.graphqlleadsapp.extension.showDefaultText
import tj.task.graphqlleadsapp.extension.showOriginalText

@AndroidEntryPoint
class CreateNewLeadFragment : Fragment() {
    private var _binding: FragmentCreateNewLeadBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: CreateNewLeadViewModel by viewModels()

    private var cityId: Int = 0
    private var countryId: Int = 0
    private var leadSourceId: Int = 0
    private var languageIds = mutableListOf<Int>()
    private var intentionId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewLeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()

        setFragmentResultListener(SelectCountryDialog.REQUEST_KEY) { requestKey, bundle ->
            val country =
                bundle.serializable(SelectCountryDialog.ARG_COUNTRY) as? Country
            country?.let {
                countryId = it.id
                prepareCountry(it)
            }
        }

        setFragmentResultListener(SelectCityDialog.REQUEST_KEY) { requestKey, bundle ->
            val city =
                bundle.serializable(SelectCityDialog.ARG_CITY) as? City
            city?.let { aCity ->
                prepareCity(aCity)
            }
        }

        setFragmentResultListener(LeadIntentionTypesDialog.REQUEST_KEY) { requestKey, bundle ->
            val itemIntentionType =
                bundle.serializable(LeadIntentionTypesDialog.ARG_LEAD_INTENTION_TYPE) as? LeadIntentionType
            itemIntentionType?.let {
                prepareType(itemIntentionType)
            }
        }

        setFragmentResultListener(SelectLanguageDialog.REQUEST_KEY) { requestKey, bundle ->
            val languages =
                bundle.serializable(SelectLanguageDialog.ARG_LANGUAGE) as? List<Language>
            languages?.let {
                prepareLanguages(languages)
            }
        }

        setFragmentResultListener(SelectSourceDialog.REQUEST_KEY) { requestKey, bundle ->
            val source = bundle.serializable(SelectSourceDialog.ARG_SOURCE_ITEM) as? LeadSource
            source?.let {
                prepareSource(it)
            }
        }
    }

    private fun initView() = with(binding) {
        toolbarCreateNewLead.viewCreateNewLead.isVisible = false
        toolbarCreateNewLead.toolbarMain.title = resources.getString(R.string.lead_information)
        toolbarCreateNewLead.toolbarMain.setNavigationIcon(R.drawable.ic_arrow_left)
        toolbarCreateNewLead.toolbarMain.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnActionCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnActionSave.setOnClickListener {
            val firstName = binding.textInputEdittextFirstName.text.toString()
            val lastName = binding.textInputEtLastName.text.toString()
            val number = binding.textInputEdittextNumber.text.toString()
            val email = binding.textInputEdittextEmail.text.toString()
            var contacts: List<CreateContact> = emptyList()

            if (number.isNotBlank() && email.isNotBlank()) {
                contacts = listOf(
                    CreateContact(
                        email = email,
                        phone = number
                    )
                )
            }

            when {
                firstName.isBlank() -> {
                    binding.textInputLayoutFirstName.error = resources.getString(R.string.error_field)
                }
                intentionId == 0 -> {
                    binding.textInputLayoutLeadIntentionType.error = resources.getString(R.string.error_field)
                }
                languageIds.isEmpty() -> {
                    binding.textInputLayoutLanguage.error = resources.getString(R.string.error_field)
                }
                contacts.isEmpty() -> {
                    binding.textInputEdittextEmail.error = resources.getString(R.string.error_field)
                    binding.textInputEdittextNumber.error = resources.getString(R.string.error_field)
                }
                else -> {
                    binding.textInputLayoutFirstName.error = null
                    binding.textInputLayoutLeadIntentionType.error = null
                    binding.textInputLayoutLanguage.error = null
                    binding.textInputEdittextEmail.error = null
                    binding.textInputEdittextNumber.error = null

                    val aLead = CreateNewLead(
                        firstName = firstName,
                        lastName = lastName,
                        intentionId = intentionId,
                        countryId = countryId,
                        cityId = cityId,
                        languageIds = languageIds,
                        contacts = contacts,
                        leadSourceId = leadSourceId
                    )
                    viewModel.createLead(aLead)
                }
            }
        }

        binding.textInputEdittextLeadIntentionType.makeEnabledButClickable()
        binding.textInputEdittextLeadIntentionType.showDefaultText(resources.getString(R.string.type))
        binding.textInputEdittextLeadIntentionType.setOnClickListener {
            LeadIntentionTypesDialog().show(
                parentFragmentManager,
                LeadIntentionTypesDialog.TAG_LEAD_INTENTION_TYPE_DIALOG
            )
        }
        binding.textInputEdittextCountry.makeEnabledButClickable()
        binding.textInputEdittextCountry.showDefaultText(resources.getString(R.string.country))
        binding.textInputEdittextCountry.setOnClickListener {
            SelectCountryDialog().show(
                parentFragmentManager,
                SelectCountryDialog.TAG_SELECT_COUNTRY_DIALOG
            )
        }
        binding.textInputEtLanguage.makeEnabledButClickable()
        binding.textInputEtLanguage.showDefaultText(resources.getString(R.string.language))
        binding.textInputEtLanguage.setOnClickListener {
            SelectLanguageDialog().show(
                parentFragmentManager,
                SelectLanguageDialog.TAG_SELECT_LANGUAGE_DIALOG
            )
        }

        binding.textInputEdittextCity.makeEnabledButClickable()
        binding.textInputEdittextCity.showDefaultText(resources.getString(R.string.city))
        binding.textInputEdittextCity.setOnClickListener {
            SelectCityDialog.newInstance(countryId).show(
                parentFragmentManager,
                SelectCityDialog.TAG_SELECT_CITY_DIALOG
            )
        }

        binding.textInputEdittextSource.makeEnabledButClickable()
        binding.textInputEdittextSource.showDefaultText(resources.getString(R.string.select_source))
        binding.textInputEdittextSource.setOnClickListener {
            SelectSourceDialog().show(
                parentFragmentManager,
                SelectSourceDialog.TAG_SELECT_SOURCE_DIALOG
            )
        }

        binding.textInputEdittextFirstName.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.textInputLayoutFirstName.error = null
            }
        }
        binding.textInputEdittextLeadIntentionType.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.textInputLayoutLeadIntentionType.error = null
            }
        }
        binding.textInputEtLanguage.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.textInputLayoutLanguage.error = null
            }
        }
        binding.textInputEdittextNumber.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.textInputLayoutNumber.error = null
            }
        }
        binding.textInputEdittextEmail.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.textInputLayoutNumber.error = null
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createdLeadState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.scrollviewInputFields.isVisible = false
                        binding.llBtnsContainer.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.scrollviewInputFields.isVisible = true
                        binding.llBtnsContainer.isVisible = true
                        if (it.createdLead != null) {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun prepareCountry(aCountry: Country) {
        binding.textInputEdittextCountry.showOriginalText(aCountry.title)
    }

    private fun prepareType(type: LeadIntentionType) {
        binding.textInputEdittextLeadIntentionType.showOriginalText(type.title)
        intentionId = type.id
    }

    private fun prepareLanguages(languages: List<Language>) {
        val languageTitles = languages.map { it.title }
        val aLanguageIds = languages.map { it.id }
        binding.textInputEtLanguage.showOriginalText(languageTitles.joinToString(", "))
        languageIds.addAll(aLanguageIds)
    }

    private fun prepareCity(aCity: City) {
        binding.textInputEdittextCity.showOriginalText(aCity.title)
        cityId = aCity.id
    }

    private fun prepareSource(source: LeadSource) {
        binding.textInputEdittextSource.showOriginalText(source.title)
        leadSourceId = source.id
    }
}