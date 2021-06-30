package database.entity

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object GamesTable : Table<GamesEntity>("games") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val description = varchar("description").bindTo { it.description }
    val category = varchar("category").bindTo { it.categoty }
    val image = varchar("image").bindTo { it.image }
    val video = varchar("video").bindTo { it.video }
    val maker = varchar("maker").bindTo { it.maker }
    val rating = double("rating").bindTo { it.rating }
    val trending = int("trending").bindTo { it.trending }
}

interface GamesEntity : Entity<GamesEntity> {
    companion object : Entity.Factory<GamesEntity>()
    val id: Int
    var name: String
    var description: String
    var categoty: String
    var image: String
    var video: String
    var maker: String
    var rating: Double
    var trending: Int
}