package tj.task.graphqlleadsapp.domain.model

data class LeadIntentionType(
    val id: Int,
    val title: String,
    var isSelected: Boolean = false
) : java.io.Serializable