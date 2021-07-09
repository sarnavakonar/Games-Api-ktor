package main.model.response

import main.model.Developer
import main.model.Game

data class GamesResponse(
    val games: Games,
    val developers: List<Developer>
)

data class Games(
    val trendingGames: List<Game>,
    val sportsGames: List<Game>,
    val openWorldGames: List<Game>,
    val actionGames: List<Game>,
    val racingGames: List<Game>
)
