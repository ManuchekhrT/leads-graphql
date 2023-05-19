package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LeadStatusTypesQuery
import tj.task.graphqlleadsapp.domain.model.LeadStatusType

class LeadStatusTypesMapper : Mapper<LeadStatusType, LeadStatusTypesQuery.FetchLeadStatusType>() {

    override fun dtoToDomain(dto: LeadStatusTypesQuery.FetchLeadStatusType?): LeadStatusType {
        return LeadStatusType(
            id = dto?.id,
            title = dto?.title,
            step = dto?.step,
            stepsCount = dto?.stepsCount,
            color = dto?.color,
            backgroundColor = dto?.backgroundColor,
            legacyColor = dto?.legacyColor
        )
    }
}