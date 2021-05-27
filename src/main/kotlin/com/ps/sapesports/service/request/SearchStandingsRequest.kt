package com.ps.sapesports.service.request

data class SearchStandingsRequest(
    val countryName: String?,
    val leagueName: String?,
    val teamName: String?
)