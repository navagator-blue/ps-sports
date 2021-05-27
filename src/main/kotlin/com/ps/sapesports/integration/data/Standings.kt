package com.ps.sapesports.integration.data

import com.fasterxml.jackson.annotation.JsonCreator

data class Standings @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    val countryName: String,
    val leagueId: String,
    val leagueName: String,
    val teamId: String,
    val teamName: String,
    val overallLeaguePosition: Int
)