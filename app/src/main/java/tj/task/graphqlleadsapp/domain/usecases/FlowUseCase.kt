package tj.task.graphqlleadsapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import tj.task.graphqlleadsapp.domain.model.State

@Suppress("InjectDispatcher")
interface FlowUseCase<in Input, Output> {
    /**
     * Executes the flow on Dispatchers.IO and wraps exceptions inside it into Result
     */
    operator fun invoke(param: Input): Flow<State<Output>> =
        execute(param)
            .catch { e ->
                emit(State.Error(error = e, message = e.localizedMessage))
            }
            .flowOn(Dispatchers.IO)

    fun execute(param: Input): Flow<State<Output>>
}

interface UseCase<in Input, Output> {

    suspend operator fun invoke(param: Input): Output {
        return execute(param)
    }

    suspend fun execute(param: Input): Output
}