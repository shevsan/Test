package ua.oshevchuk.test.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Singleton
    @Provides
    fun provideRealm(@ApplicationContext context: Context): Realm {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }
}