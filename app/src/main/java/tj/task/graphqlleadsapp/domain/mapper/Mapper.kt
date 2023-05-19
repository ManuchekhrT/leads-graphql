package tj.task.graphqlleadsapp.domain.mapper

abstract class Mapper<Domain, Dto> {
    // dto-domain mappings, mandatory to implement
    abstract fun dtoToDomain(dto: Dto?): Domain

    // abstract fun domainToDto(domain: Domain?): Dto = d

    // dto-domain list mapping support
    fun dtoToDomain(dtoList: List<Dto>?): List<Domain> = (dtoList ?: emptyList()).map(::dtoToDomain)
   // fun domainToDto(domainList: List<Domain>?): List<Dto> = (domainList?: emptyList()).map(::domainToDto)
}