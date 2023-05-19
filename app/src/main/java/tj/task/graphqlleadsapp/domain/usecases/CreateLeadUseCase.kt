package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.CreateLeadMapper
import tj.task.graphqlleadsapp.domain.model.CreateNewLead
import tj.task.graphqlleadsapp.domain.model.CreatedLead
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface CreateLeadUseCase : UseCase<CreateNewLead, CreatedLead>

class CreateLeadUseCaseImpl @Inject constructor(
    private val mapper: CreateLeadMapper,
    private val repository: LeadsRepository
) : CreateLeadUseCase {

    override suspend fun execute(param: CreateNewLead): CreatedLead {
        return mapper.dtoToDomain(repository.createLead(param))
    }
}