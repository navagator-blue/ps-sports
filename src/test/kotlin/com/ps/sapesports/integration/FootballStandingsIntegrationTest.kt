package com.ps.sapesports.integration

import com.ps.sapesports.web.data.TeamStandingsResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.test.test
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class FootballStandingsIntegrationTest {

    private lateinit var webTestClient: WebTestClient

    @BeforeAll
    internal fun setUp(applicationContext: ApplicationContext) {
        webTestClient = WebTestClient
            .bindToApplicationContext(applicationContext)
            .configureClient()
            .responseTimeout(Duration.ofSeconds(10))
            .build()
    }

    @Test
    internal fun `Given valid input, standings data is returned correctly by the API`() {
        webTestClient
            .get()
            .uri { builder ->
                builder.path("/api/sapesports/v1/football/standings/search")
                    .queryParam("teamName", "Worthing")
                    .queryParam("countryName", "England")
                    .queryParam("leagueName", "Non League Premier")
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .returnResult(TeamStandingsResponse::class.java)
            .responseBody
            .test()
            .assertNext { standings ->
                assertAll({
                    assertEquals("(44) - England", standings.countryIdAndName)
                    assertEquals("(149) - Non League Premier", standings.leagueIdAndName)
                    assertEquals("(3450) - Worthing", standings.teamIdAndName)
                    assertEquals(1, standings.overallLeaguePosition)
                })
            }.verifyComplete()
    }
}