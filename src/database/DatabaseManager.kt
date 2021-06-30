package database

import database.entity.GamesEntity
import database.entity.GamesTable
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    // config
    private val databaseName = "games_server"
    private val username = "root"
    private val password = "bumbabumba1"

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://localhost:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getGames(gameType: String): List<GamesEntity> {
        return ktormDatabase
            .sequenceOf(GamesTable)
            .filter { it.category eq gameType }
            .toList()
    }

    fun getTrendingGames(): List<GamesEntity> {
        return ktormDatabase
            .sequenceOf(GamesTable)
            .filter { it.trending eq 1 }
            .toList()
    }
}