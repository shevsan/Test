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
import javax.inject.Inject

/**
 * @author shevsan on 28.07.2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val api: Api, private val realm: Realm) :
    ViewModel() {
    private val users = MutableLiveData<List<UserModel>>()

    init {
        getUsersFromDB()
        initObserver()
    }


    fun getUserList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = api.getUsersList()
                val temp = result.body()
                temp?.let {
                    realm.executeTransaction { transactionRealm ->
                        val modelsFromRealm = realm.where<UserRO>().findAll()
                        if (modelsFromRealm.isEmpty()) {
                            it.forEachIndexed { index, userModel ->
                                val userRo = UserRO()
                                userRo.userName = userModel.login
                                userRo.id = index
                                userRo.imageURL = userModel.avatar_url
                                transactionRealm.insertOrUpdate(userRo)
                            }
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

    fun getUsersFromDB() {
        val tasks: RealmResults<UserRO> = realm.where<UserRO>().findAll()
        val array = ArrayList<UserModel>()
        tasks.forEach {
            array.add(
                UserModel(
                    login = it.userName,
                    avatar_url = it.imageURL,
                    id = it.id,
                    changesCounter = it.changesCounter
                )
            )
        }

        users.postValue(array)
    }

    fun getUsersWithChangesCounter(changesCounter: Int, id: Int) {
        val result = realm.where<UserRO>().equalTo("id", id).findFirst()
        realm.executeTransaction {
            result?.let { userRO ->
                userRO.changesCounter = changesCounter
            }
        }
        getUsersFromDB()
    }

    private fun initObserver() {
        realm.addChangeListener {
            getUsersFromDB()
        }
    }
}