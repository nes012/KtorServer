package dizi.anzhy.plugins

import dizi.anzhy.routes.getAllHeroes
import dizi.anzhy.routes.root
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
    }
}