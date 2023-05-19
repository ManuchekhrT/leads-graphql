package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LanguagesQuery
import tj.task.graphqlleadsapp.domain.model.Language

class LanguagesMapper :
    Mapper<Language, LanguagesQuery.Language>() {

    override fun dtoToDomain(dto: LanguagesQuery.Language?): Language {
        return Language(
            id = dto?.id ?: 0,
            title = dto?.title ?: "",
            shortCode = dto?.shortCode ?: ""
        )
    }

}