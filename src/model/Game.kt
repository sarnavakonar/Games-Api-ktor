package model

data class Game(
    val id: Int,
    var name: String,
    var description: String,
    var categoty: String,
    var image: String,
    var video: String,
    var maker: String,
    var rating: Double,
    var trending: Int
)
