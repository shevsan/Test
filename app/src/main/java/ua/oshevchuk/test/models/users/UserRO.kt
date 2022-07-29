package ua.oshevchuk.test.models.users


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * @author shevsan on 29.07.2022
 */
open class UserRO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var userName: String = ""
    var changesCounter: Int = 0
    var imageURL: String = ""
}