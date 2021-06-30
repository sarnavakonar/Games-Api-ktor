package repository

import database.DatabaseManager
import main.model.GamesResponse
import model.Game

object Repository {

    private val databaseManager = DatabaseManager()

    fun getAllGames(): GamesResponse {
        return GamesResponse(
            trendingGames = getTrendingGames(),
            sportsGames = getGames("Sports"),
            openWorldGames = getGames("Open World"),
            actionGames = getGames("Action"),
            racingGames = getGames("Racing")
        )
    }

    private fun getGames(gameType: String): List<Game> {
        //System.out.println(" -- $gameType -- ")
        return databaseManager.getGames(gameType).map {
            Game(
                id = it.id,
                name = it.name,
                description = it.description,
                categoty = it.categoty,
                image = it.image,
                video = it.video,
                maker = it.maker,
                rating = it.rating,
                trending = it.trending
            )
        }
    }

    private fun getTrendingGames(): List<Game> {
        return databaseManager.getTrendingGames().map {
            Game(
                id = it.id,
                name = it.name,
                description = it.description,
                categoty = it.categoty,
                image = it.image,
                video = it.video,
                maker = it.maker,
                rating = it.rating,
                trending = it.trending
            )
        }
    }
}