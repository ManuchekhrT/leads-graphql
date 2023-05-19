package tj.task.graphqlleadsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.task.graphqlleadsapp.domain.mapper.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideLeadsMapper() = LeadsMapper()

    @Singleton
    @Provides
    fun provideLeadMapper() = LeadMapper()

    @Singleton
    @Provides
    fun provideLeadIntentionTypesMapper() = LeadIntentionTypesMapper()

    @Singleton
    @Provides
    fun provideLeadStatusTypesMapper() = LeadStatusTypesMapper()

    @Singleton
    @Provides
    fun provideLanguagesMapper() = LanguagesMapper()

    @Singleton
    @Provides
    fun provideCountriesMapper() = CountriesMapper()

    @Singleton
    @Provides
    fun provideCitiesMapper() = CitiesMapper()

    @Singleton
    @Provides
    fun provideLeadSourcesMapper() = LeadSourcesMapper()

    @Singleton
    @Provides
    fun provideCreateLeadMapper() = CreateLeadMapper()
}