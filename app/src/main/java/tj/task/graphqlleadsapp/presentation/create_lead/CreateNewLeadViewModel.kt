package tj.task.graphqlleadsapp.presentation.create_lead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.domain.model.*
import tj.task.graphqlleadsapp.domain.usecases.*
import tj.task.graphqlleadsapp.presentation.lead.LeadIntentionTypesState
import javax.inject.Inject

@HiltViewModel
class CreateNewLeadViewModel @Inject constructor(
    private val fetchLanguagesUseCase: FetchLanguagesUseCase,
    private val fetchCountriesUseCase: FetchCountriesUseCase,
    private val fetchLeadIntentionTypesUseCase: FetchLeadIntentionTypesUseCase,
    private val fetchCitiesUseCase: FetchCitiesUseCase,
    private val createLeadUseCase: CreateLeadUseCase,
    private val fetchLeadSourcesUseCase: FetchLeadSourcesUseCase
) : ViewModel() {

    private val _countriesState = MutableStateFlow(CountriesState())
    val countriesState = _countriesState.asStateFlow()

    private val _languagesState = MutableStateFlow(LanguagesState())
    val languagesState = _languagesState.asStateFlow()

    private val _citiesState = MutableStateFlow(CitiesState())
    val citiesState = _citiesState.asStateFlow()

    private val _sourcesState = MutableStateFlow(SourcesState())
    val sourcesState = _sourcesState.asStateFlow()

    private val _leadIntentionTypesState = MutableStateFlow(LeadIntentionTypesState())
    val leadIntentionTypesState = _leadIntentionTypesState.asStateFlow()

    private val _createdLeadState = MutableStateFlow(CreatedLeadState())
    val createdLeadState = _createdLeadState.asStateFlow()

    fun createLead(param: CreateNewLead) {
        viewModelScope.launch {
            _createdLeadState.update {
                it.copy(isLoading = true)
            }
            _createdLeadState.update {
                it.copy(
                    createdLead =  createLeadUseCase.execute(param),
                    isLoading = false
                )
            }
        }
    }


    fun fetchLeadSources() {
        viewModelScope.launch {
            _sourcesState.update {
                it.copy(isLoading = true)
            }
            _sourcesState.update {
                it.copy(
                    sources = fetchLeadSourcesUseCase.execute(Unit),
                    isLoading = false
                )
            }
        }
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _countriesState.update {
                it.copy(isLoading = true)
            }
            _countriesState.update {
                it.copy(
                    countries = fetchCountriesUseCase.execute(Unit),
                    isLoading = false
                )
            }
        }
    }

    fun fetchCities(countryId: Int) {
        viewModelScope.launch {
            _citiesState.update {
                it.copy(isLoading = true)
            }
            _citiesState.update {
                it.copy(
                    cities = fetchCitiesUseCase.execute(countryId),
                    isLoading = false
                )
            }
        }
    }


    fun fetchLanguages() {
        viewModelScope.launch {
            _languagesState.update {
                it.copy(isLoading = true)
            }
            _languagesState.update {
                it.copy(
                    languages = fetchLanguagesUseCase.execute(Unit),
                    isLoading = false
                )
            }
        }
    }

    fun fetchLeadIntentionTypes() {
        viewModelScope.launch {
            _leadIntentionTypesState.update {
                it.copy(isLoading = true)
            }
            _leadIntentionTypesState.update {
                it.copy(
                    leadIntentionTypeList = fetchLeadIntentionTypesUseCase.execute(Unit),
                    isLoading = false
                )
            }
        }
    }
}

data class CountriesState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false
)

data class CitiesState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false
)

data class SourcesState(
    val sources: List<LeadSource> = emptyList(),
    val isLoading: Boolean = false
)
data class CreatedLeadState(
    val createdLead: CreatedLead? = null,
    val isLoading: Boolean = false
)

data class LanguagesState(
    val languages: List<Language> = emptyList(),
    val isLoading: Boolean = false
)