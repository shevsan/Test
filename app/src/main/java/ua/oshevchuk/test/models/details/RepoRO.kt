package ua.oshevchuk.test.models.details

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * @author shevsan on 29.07.2022
 */
open class RepoRO: RealmObject{
    @PrimaryKey
    val name: String= ""
    val html_url: String = ""
    val language: String?= ""
    val stargazers_count: Int= 0
    val username: String= ""
}
