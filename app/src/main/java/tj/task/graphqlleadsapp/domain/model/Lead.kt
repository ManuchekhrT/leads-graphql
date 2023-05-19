package tj.task.graphqlleadsapp.domain.model

data class Lead(
    val id: Int?,
    val personId: Int?,
    val avatar: Avatar?,
    val country: Country?,
    val adSource: AdSource?,
    val status: Status?,
    val intention: Intention?,
    val channelSource: ChannelSource?,
    val displayName: String?,
    val createdAt: Any?,
    val updatedAt: Any?,
)

data class Avatar(
    val id: Int,
     val name: String,
     val mimeType: String,
     val size: Int,
     val resolution: String?,
     val thumbnail: String?,
     val path: String,
)

data class Country(
    val id: Int,
    val emoji: String?,
    val adWordsCode: Int?,
    val title: String,
    val phoneCode: String,
    val shortCode1: String,
    val shortCode2: String
) : java.io.Serializable

data class AdSource(
    val id: Int,
    val title: String,
)

data class Status(
    val backgroundColor: String,
    val color: String,
    val id: Int,
    val step: Int,
    val stepsCount: Int,
    val title: String,
)

data class Intention(
    val id: Int,
    val title: String,
)

data class ChannelSource(
    val id: Int,
    val title: String
)
