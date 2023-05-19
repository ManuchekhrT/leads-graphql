package tj.task.graphqlleadsapp.data.datasouce.remote

import com.apollographql.apollo3.api.Optional
import tj.task.type.ContactDataInput

data class CreateLeadParam(
    val firstName: String,
    val lastName: String? = null,
    val intentionId: Int,
    val countryId: Int? = null,
    val cityId: Int? = null,
    val languageIds: List<Int> = emptyList(),
    val contacts: List<ContactInput> = emptyList(),
    val leadSourceId: Int? = null
)

data class ContactInput(
    val email: String? = null,
    val phone: String? = null,
    val telegramId: String? = null,
    val telegramUsername: String? = null
) {
    fun toContactDataInput(): ContactDataInput {
        return ContactDataInput(
            email = Optional.present(email),
            phone = Optional.present(phone),
            telegramId = Optional.present(telegramId),
            telegramUsername = Optional.present(telegramUsername)
        )
    }
}