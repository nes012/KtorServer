package dizi.anzhy.plugins

import dizi.anzhy.routes.getAllHeroes
import dizi.anzhy.routes.root
import dizi.anzhy.routes.searchHeroes
import io.ktor.server.routing.*
 import io.ktor.server.application.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
        searchHeroes()

        static("/images"){
            resources("images")
        }
    }
}
