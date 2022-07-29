package ua.oshevchuk.test.ui.detailsGitRepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.models.details.RepositoryModel
import ua.oshevchuk.test.models.users.UserModel
import javax.inject.Inject

/**
 * @author shevsan on 29.07.2022
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val api:Api) : ViewModel() {
    private val repos = MutableLiveData<List<RepositoryModel>>()
    fun getReposFromApi(name: String) {
        viewModelScope.launch {
            kotlin.runCatching {
               val result =  api.getUsersRepos(name)
                repos.value = result.body()
            }
        }
    }
    fun getRepos(): LiveData<List<RepositoryModel>>{
        return repos
    }

}