package ua.oshevchuk.test.data.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ua.oshevchuk.test.models.details.RepositoryModel
import ua.oshevchuk.test.models.users.UserModel

/**
 * @author shevsan on 28.07.2022
 */
interface Api
{
    @GET("./users")
    suspend fun getUsersList(): Response<List<UserModel>>
    @GET("/users/{username}/repos")
    suspend fun getUsersRepos(@Path("username") username: String): Response<List<RepositoryModel>>
}