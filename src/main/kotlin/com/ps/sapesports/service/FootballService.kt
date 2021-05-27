package com.ps.sapesports.service

import com.ps.sapesports.integration.FootballAPIClient
import com.ps.sapesports.integration.data.Standings
import com.ps.sapesports.model.TeamStandings
import com.ps.sapesports.service.request.SearchStandingsRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FootballService(private val footballAPIClient: FootballAPIClient) {

    fun searchStandings(searchStandingsRequest: SearchStandingsRequest): Mono<TeamStandings> {

        // Looking at the API, get_countries can be omitted as countryId is optional in the get_leagues endpoint
        // However this should be done based on get_leagues performance

        return footballAPIClient.getCountries()
            .collectList()
            .map { countries ->
                countries.firstOrNull {
                    it.countryName.equals(searchStandingsRequest.countryName, true)
                } ?: throw IllegalArgumentException("Country not found: ${searchStandingsRequest.countryName}")
            }
            .doOnNext { logger.info("Found matching country $it") }
            .flatMapMany { country -> footballAPIClient.getLeagues(countryId = country.countryId) }
            .collectList()
            .map { leagues ->
                leagues.firstOrNull {
                    it.leagueName.equals(searchStandingsRequest.leagueName, true)
                }
                    ?: throw IllegalArgumentException("League not found: ${searchStandingsRequest.leagueName}")

            }
            .doOnNext { logger.info("Found matching league $it") }
            .flatMapMany { league -> footballAPIClient.getStandings(leagueId = league.leagueId) }
            .collectList()
            .map { it.first { standings -> standings.leagueName.equals(searchStandingsRequest.leagueName, true) } }
            .map { mapStandings(it) }
    }

    private fun mapStandings(standings: Standings): TeamStandings {
        return TeamStandings(
            countryName = standings.countryName,
            leagueId = standings.leagueId,
            leagueName = standings.leagueName,
            teamId = standings.teamId,
            teamName = standings.teamName,
            overallLeaguePosition = standings.overallLeaguePosition
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FootballService::class.java)
    }

}