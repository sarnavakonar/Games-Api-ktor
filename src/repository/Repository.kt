package repository

import database.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.model.Developer
import main.model.Game
import main.model.response.*
import main.util.Constants.ACTION
import main.util.Constants.OPEN_WORLD
import main.util.Constants.RACING
import main.util.Constants.SPORTS
import main.util.Constants.USERNAME
import main.util.Constants.gamesCategoryList

object Repository {

    private val databaseManager = DatabaseManager()

    fun checkIfUserExists(username: String, password: String): Boolean {
        return databaseManager.checkIfUserExists(username, password) > 0
    }

    fun createUserIfNotExist(username: String, password: String): Any {
        var status = "FAILURE"
        var message = "User with username $username already exists"
        if(databaseManager.checkIfUserNameExists(username) == 0){
            if(databaseManager.addNewUser(username, password) == 1){
                status = "SUCCESS"
                message = "User created"
            }
            else{
                message = "Error in creating new user"
            }
        }
        return GenericResponse(status = status, message = message)
    }

    fun getAllFavouritesForUser(): FavouriteGamesResponse {
        val userId = databaseManager.getUserIdFromUsername(USERNAME)
        var games: List<Game> = mutableListOf()
        var status = "FAILURE"
        var message = "User not found"
        userId?.let {
            games = databaseManager.getAllFavouriteGames(userId)
            if (!games.isNullOrEmpty()){
                status = "SUCCESS"
                message = "Favourite games found"
            }
            else{
                message = "No favourite games found"
            }
        }
        return FavouriteGamesResponse(status = status, message = message, games = games)
    }

    fun addGameAsFavourite(gameId: Int): GenericResponse {
        val userId = databaseManager.getUserIdFromUsername(USERNAME)
        var status = "FAILURE"
        var message = "User not found"
        userId?.let {
            if(databaseManager.checkIfAlreadyFavourite(userId, gameId) > 0) {
                message = "Game already exists as favourite"
            }
            else {
                if(databaseManager.addGameAsFavourite(userId, gameId) == 1) {
                    status = "SUCCESS"
                    message = "Favourite game added"
                }
                else{
                    message = "Error in adding game as favourite"
                }
            }
        }
        return GenericResponse(status = status, message = message)
    }

    fun deleteGameAsFavourite(gameId: Int): GenericResponse {
        val userId = databaseManager.getUserIdFromUsername(USERNAME)
        var status = "FAILURE"
        var message = "User not found"
        userId?.let {
            if(databaseManager.deleteGameAsFavourite(userId, gameId) == 1) {
                status = "SUCCESS"
                message = "Favourite game removed"
            }
            else{
                message = "Error in removing game as favourite"
            }
        }
        return GenericResponse(status = status, message = message)
    }

    fun searchGame(param: String?): FavouriteGamesResponse {
        var games: List<Game> = mutableListOf()
        var status = "FAILURE"
        var message = "Search param empty"
        if(!param.isNullOrEmpty()){
            games = databaseManager.searchGame(param)
            if(games.isNotEmpty()){
                status = "SUCCESS"
                message = "Game(s) found"
            }
            else{
                message = "Game not found"
            }
        }
        return FavouriteGamesResponse(status = status, message = message, games = games)
    }

    suspend fun getGameByDev(devId: Int?): FavouriteGamesResponse {
        var games: List<Game> = mutableListOf()
        var status = "FAILURE"
        var message = "Search param empty"
        devId?.let {
            games = withContext(Dispatchers.Default){
                databaseManager.getGamesByDev(devId).map {
                    Game(
                        id = it.id,
                        name = it.name,
                        categoty = it.categoty,
                        image = it.image,
                        trending = it.trending
                    )
                }
            }
            if(games.isNotEmpty()){
                status = "SUCCESS"
                message = "Game(s) found"
            }
            else{
                message = "Games not found"
            }
        }
        return FavouriteGamesResponse(status = status, message = message, games = games)
    }

    suspend fun getGameDetail(gameId: Int?): GameDetailResponse {
        var game: Game? = null
        var status = "FAILURE"
        var message = "Search param empty"
        gameId?.let {
            game = withContext(Dispatchers.Default){
                databaseManager.getGameDetail(gameId)
            }
            if(game != null){
                val userId = databaseManager.getUserIdFromUsername(USERNAME)
                userId?.let {
                    game!!.isFavourite = databaseManager.checkIfAlreadyFavourite(userId, gameId) > 0
                }
                status = "SUCCESS"
                message = "Game data found"
            }
            else{
                message = "Game data not found"
            }
        }
        return GameDetailResponse(status = status, message = message, gamesData = game)
    }

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
                    logo = it.logo,
                    about = it.about,
                    founded = it.founded,
                    twitter = it.twitter,
                    insta = it.insta,
                    fb = it.fb,
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