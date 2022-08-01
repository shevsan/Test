package ua.oshevchuk.test.models.details

/**
 * @author shevsan on 29.07.2022
 */
data class RepositoryModel(
    val html_url: String,
    val language: String?,
    val name: String,
    val stargazers_count: Int,
    val owner: Owner
)

data class Owner(
    val login: String
)
