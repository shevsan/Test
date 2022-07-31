package ua.oshevchuk.test.ui.detailsGitRepos

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
import ua.oshevchuk.test.models.details.RepoRO
import ua.oshevchuk.test.models.details.RepositoryModel
import javax.inject.Inject

/**
 * @author shevsan on 29.07.2022
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val api: Api, private val realm: Realm) :
    ViewModel() {
    private val repos = MutableLiveData<List<RepositoryModel>>()

    fun getReposFromApi(name: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = api.getUsersRepos(name)
                val temp = result.body()
                repos.value = temp
                temp?.let {
                    realm.executeTransaction {
                            transactionRealm ->

                        it.forEach {
                            val repoRo = RepoRO()
                            repoRo.username = it.username
                            repoRo.name = it.name
                            repoRo.language = it.language
                            repoRo.html_url = it.html_url
                            repoRo.stargazers_count = it.stargazers_count
                            transactionRealm.insertOrUpdate(repoRo)
                        }
                    }

                }
                getReposFromDB(name)
            }.onFailure {
                getReposFromDB(name)
            }


        }


    }

    fun getReposFromDB(username: String) {
        val tasks = realm.where<RepoRO>().equalTo("username", username).findAll()
        val array = ArrayList<RepositoryModel>()
        tasks.forEach {
            array.add(
                RepositoryModel(
                    name = it.name,
                    language = it.language,
                    stargazers_count = it.stargazers_count,
                    html_url = it.html_url,
                    username = it.username
                )
            )
        }
        if (array.size != 0)
            repos.postValue(array)
    }


    fun getRepos(): LiveData<List<RepositoryModel>> {
        return repos
    }


}