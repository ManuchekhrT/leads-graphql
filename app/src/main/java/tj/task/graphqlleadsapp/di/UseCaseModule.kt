package tj.task.graphqlleadsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.task.graphqlleadsapp.domain.mapper.*
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import tj.task.graphqlleadsapp.domain.usecases.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideFetchLeadsUseCase(
        mapper: LeadsMapper,
        repository: LeadsRepository
    ): FetchLeadsUseCase = FetchLeadsUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchLeadUseCase(
        mapper: LeadMapper,
        repository: LeadsRepository
    ): FetchLeadUseCase = FetchLeadUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchLeadStatusTypesUseCase(
        mapper: LeadStatusTypesMapper,
        repository: LeadsRepository
    ): FetchLeadStatusTypesUseCase = FetchLeadStatusTypesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchLeadIntentionTypesUseCase(
        mapper: LeadIntentionTypesMapper,
        repository: LeadsRepository
    ): FetchLeadIntentionTypesUseCase = FetchLeadIntentionTypesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchLanguagesUseCase(
        mapper: LanguagesMapper,
        repository: LeadsRepository
    ): FetchLanguagesUseCase = FetchLanguagesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchCountriesUseCase(
        mapper: CountriesMapper,
        repository: LeadsRepository
    ): FetchCountriesUseCase = FetchCountriesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchCitiesUseCase(
        mapper: CitiesMapper,
        repository: LeadsRepository
    ): FetchCitiesUseCase = FetchCitiesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideFetchLeadSourcesUseCase(
        mapper: LeadSourcesMapper,
        repository: LeadsRepository
    ): FetchLeadSourcesUseCase = FetchLeadSourcesUseCaseImpl(mapper, repository)

    @Singleton
    @Provides
    fun provideCreateLeadUseCase(
        mapper: CreateLeadMapper,
        repository: LeadsRepository
    ): CreateLeadUseCase = CreateLeadUseCaseImpl(mapper, repository)
}