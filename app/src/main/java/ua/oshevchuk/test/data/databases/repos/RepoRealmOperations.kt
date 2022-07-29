package ua.oshevchuk.test.data.databases.repos

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import ua.oshevchuk.test.models.details.RepoRO
import ua.oshevchuk.test.models.details.RepositoryModel

/**
 * @author shevsan on 29.07.2022
 */
open class RepoRealmOperations {
    private val config = RealmConfiguration.Builder(schema = setOf(RepoRO::class)).build()
    private val realm = Realm.open(config)

    suspend fun updateOrCreateRepo(
        repositoryName: String,
        programmingLanguage: String?,
        starCount: Int,
        url: String,
        username: String
    ) {
        realm.write {
            val user: RepoRO? = query<RepoRO>("url == $0", url).first().find()
            if (user != null) {
                user.name = repositoryName
                user.language = programmingLanguage
                user.stargazers_count = starCount
                user.html_url = url
            } else {
                copyToRealm(RepoRO().apply {
                    this.name = repositoryName
                    this.language = programmingLanguage
                    this.stargazers_count = starCount
                    this.html_url = url
                    this.username = username
                })
            }
        }
    }

    fun getRepos(
        username: String
    ): ArrayList<RepositoryModel> {
        val repoArray = ArrayList<RepositoryModel>()
        val tasks: RealmResults<RepoRO> = realm.query<RepoRO>("username == $0", username).find()
        val temp = ArrayList<RepositoryModel>()
        tasks.forEach { repo->
            temp.add(
                RepositoryModel(
                    name = repo.name,
                    language = repo.language,
                    stargazers_count = repo.stargazers_count,
                    html_url = repo.html_url,
                    username = repo.username
                )
            )
        }
        temp.forEach{
            repoArray.add(it)
        }

        return repoArray
    }
}