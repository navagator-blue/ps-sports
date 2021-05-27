package com.ps.sapesports.web.data

import com.fasterxml.jackson.annotation.JsonProperty

data class TeamStandingsResponse(
    @JsonProperty("Country ID & Name") val countryIdAndName: String,
    @JsonProperty("League ID & Name") val leagueIdAndName: String,
    @JsonProperty("Team ID & Name") val teamIdAndName: String,
    @JsonProperty("Overall League Position") val overallLeaguePosition: Int
)
