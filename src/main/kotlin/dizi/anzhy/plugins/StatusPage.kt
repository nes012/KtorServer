package dizi.anzhy.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException


fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                message = "Page not Found.",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
