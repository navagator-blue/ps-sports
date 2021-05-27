# Sape Sports

A reactive microservice to find standings of a team playing league football match using country name, league name and team name.

### Tech
- Spring Webflux
- Kotlin
- Gradle
- Java 11
- Gatling

### Build and start the application
```bash

export SAPESPORTS_FOOTBALLAPI_APIKEY=<the-api-key>
./gradlew bootRun

```

### TODO
- JUnits
- API Doc
- Circuit Breakers (resilience4j)
- Monitoring using Prometheus/Grafana
- Performance Tests using Gatling
- Front-end
