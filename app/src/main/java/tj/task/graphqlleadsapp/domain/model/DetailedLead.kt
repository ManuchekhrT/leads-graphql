package tj.task.graphqlleadsapp.domain.model

data class DetailedLead(
    val id: Int?,
    val personId: Int?,
    val displayName: String?,
    val avatar: Avatar?,
    val contacts: List<Contact> = listOf(),
    val status: Status?,
    val intention: Intention?,
    val adSource: AdSource?,
    val country: Country?,
    val webSource: WebSource?,
    val city: City?,
    val channelSource: ChannelSource?,
    val languages: List<Language> = listOf(),
    val propertyType: PropertyType?,
    val nationality: Nationality?,
    val adTags: List<AdTag> = listOf(),
    val birthDate: Any?,
    val budget: Double?,
)

data class Contact(
    val emailContact: EmailContact?,
    val id: Int,
    val phoneContact: PhoneContact?,
    val sessionContact: SessionContact?,
    val type: Type?,
    val typeId: Int,
    val editable: Boolean,
)

data class PhoneContact(
    val phone: String,
    val title: String,
    val telegramId: String?,
    val telegramUsername: String?,
    val contactId: Int,
    val color: String,
)

data class SessionContact(
    val contactId: Int,
    val sessionId: String,
)

data class Type(
    val id: Int,
    val title: String
)

data class EmailContact(
    val contactId: Int,
    val email: String,
    val title: String,
)

data class WebSource(
    val id: Int,
    val title: String,
    val url: String,
)

data class City(
    val countryId: Int,
    val id: Int,
    val offset: String,
    val offsetMs: Int,
    val timezone: String,
    val title: String,
) : java.io.Serializable


data class Language(
    val id: Int,
    val title: String,
    val shortCode: String,
    var isSelected: Boolean = false
) : java.io.Serializable

data class PropertyType(
    val id: Int,
    val title: String,
)

data class Nationality(
    val countryId: Int?,
    val id: Int,
    val title: String,
)

data class AdTag(
    val id: Int,
    val conditional: Boolean,
    val `field`: String?,
    val key: String,
    val `value`: String,
    val category: Category?,
)

data class Category(
    val id: Int,
    val title: String,
    val color: String?,
)