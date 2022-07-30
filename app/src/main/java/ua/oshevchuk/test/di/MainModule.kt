package ua.oshevchuk.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.utils.baseUrl
import javax.inject.Singleton

/**
 * @author shevsan on 28.07.2022
 */

@Module
@InstallIn(SingletonComponent::class)
object MainModule
{
    @Provides
    @Singleton
    fun retrofitProvide():Api
    {
       return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}