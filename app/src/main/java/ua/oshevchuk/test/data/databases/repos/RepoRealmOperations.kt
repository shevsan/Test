package ua.oshevchuk.test.data.databases.repos

import android.util.Log
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
    private val cfg = RealmConfiguration.Builder(schema = setOf(RepoRO::class)).build()
    private val realm = Realm.open(cfg)

    suspend fun updateOrCreateRepo(repoTitle: String, lang: String?, starring: Int, url: String, username: String)
    {
        realm.write {
            val user: RepoRO? = query<RepoRO>("html_url == $0", url).first().find()
            if (user != null)
            {
                user.name = repoTitle
                user.language = lang
                user.stargazers_count = starring
                user.html_url = url
            } else {
                copyToRealm(RepoRO().apply
                {
                    this.name = repoTitle
                    this.language = lang
                    this.stargazers_count = starring
                    this.html_url = url
                    this.username = username
                })
            }
        }
    }

    fun getRepos(login: String): ArrayList<RepositoryModel>
    {
        val tasks: RealmResults<RepoRO> = realm.query<RepoRO>("username == $0", login).find()
        Log.d("mytag",tasks.toString())
        val temp = ArrayList<RepositoryModel>()
        tasks.forEach { repo ->
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


        return temp
    }
}