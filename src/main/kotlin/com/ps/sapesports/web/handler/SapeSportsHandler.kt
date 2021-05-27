package com.ps.sapesports.web.handler

import com.ps.sapesports.model.TeamStandings
import com.ps.sapesports.service.FootballService
import com.ps.sapesports.service.request.SearchStandingsRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class SapeSportsHandler(private val footballService: FootballService) {

    fun searchStandings(serverRequest: ServerRequest): Mono<ServerResponse> {

        val countryName = serverRequest.queryParam("countryName")
        val leagueName = serverRequest.queryParam("leagueName")
        val teamName = serverRequest.queryParam("teamName")

        require(listOf(countryName, leagueName, teamName).any { it.isPresent }) {
            "At least one of [countryName, leagueName, teamName] must be specified"
        }

        val standings = footballService.searchStandings(
            SearchStandingsRequest(
                countryName = countryName.orElse(null),
                leagueName = leagueName.orElse(null),
                teamName = teamName.orElse(null)
            )
        )

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(standings, TeamStandings::class.java))
    }
}