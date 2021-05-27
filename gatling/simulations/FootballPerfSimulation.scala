package com.ps.sapesports

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class FootballPerfSimulation extends Simulation {

  val serviceBaseUrl = System.getProperty("serviceBaseUrl", "host.docker.internal:8080")
  val tps = System.getProperty("tps", "2").toInt
  val rampUpSeconds = System.getProperty("rampUpSeconds", "300").toInt
  val steadyStateSeconds = System.getProperty("steadyStateSeconds", "300").toInt
  val rampDownSeconds = System.getProperty("rampDownSeconds", "300").toInt

  println(s"serviceBaseUrl: $serviceBaseUrl")
  println(s"tps: $tps")
  println(s"rampUpSeconds: $rampUpSeconds")
  println(s"steadyStateSeconds: $steadyStateSeconds")
  println(s"rampDownSeconds: $rampDownSeconds")

  val protocol = http
    .baseUrl(s"http://${serviceBaseUrl}")

  val customerFlow = scenario("Customer Flow")
    .exec(http("Get Football Standings")
      .get("/api/sapesports/v1/football/standings/search")
      .queryParam("teamName", "Worthing")
      .queryParam("countryName", "England")
      .queryParam("leagueName", "Non League Premier")
      .check(status is 200))

  setUp(
    customerFlow.inject(
      rampUsersPerSec(0) to (tps) during (rampUpSeconds seconds),
      constantUsersPerSec(tps) during (steadyStateSeconds seconds),
      rampUsersPerSec(tps) to (0) during (rampDownSeconds seconds)).protocols(protocol)
  ).assertions(global.successfulRequests.percent.gt(99))

}
