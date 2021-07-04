package main.database.entity

import main.util.Constants.DEVELOPERS_TABLE
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DevelopersTable : Table<DevelopersEntity>(DEVELOPERS_TABLE) {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val logo = varchar("logo").bindTo { it.logo }
}

interface DevelopersEntity : Entity<DevelopersEntity> {
    companion object : Entity.Factory<DevelopersEntity>()
    val id: Int
    var name: String
    var logo: String
}