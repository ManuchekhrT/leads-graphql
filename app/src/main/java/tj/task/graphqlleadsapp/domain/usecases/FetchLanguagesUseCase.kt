package tj.task.graphqlleadsapp.domain.usecases

import tj.task.graphqlleadsapp.domain.mapper.LanguagesMapper
import tj.task.graphqlleadsapp.domain.model.Language
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

interface FetchLanguagesUseCase : UseCase<Unit, List<Language>>

class FetchLanguagesUseCaseImpl @Inject constructor(
    private val mapper: LanguagesMapper,
    private val repository: LeadsRepository
) : FetchLanguagesUseCase  {

    override suspend fun execute(param: Unit): List<Language> {
        return repository.fetchLanguages()?.map(mapper::dtoToDomain) ?: listOf()
    }
}