package tj.task.graphqlleadsapp.domain.repository

import tj.task.*
import tj.task.graphqlleadsapp.domain.model.CreateNewLead

interface LeadsRepository {
    suspend fun fetchLeads(): List<LeadsQuery.Data1>?
    suspend fun fetchLead(id: Int): LeadQuery.Data1?
    suspend fun fetchLeadStatusTypes(): List<LeadStatusTypesQuery.FetchLeadStatusType>?
    suspend fun fetchLeadIntentionTypes(): List<LeadIntentionTypesQuery.FetchLeadIntentionType>?
    suspend fun fetchCountries(): List<CountriesQuery.FetchCountry>?
    suspend fun fetchLanguages(): List<LanguagesQuery.Language>?
    suspend fun fetchCities(countryId: Int): List<CitiesQuery.City>?
    suspend fun createLead(aLead: CreateNewLead): CreateLeadMutation.Data1?
    suspend fun fetchLeadSources(): List<LeadSourcesQuery.FetchLeadSource>?
}