package repository

import database.DatabaseManager
import main.model.GamesResponse
import main.util.Constants.ACTION
import main.util.Constants.OPEN_WORLD
import main.util.Constants.RACING
import main.util.Constants.SPORTS
import model.Game

object Repository {

    private val databaseManager = DatabaseManager()

    fun getAllGames(): GamesResponse {
        return GamesResponse(
            trendingGames = getTrendingGames(),
            sportsGames = getGames(SPORTS),
            openWorldGames = getGames(OPEN_WORLD),
            actionGames = getGames(ACTION),
            racingGames = getGames(RACING)
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