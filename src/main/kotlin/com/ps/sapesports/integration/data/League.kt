package com.ps.sapesports.integration.data

import com.fasterxml.jackson.annotation.JsonCreator

data class League @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    val countryId: String,
    val countryName: String,
    val leagueId: String,
    val leagueName: String,
    val leagueSeason: String,
    val leagueLogo: String,
    val countryLogo: String
)