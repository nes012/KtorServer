package dizi.anzhy.plugins

import io.ktor.server.plugins.callloging.*
import io.ktor.server.application.*

fun Application.configureMonitoring() {
    install(CallLogging)
}
