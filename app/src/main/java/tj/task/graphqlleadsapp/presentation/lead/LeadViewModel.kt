package tj.task.graphqlleadsapp.presentation.lead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.domain.model.DetailedLead
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType
import tj.task.graphqlleadsapp.domain.model.LeadStatusType
import tj.task.graphqlleadsapp.domain.usecases.*
import javax.inject.Inject

@HiltViewModel
class LeadViewModel @Inject constructor(
    private val fetchLeadIntentionTypesUseCase: FetchLeadIntentionTypesUseCase,
    private val fetchLeadStatusTypesUseCase: FetchLeadStatusTypesUseCase,
    private val fetchLeadUseCase: FetchLeadUseCase
) : ViewModel() {

    private val _leadState = MutableStateFlow(LeadState())
    val leadState = _leadState.asStateFlow()

    private val _leadStatusTypesState = MutableStateFlow(LeadStatusTypesState())
    val leadStatusTypesState = _leadStatusTypesState.asStateFlow()

    private val _leadIntentionTypesState = MutableStateFlow(LeadIntentionTypesState())
    val leadIntentionTypesState = _leadIntentionTypesState.asStateFlow()

    fun fetchDetailedLead(leadId: Int) {
        viewModelScope.launch {
            _leadState.update {
                it.copy(isLoading = true)
            }
            _leadState.update {
                it.copy(
                    detailedLead = fetchLeadUseCase.execute(LeadUseCaseParam(leadId)),
                    isLoading = false
                )
            }
        }
    }

    fun fetchLeadStatusTypes() {
        viewModelScope.launch {
            _leadStatusTypesState.update {
                it.copy(isLoading = true)
            }
            _leadStatusTypesState.update {
                it.copy(
                    leadStatusTypeList = fetchLeadStatusTypesUseCase.execute(Unit),
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

data class LeadState(
    val detailedLead: DetailedLead? = null,
    val isLoading: Boolean = false
)

data class LeadStatusTypesState(
    val leadStatusTypeList: List<LeadStatusType> = emptyList(),
    val isLoading: Boolean = false
)

data class LeadIntentionTypesState(
    val leadIntentionTypeList: List<LeadIntentionType> = emptyList(),
    val isLoading: Boolean = false
)