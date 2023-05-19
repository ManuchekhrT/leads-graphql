package tj.task.graphqlleadsapp.presentation.leads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.domain.model.Lead
import tj.task.graphqlleadsapp.domain.usecases.FetchLeadsUseCase
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val fetchLeadsUseCase: FetchLeadsUseCase
) : ViewModel() {

    private val _leadsState = MutableStateFlow(LeadsState())
    val leadsState = _leadsState.asStateFlow()


    fun fetchLeads() {
        viewModelScope.launch {
            _leadsState.update {
                it.copy(isLoading = true)
            }
            _leadsState.update {
                it.copy(
                    leads = fetchLeadsUseCase.execute(Unit),
                    isLoading = false
                )
            }
        }
    }
}

data class LeadsState(
    val leads: List<Lead> = emptyList(),
    val isLoading: Boolean = false
)
