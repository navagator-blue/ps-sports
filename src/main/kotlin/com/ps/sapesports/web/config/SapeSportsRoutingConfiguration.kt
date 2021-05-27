package com.ps.sapesports.web.config

import com.ps.sapesports.web.handler.SapeSportsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router


@Configuration
class SapeSportsRoutingConfiguration(private val sapeSportsHandler: SapeSportsHandler) {

    @Bean
    fun sapeSportsRouter(): RouterFunction<ServerResponse> {
        return router {
            GET("$BASE_URI/football/standings/search").invoke { sapeSportsHandler.searchStandings(it) }
        }
    }

    companion object {
        private const val BASE_URI = "/api/sapesports/v1"
    }
}