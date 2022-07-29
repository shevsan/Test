package ua.oshevchuk.test.models.users

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author shevsan on 29.07.2022
 */
open class UserRO : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var userName: String = ""
    var changesCounter: String = ""
    var imageURL: String = ""
}