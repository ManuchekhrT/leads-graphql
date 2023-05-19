package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LeadStatusTypesMapper
import tj.task.graphqlleadsapp.domain.model.LeadStatusType
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchLeadStatusTypesUseCase : UseCase<Unit, List<LeadStatusType>>

class FetchLeadStatusTypesUseCaseImpl @Inject constructor(
    private val mapper: LeadStatusTypesMapper,
    private val repository: LeadsRepository
) : FetchLeadStatusTypesUseCase  {

    override suspend fun execute(param: Unit): List<LeadStatusType> {
        return repository.fetchLeadStatusTypes()?.map(mapper::dtoToDomain) ?: listOf()
    }
}