package tj.task.graphqlleadsapp.domain.mapper

import tj.task.CitiesQuery
import tj.task.graphqlleadsapp.domain.model.City

class CitiesMapper : Mapper<City, CitiesQuery.City>() {

    override fun dtoToDomain(dto: CitiesQuery.City?): City {
        return City(
            countryId = dto?.countryId ?: 0,
            id = dto?.id ?: 0,
            offset = dto?.offset ?: "",
            offsetMs = dto?.offsetMs ?: 0,
            timezone = dto?.timezone ?: "",
            title = dto?.title ?: ""
        )
    }
}