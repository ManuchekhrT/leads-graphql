package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LeadQuery
import tj.task.graphqlleadsapp.domain.model.*

class LeadMapper : Mapper<DetailedLead, LeadQuery.Data1>() {

    override fun dtoToDomain(dto: LeadQuery.Data1?): DetailedLead {
        return DetailedLead(
            id = dto?.id,
            personId = dto?.personId,
            displayName = dto?.displayName,
            avatar = prepareAvatar(dto?.avatar),
            contacts = prepareContacts(dto?.contacts),
            status = prepareStatus(dto?.status),
            intention = prepareIntention(dto?.intention),
            adSource = prepareAdSource(dto?.adSource),
            country = prepareCountry(dto?.country),
            webSource = prepareWebSource(dto?.webSource),
            city = prepareCity(dto?.city),
            channelSource = prepareChannelSource(dto?.channelSource),
            languages = prepareLanguages(dto?.languages),
            propertyType = preparePropertyType(dto?.propertyType),
            nationality = prepareNationality(dto?.nationality),
            adTags = prepareAdTags(dto?.adTags),
            birthDate = dto?.birthDate,
            budget = dto?.budget
        )
    }

    private fun prepareAvatar(avatar: LeadQuery.Avatar?): Avatar? {
        return avatar?.let {
            Avatar(
                id = it.id,
                name = it.name,
                mimeType = it.mimeType,
                size = it.size,
                resolution = it.resolution,
                thumbnail = it.thumbnail,
                path = it.path
            )
        }
    }

    private fun prepareContacts(contacts: LeadQuery.Contacts?): List<Contact> {
        return contacts?.let { aContact ->
            aContact.data.map {
                Contact(
                    emailContact = prepareEmailContact(it.emailContact),
                    id = it.id,
                    phoneContact = preparePhoneContact(it.phoneContact),
                    sessionContact = prepareSessionContact(it.sessionContact),
                    type = prepareType(it.type),
                    typeId = it.typeId,
                    editable = it.editable
                )
            }
        } ?: listOf()
    }

    private fun prepareEmailContact(emailContact: LeadQuery.EmailContact?): EmailContact? {
        return emailContact?.let {
            EmailContact(
                contactId = it.contactId,
                email = it.email,
                title = it.title
            )
        }
    }

    private fun preparePhoneContact(phoneContact: LeadQuery.PhoneContact?): PhoneContact? {
        return phoneContact?.let {
            PhoneContact(
                phone = it.phone,
                title = it.title,
                contactId = it.contactId,
                color = it.color,
                telegramId = it.telegramId,
                telegramUsername = it.telegramUsername
            )
        }
    }

    private fun prepareSessionContact(sessionContact: LeadQuery.SessionContact?): SessionContact? {
        return sessionContact?.let {
            SessionContact(
                contactId = it.contactId,
                sessionId = it.sessionId
            )
        }
    }

    private fun prepareType(type: LeadQuery.Type?): Type? {
        return type?.let {
            Type(id = it.id, title = it.title)
        }
    }

    private fun prepareCountry(country: LeadQuery.Country?): Country? {
        return country?.let {
            Country(
                id = it.id,
                emoji = it.emoji,
                adWordsCode = it.adWordsCode,
                title = it.title,
                phoneCode = it.phoneCode,
                shortCode1 = it.shortCode1,
                shortCode2 = it.shortCode2
            )
        }
    }

    private fun prepareAdSource(adSource: LeadQuery.AdSource?): AdSource? {
        return adSource?.let { AdSource(it.id, it.title) }
    }

    private fun prepareStatus(status: LeadQuery.Status?): Status? {
        return status?.let {
            Status(
                backgroundColor = it.backgroundColor,
                color = it.color,
                id = it.id,
                step = it.step,
                stepsCount = it.stepsCount,
                title = it.title
            )
        }
    }

    private fun prepareIntention(intention: LeadQuery.Intention?): Intention? {
        return intention?.let { Intention(it.id, it.title) }
    }

    private fun prepareWebSource(webSource: LeadQuery.WebSource?): WebSource? {
        return webSource?.let { WebSource(it.id, it.title, it.url) }
    }

    private fun prepareChannelSource(channelSource: LeadQuery.ChannelSource?): ChannelSource? {
        return channelSource?.let { ChannelSource(it.id, it.title) }
    }

    private fun prepareLanguages(languages: List<LeadQuery.Language>?): List<Language> {
        return languages?.let { listOfLeadQueryLanguage ->
            listOfLeadQueryLanguage.map { aLeadQueryLanguage ->
                Language(
                    id = aLeadQueryLanguage.id,
                    title = aLeadQueryLanguage.title,
                    shortCode = aLeadQueryLanguage.shortCode
                )
            }
        } ?: listOf()
    }

    private fun prepareCity(city: LeadQuery.City?): City? {
        return city?.let {
            City(
                countryId = it.countryId,
                id = it.id,
                offset = it.offset,
                offsetMs = it.offsetMs,
                timezone = it.timezone,
                title = it.title
            )
        }
    }

    private fun preparePropertyType(propertyType: LeadQuery.PropertyType?): PropertyType? {
        return propertyType?.let { PropertyType(id = it.id, title = it.title) }
    }

    private fun prepareNationality(nationality: LeadQuery.Nationality?): Nationality? {
        return nationality?.let { Nationality(id = it.id, title = it.title, countryId = it.countryId) }
    }

    private fun prepareAdTags(adTags: List<LeadQuery.AdTag>?): List<AdTag> {
        return adTags?.let { listOfLeadQueryAdTag ->
            listOfLeadQueryAdTag.map { aLeadQueryAdTag ->
                AdTag(
                    id = aLeadQueryAdTag.id,
                    conditional = aLeadQueryAdTag.conditional,
                    field = aLeadQueryAdTag.field,
                    key = aLeadQueryAdTag.key,
                    value = aLeadQueryAdTag.value,
                    category = prepareCategory(aLeadQueryAdTag.category)
                )
            }
        } ?: listOf()
    }

    private fun prepareCategory(category: LeadQuery.Category?): Category? {
        return category?.let { Category(id = it.id, title = it.title, color = it.color) }
    }
}