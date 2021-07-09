package main.model.request

data class FavouriteRequestBody(
    val username: String?,
    var gameId: Int = -1,
    var addToFav: Boolean = true
)
