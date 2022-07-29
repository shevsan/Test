package ua.oshevchuk.test.data.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import ua.oshevchuk.test.models.users.UserModel

/**
 * @author shevsan on 28.07.2022
 */
interface Api {
    @GET("./users")
    suspend fun getUsersList(): Response<List<UserModel>>
}