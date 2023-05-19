package tj.task.graphqlleadsapp.data.datasouce.remote

import okhttp3.Interceptor
import okhttp3.Response

const val JWT_TOKEN =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlSWQiOjE0MjQsImZJZCI6ImRqempLaEpjcFBNRnFBM0MiLCJzSWQiOjQ5LCJpYXQiOjE2ODM4MDA0NDUsImV4cCI6MTY4NTAxMDA0NX0._8HYLmn38zMS19vgfDrHNo4svllQENwYO8ekWfXKIYI"

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $JWT_TOKEN")
        return chain.proceed(request.build())
    }
}