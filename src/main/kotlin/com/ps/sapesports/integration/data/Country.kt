package com.ps.sapesports.integration.data

import com.fasterxml.jackson.annotation.JsonCreator

data class Country @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    val countryId: String,
    val countryName: String,
    val countryLogo: String
)