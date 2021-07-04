package model

data class Game(
    val id: Int,
    var name: String,
    var description: String? = null,
    var categoty: String,
    var image: String,
    var video: String? = null,
    var developer: Int? = null,
    var developerLogo: String? = null,
    var rating: Double? = null,
    var trending: Int
)
