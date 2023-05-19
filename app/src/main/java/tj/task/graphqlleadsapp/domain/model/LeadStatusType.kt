package tj.task.graphqlleadsapp.domain.model

data class LeadStatusType(
    val id: Int?,
    val title: String?,
    val step: Int?,
    val stepsCount: Int?,
    val color: String?,
    val backgroundColor: String?,
    val legacyColor: String?
) : java.io.Serializable