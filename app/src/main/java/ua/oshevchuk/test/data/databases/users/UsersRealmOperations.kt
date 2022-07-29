package ua.oshevchuk.test.data.databases.users

import android.system.Os.open
import com.bumptech.glide.disklrucache.DiskLruCache.open
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import ua.oshevchuk.test.models.users.UserModel
import ua.oshevchuk.test.models.users.UserRO
import java.nio.channels.AsynchronousSocketChannel.open

/**
 * @author shevsan on 29.07.2022
 */
open class UsersRealmOperations {
    private val config = RealmConfiguration.Builder(schema = setOf(UserRO::class)).build()
    private val realm = Realm.open(config)

    suspend fun updateOrCreateUser(
        username: String,
        imageUrl: String,
        id: String
    ) {
        realm.write {
            val user: UserRO? = query<UserRO>("id == $0", id).first().find()
            if (user != null) {
                user.userName = username
                user.imageURL = imageUrl
            } else {
                this.copyToRealm(UserRO().apply {
                    this.id = id
                    this.userName = username
                    this.imageURL = imageUrl
                    this.changesCounter = 0
                })
            }
        }
    }

    fun updateUserChanges(
        id: String,
        changesCount: Int
    ) {
        realm.writeBlocking {
            val user: UserRO? = query<UserRO>("id == $0", id).first().find()
            user?.apply {
                this.changesCounter = changesCount
            }
        }
    }


    fun getUsers(): ArrayList<UserModel> {
        val users = arrayListOf<UserModel>()
        val tasks: RealmResults<UserRO> = realm.query<UserRO>().find()
        val temp = ArrayList<UserModel>()
        tasks.forEach { user ->
            temp.add(
                UserModel(
                    login = user.userName,
                    avatar_url = user.imageURL,
                    id = user.id.toInt(),
                    changesCounter = user.changesCounter
                )
            )
        }
        temp.forEach{
            users.add(it)
        }
        return users
    }

}