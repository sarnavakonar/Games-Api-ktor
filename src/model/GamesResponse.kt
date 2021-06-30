package main.model

import model.Game

data class GamesResponse(
    val trendingGames: List<Game>,
    val sportsGames: List<Game>,
    val openWorldGames: List<Game>,
    val actionGames: List<Game>,
    val racingGames: List<Game>
)
