package tj.task.graphqlleadsapp.data.datasouce.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tj.task.*

class LeadsRemoteDataSource(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchLeads() = withContext(ioDispatcher) {
        apolloClient.query(LeadsQuery()).execute()
    }

    suspend fun fetchLead(leadId: Int) = withContext(ioDispatcher) {
        apolloClient.query(LeadQuery(leadId)).execute()
    }

    suspend fun fetchLeadStatusTypes() = withContext(ioDispatcher) {
        apolloClient.query(LeadStatusTypesQuery()).execute()
    }

    suspend fun fetchLeadIntentionTypes() = withContext(ioDispatcher) {
        apolloClient.query(LeadIntentionTypesQuery()).execute()
    }

    suspend fun fetchCountries() = withContext(ioDispatcher) {
        apolloClient.query(CountriesQuery()).execute()
    }

    suspend fun fetchLanguages() = withContext(ioDispatcher) {
        apolloClient.query(LanguagesQuery()).execute()
    }

    suspend fun fetchCities(countryId: Int) = withContext(ioDispatcher) {
        apolloClient.query(CitiesQuery(countryId)).execute()
    }

    suspend fun createLead(param: CreateLeadParam) = withContext(ioDispatcher) {
        apolloClient.mutation(
            CreateLeadMutation(
                firstName = param.firstName,
                lastName = Optional.present(param.lastName),
                intentionId = param.intentionId,
                countryId = Optional.present(param.countryId),
                cityId = Optional.present(param.cityId),
                languageIds = param.languageIds,
                contacts = param.contacts.map { contactInput ->
                    contactInput.toContactDataInput()
                },
                leadSourceId = Optional.present(param.leadSourceId)
            )
        ).execute()
    }

    suspend fun fetchLeadSources() = withContext(ioDispatcher) {
        apolloClient.query(LeadSourcesQuery()).execute()
    }
}