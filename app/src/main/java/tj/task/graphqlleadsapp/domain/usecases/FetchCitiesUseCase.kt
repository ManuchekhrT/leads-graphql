package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.CitiesMapper
import tj.task.graphqlleadsapp.domain.model.City
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchCitiesUseCase : UseCase<Int, List<City>>

class FetchCitiesUseCaseImpl @Inject constructor(
    private val mapper: CitiesMapper,
    private val repository: LeadsRepository
) : FetchCitiesUseCase  {

    override suspend fun execute(countryId: Int): List<City> {
        return repository.fetchCities(countryId)?.map(mapper::dtoToDomain) ?: listOf()
    }
}