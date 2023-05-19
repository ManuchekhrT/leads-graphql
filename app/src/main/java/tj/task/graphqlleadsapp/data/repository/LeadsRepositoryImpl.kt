package tj.task.graphqlleadsapp.data.repository

import tj.task.*
import tj.task.graphqlleadsapp.data.datasouce.remote.LeadsRemoteDataSource
import tj.task.graphqlleadsapp.domain.model.CreateNewLead
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Inject

class LeadsRepositoryImpl @Inject constructor(
    private val dataSource: LeadsRemoteDataSource
) : LeadsRepository {

    override suspend fun fetchLeads(): List<LeadsQuery.Data1>? {
        return dataSource.fetchLeads().data?.fetchLeads?.data
    }

    override suspend fun fetchLead(id: Int): LeadQuery.Data1? {
        return dataSource.fetchLead(id).data?.fetchLead?.data
    }

    override suspend fun fetchLeadStatusTypes(): List<LeadStatusTypesQuery.FetchLeadStatusType>? {
        return dataSource.fetchLeadStatusTypes().data?.fetchLeadStatusTypes
    }

    override suspend fun fetchLeadIntentionTypes():
            List<LeadIntentionTypesQuery.FetchLeadIntentionType>? {
        return dataSource.fetchLeadIntentionTypes().data?.fetchLeadIntentionTypes
    }

    override suspend fun fetchCountries(): List<CountriesQuery.FetchCountry>? {
        return dataSource.fetchCountries().data?.fetchCountries
    }

    override suspend fun fetchLanguages(): List<LanguagesQuery.Language>? {
        return dataSource.fetchLanguages().data?.languages
    }

    override suspend fun fetchCities(countryId: Int): List<CitiesQuery.City>? {
        return dataSource.fetchCities(countryId).data?.cities
    }

    override suspend fun createLead(aLead: CreateNewLead): CreateLeadMutation.Data1? {
        return dataSource.createLead(aLead.toCreateLeadParam()).data?.createLead?.data
    }

    override suspend fun fetchLeadSources(): List<LeadSourcesQuery.FetchLeadSource>? {
        return dataSource.fetchLeadSources().data?.fetchLeadSources
    }
}