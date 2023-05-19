package tj.task.graphqlleadsapp.domain.model

data class CreatedLead(
    val id: Int,
    val personId: Int,
    val displayName: String
)