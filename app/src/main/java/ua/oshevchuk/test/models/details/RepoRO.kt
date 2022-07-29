package ua.oshevchuk.test.models.details

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * @author shevsan on 29.07.2022
 */
open class RepoRO: RealmObject{
    @PrimaryKey
    var html_url: String = ""
    var name: String= ""
    var language: String?= ""
    var stargazers_count: Int= 0
    var username: String= ""
}
