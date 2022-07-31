package ua.oshevchuk.test.ui.mainUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.launch
import ua.oshevchuk.test.data.databases.users.UsersRealmOperations
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.models.users.UserModel
import ua.oshevchuk.test.models.users.UserRO
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * @author shevsan on 28.07.2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val realmOperations = UsersRealmOperations()
    private var realmUsers = ArrayList<UserModel>()
    private val users = MutableLiveData<List<UserModel>>()
    init {
        getUsersFromDB()

    }




    fun getUserList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = api.getUsersList()
                val temp  = result.body()
                users.value =temp
                temp?.let {
                    for (i in 0 until it.size) {
                        realmOperations.createUser(
                            login = it[i].login,
                            image = it[i].avatar_url,
                            id = i.toString()
                        )
                    }
                }
                getUsersFromDB()

            }.onFailure {
                getUsersFromDB()
            }


        }
    }

    fun getUsers(): LiveData<List<UserModel>> {
        return users
    }
    fun getUsersFromDB(){
        realmUsers =UsersRealmOperations().getUsers()
        users.postValue(realmUsers)
    }
    fun getUsersWithChangesCounter(){
        val cfg = RealmConfiguration.Builder(schema = setOf(UserRO::class)).build()
        val realm = Realm.open(cfg)
        val arrayList = arrayListOf<UserModel>()
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
            arrayList.add(it)
        }
        users.value = arrayList

    }
}