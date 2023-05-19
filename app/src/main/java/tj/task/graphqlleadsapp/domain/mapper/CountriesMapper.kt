package tj.task.graphqlleadsapp.domain.mapper

import tj.task.CountriesQuery
import tj.task.graphqlleadsapp.domain.model.Country

class CountriesMapper :
    Mapper<Country, CountriesQuery.FetchCountry>() {

    override fun dtoToDomain(dto: CountriesQuery.FetchCountry?): Country {
        return Country(
            id = dto?.id ?: 0,
            emoji = dto?.emoji,
            adWordsCode = dto?.adWordsCode,
            title = dto?.title ?: "",
            phoneCode = dto?.phoneCode ?: "",
            shortCode1 = dto?.shortCode1 ?: "",
            shortCode2 = dto?.shortCode2 ?: ""
        )
    }
}