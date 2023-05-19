package tj.task.graphqlleadsapp.domain.mapper

import tj.task.LeadsQuery
import tj.task.graphqlleadsapp.domain.model.*

class LeadsMapper : Mapper<Lead, LeadsQuery.Data1>() {

    override fun dtoToDomain(dto: LeadsQuery.Data1?): Lead {
        return Lead(
            id = dto?.id,
            personId = dto?.personId,
            avatar = prepareAvatar(dto?.avatar),
            country = prepareCountry(dto?.country),
            adSource = prepareAdSource(dto?.adSource),
            status = prepareStatus(dto?.status),
            intention = prepareIntention(dto?.intention),
            channelSource = prepareChannelSource(dto?.channelSource),
            displayName = dto?.displayName,
            createdAt = dto?.createdAt,
            updatedAt = dto?.updatedAt
        )
    }

    private fun prepareAvatar(avatar: LeadsQuery.Avatar?): Avatar? {
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

    private fun prepareCountry(country: LeadsQuery.Country?): Country? {
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

    private fun prepareAdSource(adSource: LeadsQuery.AdSource?): AdSource? {
        return adSource?.let { AdSource(it.id, it.title) }
    }

    private fun prepareStatus(status: LeadsQuery.Status?): Status? {
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

    private fun prepareIntention(intention: LeadsQuery.Intention?): Intention? {
        return intention?.let { Intention(it.id, it.title) }
    }

    private fun prepareChannelSource(channelSource: LeadsQuery.ChannelSource?): ChannelSource? {
        return channelSource?.let { ChannelSource(it.id, it.title) }
    }
}