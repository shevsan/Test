package ua.oshevchuk.test.ui.mainUsers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.models.users.UserModel
import java.io.IOException
import javax.inject.Inject

/**
 * @author shevsan on 28.07.2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val users = MutableLiveData<List<UserModel>>()
    init {
        getUserList()
    }




    fun getUserList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = api.getUsersList()
                Log.d("mytag", "${result.code().toString()}${result.body().toString()}")
                users.value = result.body()
            }

        }
    }

    fun getUsers(): LiveData<List<UserModel>> {
        return users
    }
}