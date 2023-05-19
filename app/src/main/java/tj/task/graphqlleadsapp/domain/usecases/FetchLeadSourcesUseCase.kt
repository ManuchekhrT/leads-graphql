package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LeadSourcesMapper
import tj.task.graphqlleadsapp.domain.model.LeadSource
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchLeadSourcesUseCase : UseCase<Unit, List<LeadSource>>

class FetchLeadSourcesUseCaseImpl @Inject constructor(
    private val mapper: LeadSourcesMapper,
    private val repository: LeadsRepository
) : FetchLeadSourcesUseCase  {

    override suspend fun execute(param: Unit): List<LeadSource> {
        return repository.fetchLeadSources()?.map(mapper::dtoToDomain) ?: listOf()
    }
}