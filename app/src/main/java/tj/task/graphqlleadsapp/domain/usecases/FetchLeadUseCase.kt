package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LeadMapper
import tj.task.graphqlleadsapp.domain.model.DetailedLead
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

data class LeadUseCaseParam(val leadId: Int)

interface FetchLeadUseCase : UseCase<LeadUseCaseParam, DetailedLead>

class FetchLeadUseCaseImpl @Inject constructor(
    private val mapper: LeadMapper,
    private val repository: LeadsRepository
) : FetchLeadUseCase {

    override suspend fun execute(param: LeadUseCaseParam): DetailedLead {
        return mapper.dtoToDomain(repository.fetchLead(param.leadId))
    }
}