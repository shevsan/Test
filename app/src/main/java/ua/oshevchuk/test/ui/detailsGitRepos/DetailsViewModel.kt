package ua.oshevchuk.test.ui.detailsGitRepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.oshevchuk.test.data.databases.repos.RepoRealmOperations
import ua.oshevchuk.test.data.retrofit.Api
import ua.oshevchuk.test.models.details.RepositoryModel
import javax.inject.Inject

/**
 * @author shevsan on 29.07.2022
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val repos = MutableLiveData<List<RepositoryModel>>()
    private val repoOperations = RepoRealmOperations()
    private var reposRealm = ArrayList<RepositoryModel>()

    suspend fun getReposFromApi(name: String) {
        val result = api.getUsersRepos(name)
        val temp = result.body()
        repos.value = temp
        temp?.let {
            for (i in it.indices) {
                repoOperations.updateOrCreateRepo(
                    repoTitle = it[i].name,
                    lang = it[i].language,
                    starring = it[i].stargazers_count,
                    url = it[i].html_url,
                    username = name
                )
            }

            getReposFromDB(name)

        }


    }

    fun getReposFromDB(username: String) {
        reposRealm = repoOperations.getRepos(username)
        if(reposRealm.size!=0)
            repos.postValue(reposRealm)


    }

    fun getRepos(): LiveData<List<RepositoryModel>> {
        return repos
    }


}