package com.ps.sapesports.model

data class TeamStandings(
    val countryId: String,
    val countryName: String,
    val leagueId: String,
    val leagueName: String,
    val teamId: String,
    val teamName: String,
    val overallLeaguePosition: Int
)