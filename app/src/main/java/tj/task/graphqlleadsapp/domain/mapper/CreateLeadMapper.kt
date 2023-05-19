package tj.task.graphqlleadsapp.domain.mapper

import tj.task.CreateLeadMutation
import tj.task.graphqlleadsapp.domain.model.CreatedLead

class CreateLeadMapper : Mapper<CreatedLead, CreateLeadMutation.Data1>() {

    override fun dtoToDomain(dto: CreateLeadMutation.Data1?): CreatedLead {
        return CreatedLead(
            id = dto?.id ?: 0,
            personId = dto?.personId ?: 0,
            displayName = dto?.displayName ?: ""
        )
    }
}