package ua.oshevchuk.test.ui.mainUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.oshevchuk.test.data.databases.users.UsersRealmOperations
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.models.users.UserModel
import javax.inject.Inject

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
                    for (i in it.indices) {
                        realmOperations.createUser(
                            login = temp[i].login,
                            image = temp[i].avatar_url,
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
}