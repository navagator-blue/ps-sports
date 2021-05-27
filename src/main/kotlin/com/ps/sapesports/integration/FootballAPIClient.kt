package com.ps.sapesports.integration

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.ps.sapesports.integration.data.Country
import com.ps.sapesports.integration.data.League
import com.ps.sapesports.integration.data.Standings
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class FootballAPIClient(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${sapesports.football-api.base-url}") private val baseUrl: String,
    @Value("\${sapesports.football-api.api-key}") private val apiKey: String
) {
    private val footballWebClient: WebClient = webClientBuilder
        .codecs {
            it.defaultCodecs().jackson2JsonDecoder(
                Jackson2JsonDecoder(
                    Jackson2ObjectMapperBuilder
                        .json()
                        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                        .modules(KotlinModule())
                        .failOnUnknownProperties(false)
                        .build()
                )
            )
        }
        .baseUrl(baseUrl)
        .defaultHeader("accept", "application/json")
        .defaultHeader("cache-control", "no-cache")
        .filter { request, next ->
            Mono.deferContextual { Mono.just(it) }
                .flatMap {
                    if (it.hasKey("x-trace-id")) {
                        val mutatedRequest = ClientRequest
                            .from(request)
                            .headers { httpHeaders -> httpHeaders["x-trace-id"] = it["x-trace-id"] }
                            .build()
                        next.exchange(mutatedRequest)
                    } else {
                        next.exchange(request)
                    }
                }
        }
        .build()

    fun getCountries(): Flux<Country> {
        return footballWebClient
            .get()
            .uri("/?action=get_countries&APIkey={apiKey}", apiKey)
            .retrieve()
            .bodyToFlux(Country::class.java)
            .doOnComplete { logger.info("Successfully retrieved countries from football API") }
            .doOnError { logger.error("Error while retrieving countries from football API $it") }
    }

    fun getLeagues(countryId: String?): Flux<League> {
        return footballWebClient
            .get()
            .uri { builder ->
                builder.path("/")
                    .queryParam("action", "get_leagues")
                    .queryParam("APIkey", apiKey)
                    .queryParamIfPresent("country_id", Optional.ofNullable(countryId))
                    .build()
            }
            .retrieve()
            .bodyToFlux(League::class.java)
            .doOnComplete { logger.info("Successfully retrieved leagues from football API") }
            .doOnError { logger.error("Error while retrieving leagues from football API $it") }
    }

    fun getStandings(leagueId: String): Flux<Standings> {
        return footballWebClient
            .get()
            .uri { builder ->
                builder.path("/")
                    .queryParam("action", "get_standings")
                    .queryParam("APIkey", apiKey)
                    .queryParam("league_id", leagueId)
                    .build()
            }
            .retrieve()
            .bodyToFlux(Standings::class.java)
            .doOnComplete { logger.info("Successfully retrieved standings for league $leagueId from football API") }
            .doOnError { logger.error("Error while retrieving standings for league $leagueId from football API $it") }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(FootballAPIClient::class.java)
    }


}