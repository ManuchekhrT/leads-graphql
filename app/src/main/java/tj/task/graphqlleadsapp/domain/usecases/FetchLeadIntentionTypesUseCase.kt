package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LeadIntentionTypesMapper
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchLeadIntentionTypesUseCase : UseCase<Unit, List<LeadIntentionType>>

class FetchLeadIntentionTypesUseCaseImpl @Inject constructor(
    private val mapper: LeadIntentionTypesMapper,
    private val repository: LeadsRepository
) : FetchLeadIntentionTypesUseCase  {

    override suspend fun execute(param: Unit): List<LeadIntentionType> {
        return repository.fetchLeadIntentionTypes()?.map(mapper::dtoToDomain) ?: listOf()
    }
}