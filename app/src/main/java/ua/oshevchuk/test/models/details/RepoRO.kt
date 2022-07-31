package ua.oshevchuk.test.models.details

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 * @author shevsan on 29.07.2022
 */
open class RepoRO : RealmObject() {
    @PrimaryKey
    var html_url: String = ""
    var name: String = ""
    var language: String? = ""
    var stargazers_count: Int = 0
    var username: String = ""
}
