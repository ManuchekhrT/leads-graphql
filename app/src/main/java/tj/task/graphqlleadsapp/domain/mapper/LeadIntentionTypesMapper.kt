package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LeadIntentionTypesQuery
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType

class LeadIntentionTypesMapper :
    Mapper<LeadIntentionType, LeadIntentionTypesQuery.FetchLeadIntentionType>() {

    override fun dtoToDomain(dto: LeadIntentionTypesQuery.FetchLeadIntentionType?): LeadIntentionType {
        return LeadIntentionType(
            id = dto?.id ?: 0,
            title = dto?.title ?: ""
        )
    }
}