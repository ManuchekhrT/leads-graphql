package tj.task.graphqlleadsapp.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import tj.task.graphqlleadsapp.data.datasouce.remote.LeadsRemoteDataSource
import tj.task.graphqlleadsapp.data.repository.LeadsRepositoryImpl
import tj.task.graphqlleadsapp.domain.repository.LeadsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideLeadsRemoteDataSource(
        apolloClient: ApolloClient,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) = LeadsRemoteDataSource(apolloClient, coroutineDispatcher)

    @Singleton
    @Provides
    fun provideLeadsRepository(
        leadsRemoteDataSource: LeadsRemoteDataSource
    ): LeadsRepository = LeadsRepositoryImpl(leadsRemoteDataSource)

}