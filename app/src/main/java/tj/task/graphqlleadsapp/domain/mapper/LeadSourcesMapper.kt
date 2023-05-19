package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LeadSourcesQuery
import tj.task.graphqlleadsapp.domain.model.LeadSource

class LeadSourcesMapper : Mapper<LeadSource, LeadSourcesQuery.FetchLeadSource>() {

    override fun dtoToDomain(dto: LeadSourcesQuery.FetchLeadSource?): LeadSource {
        return LeadSource(
            id = dto?.id ?: 0,
            title = dto?.title ?: ""
        )
    }
}