package ua.oshevchuk.test.ui.mainUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.launch
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
class MainViewModel @Inject constructor(private val api: Api, private val realm:Realm) : ViewModel() {
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
                        val userRo = UserRO()
                        userRo.userName = it[i].login
                        userRo.id = i.toString()
                        userRo.imageURL = it[i].avatar_url
                        realm.executeTransaction { transactionRealm ->
                            transactionRealm.insertOrUpdate(userRo)
                        }
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
        val tasks : RealmResults<UserRO> = realm.where<UserRO>().findAll()
        val array = ArrayList<UserModel>()
        tasks.forEach{
            array.add(
                UserModel(
                    login = it.userName,
                    avatar_url = it.imageURL,
                    id = it.id.toInt(),
                    changesCounter = it.changesCounter
            )
            )
        }

        users.postValue(array)
    }
    fun getUsersWithChangesCounter(changesCounter:Int, id:String){
        val result = realm.where<UserRO>().equalTo("id",id).findFirst()
        val userRO = UserRO()
        result?.let {
            userRO.userName = it.userName
            userRO.changesCounter = changesCounter
            userRO.imageURL = it.imageURL
        }
        result?.deleteFromRealm()
        realm.executeTransaction {
                transactionRealm ->
            transactionRealm.insertOrUpdate(userRO)
        }
        getUserList()
    }
}