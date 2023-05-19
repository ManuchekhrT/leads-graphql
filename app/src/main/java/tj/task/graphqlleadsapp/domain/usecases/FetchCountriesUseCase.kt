package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.CountriesMapper
import tj.task.graphqlleadsapp.domain.model.Country
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchCountriesUseCase : UseCase<Unit, List<Country>>

class FetchCountriesUseCaseImpl @Inject constructor(
    private val mapper: CountriesMapper,
    private val repository: LeadsRepository
) : FetchCountriesUseCase  {

    override suspend fun execute(param: Unit): List<Country> {
        return repository.fetchCountries()?.map(mapper::dtoToDomain) ?: listOf()
    }
}