package ua.oshevchuk.test.data.databases.users

import com.bumptech.glide.disklrucache.DiskLruCache.open
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.where
import ua.oshevchuk.test.models.users.UserRO

/**
 * @author shevsan on 29.07.2022
 */
class UsersRealmOperations {
    val realmName: String = "Users"
    val config = RealmConfiguration.Builder()
        .allowQueriesOnUiThread(true)
        .allowWritesOnUiThread(true)
        .build()
    val realm = Realm.getInstance(config)
    val users:RealmResults<UserRO> =realm.where<UserRO>().findAll()
    fun createOrUpdateUser(username: String, imageUrl: String, id: String){
        val user = UserRO()
        user.id = id
        user.userName = username
        user.imageURL = imageUrl
        realm.executeTransaction {transactionRealm ->
            transactionRealm.insert(user)
        }
    }
    fun updateUser(id: String, changesCount: String){
        val user = users.where().equalTo("id",id)
        realm.executeTransaction {transactionRealm ->
//            transactionRealm.insertOrUpdate()
        }
    }

}