package database

import database.entity.GamesEntity
import database.entity.GamesTable
import main.database.entity.DevelopersEntity
import main.database.entity.DevelopersTable
import main.database.entity.FavouritesTable
import main.database.entity.UsersTable
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.schema.Column

class DatabaseManager {

    // config
    private val databaseName = "games_server"
    private val username = "root"
    private val password = "bumbabumba1"

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://localhost:3306/$databaseName?user=$username&password=$password&useSSL=false"
        //val jdbcUrl = "jdbc:mysql://us-cdbr-east-04.cleardb.com:3306/heroku_70ea6ff4c3e3313?user=b3f8bb58a60d20&password=e83a13d6&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getGames(): List<GamesEntity> {
        return ktormDatabase
            .sequenceOf(GamesTable)
            //.filter { it.category eq gameType }
            .toList()
    }

    fun getDevelopers(): List<DevelopersEntity> {
        return ktormDatabase
            .sequenceOf(DevelopersTable)
            .toList()
    }

    fun getUserIdFromUsername(username: String): Int? {
        ktormDatabase
            .from(UsersTable)
            .select(UsersTable.id)
            .where { UsersTable.username eq username }
            .forEach {
                return it[UsersTable.id]
            }
        return -1
    }

    fun addGameAsFavourite(userId: Int, gameId: Int): Int {
        return ktormDatabase.insert(FavouritesTable) {
            set(it.userid, userId)
            set(it.gameid, gameId)
        }
    }

    fun deleteGameAsFavourite(userId: Int, gameId: Int): Int {
        return ktormDatabase.delete(FavouritesTable){
            (it.userid eq userId) and (it.gameid eq gameId)
        }
    }

    fun get(): Query {
        return ktormDatabase
            .from(GamesTable)
            .innerJoin(DevelopersTable, on = GamesTable.developer eq DevelopersTable.id)
            .select(GamesTable.id, GamesTable.name, GamesTable.category, GamesTable.image, GamesTable.trending, DevelopersTable.name, DevelopersTable.logo)
//            .forEach {
//                it[GamesTable.name]
//            }
    }

}