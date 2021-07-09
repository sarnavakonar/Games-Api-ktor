package main

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import main.model.request.FavouriteRequestBody
import repository.Repository

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (credentials.name == "sarnava" && credentials.password == "konar") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        authenticate("auth-basic") {

            get("/getAllGames") {
                call.respond(Repository.getAllGames())
            }

            post("/alterFav") {
                val requestBody = call.receive<FavouriteRequestBody>()
                val username = requestBody.username
                val gameId = requestBody.gameId
                val addToFav = requestBody.addToFav
                if(username.isNullOrEmpty() || gameId == -1){
                    call.respond(HttpStatusCode.BadRequest, "Invalid request")
                    return@post
                }
                if (addToFav){
                    val result = Repository.addGameAsFavourite(username, gameId)
                    call.respond("Add Result is: $result")
                }
                else{
                    val result = Repository.deleteGameAsFavourite(username, gameId)
                    call.respond("Delete Result is: $result")
                }
            }

//            get("/session/increment") {
//                val session = call.sessions.get<MySession>() ?: MySession()
//                call.sessions.set(session.copy(count = session.count + 1))
//                call.respondText("Counter is ${session.count}. Refresh to increment.")
//            }
//
//            get("/json/gson") {
//                call.respond(mapOf("hello" to "world-hi"))
//            }
//
//            get("/download") {
//                val file = File("resources/files/img.jpg")
//                call.response.header(
//                    HttpHeaders.ContentDisposition,
//                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "img.jpg")
//                        .toString()
//                )
//                call.respondFile(file)
//            }
        }
    }
}

data class MySession(val count: Int = 0)

