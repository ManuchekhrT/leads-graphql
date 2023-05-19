package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LeadsMapper
import tj.task.graphqlleadsapp.domain.model.Lead
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchLeadsUseCase : UseCase<Unit, List<Lead>>

class FetchLeadsUseCaseImpl @Inject constructor(
    private val mapper: LeadsMapper,
    private val repository: LeadsRepository
) : FetchLeadsUseCase  {

    override suspend fun execute(param: Unit): List<Lead> {
        return repository.fetchLeads()?.map(mapper::dtoToDomain) ?: listOf()
    }
}