package tj.task.graphqlleadsapp.domain.model

import tj.task.graphqlleadsapp.data.datasouce.remote.ContactInput
import tj.task.graphqlleadsapp.data.datasouce.remote.CreateLeadParam

data class CreateNewLead(
    var firstName: String,
    var lastName: String? = null,
    var intentionId: Int,
    var countryId: Int? = null,
    var cityId: Int? = null,
    var languageIds: List<Int> = emptyList(),
    var contacts: List<CreateContact> = emptyList(),
    var leadSourceId: Int? = null
) {
    fun toCreateLeadParam(): CreateLeadParam {
        return CreateLeadParam(
            firstName = firstName,
            lastName = lastName,
            intentionId,
            countryId, cityId,
            languageIds,
            contacts.map { it.toContactDataInput() },
            leadSourceId
        )
    }
}

data class CreateContact(
    var email: String? = null,
    var phone: String? = null,
    var telegramId: String? = null,
    var telegramUsername: String? = null
) {
    fun toContactDataInput(): ContactInput {
        return ContactInput(
            email = email,
            phone = phone,
            telegramId = phone,
            telegramUsername = telegramUsername
        )
    }
}