package repository

import database.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.model.Developer
import main.model.Games
import main.model.GamesResponse
import main.util.Constants.ACTION
import main.util.Constants.OPEN_WORLD
import main.util.Constants.RACING
import main.util.Constants.SPORTS
import main.util.Constants.gamesCategoryList
import model.Game

object Repository {

    private val databaseManager = DatabaseManager()

    suspend fun getAllGames(): GamesResponse {
        var sportsGames:List<Game> = ArrayList()
        var actionGames:List<Game> = ArrayList()
        var racingGames:List<Game> = ArrayList()
        var openWorldGames:List<Game> = ArrayList()

        val developers = withContext(Dispatchers.Default){
            databaseManager.getDevelopers().map {
                Developer(
                    id = it.id,
                    name = it.name,
                    logo = it.logo
                )
            }
        }
        val gamesEntity = withContext(Dispatchers.Default){ databaseManager.getGames() }

        gamesCategoryList.forEach { category ->
            when (category) {
                SPORTS -> {
                    sportsGames = withContext(Dispatchers.Default) {
                        gamesEntity
                            .filter { it.categoty == category }
                            .map {
                                Game(
                                    id = it.id,
                                    name = it.name,
                                    categoty = it.categoty,
                                    image = it.image,
                                    trending = it.trending
                                )
                            }
                    }
                }
                ACTION -> {
                    actionGames = withContext(Dispatchers.Default) {
                        gamesEntity
                            .filter { it.categoty == category }
                            .map {
                                Game(
                                    id = it.id,
                                    name = it.name,
                                    categoty = it.categoty,
                                    image = it.image,
                                    trending = it.trending
                                )
                            }
                    }
                }
                RACING -> {
                    racingGames = withContext(Dispatchers.Default) {
                        gamesEntity
                            .filter { it.categoty == category }
                            .map {
                                Game(
                                    id = it.id,
                                    name = it.name,
                                    categoty = it.categoty,
                                    image = it.image,
                                    trending = it.trending
                                )
                            }
                    }
                }
                OPEN_WORLD -> {
                    openWorldGames = withContext(Dispatchers.Default) {
                        gamesEntity
                            .filter { it.categoty == category }
                            .map {
                                Game(
                                    id = it.id,
                                    name = it.name,
                                    categoty = it.categoty,
                                    image = it.image,
                                    trending = it.trending
                                )
                            }
                    }
                }
            }
        }

        val trendingGames = withContext(Dispatchers.Default){
            gamesEntity
                .filter { it.trending == 1 }
                .map {
                    Game(
                        id = it.id,
                        name = it.name,
                        categoty = it.categoty,
                        image = it.image,
                        trending = it.trending
                    )
                }
        }

        val games = Games(
            trendingGames = trendingGames,
            sportsGames = sportsGames,
            openWorldGames = openWorldGames,
            actionGames = actionGames,
            racingGames = racingGames
        )
        return GamesResponse(
            games = games,
            developers = developers
        )
    }

}