package ua.oshevchuk.test.data.databases.users

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import ua.oshevchuk.test.models.users.UserModel
import ua.oshevchuk.test.models.users.UserRO

/**
 * @author shevsan on 29.07.2022
 */
open class UsersRealmOperations {
    private val cfg = RealmConfiguration.Builder(schema = setOf(UserRO::class)).build()
    private val realm = Realm.open(cfg)

    suspend fun createUser(login: String, image: String, id: String
    )
    {
        realm.write {
            val user: UserRO? = query<UserRO>("id == $0", id).first().find()
            if (user != null) {
                user.userName = login
                user.imageURL = image
            } else {
                this.copyToRealm(UserRO().apply
                {
                    this.id = id
                    this.userName = login
                    this.imageURL = image
                    this.changesCounter = 0
                })
            }
        }
    }

    fun getUsers(): ArrayList<UserModel> {
        val cfg = RealmConfiguration.Builder(schema = setOf(UserRO::class)).build()
        val realm = Realm.open(cfg)
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
    suspend fun updateUser(id: String, counter: Int)
    {
        realm.write {
            val user: UserRO? = query<UserRO>("id == $0", id).first().find()
            user?.apply {
                this.changesCounter = counter
            }
        }
    }
}
