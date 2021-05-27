# Sape Sports API

A reactive microservice to find standings of a team playing league football match using country name, league name and team name.

### Tech stack
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

### What has been done so far
- Reactive microservice
- Favors immutability
- API Doc (Design First) ([doc](openapi.yaml))
- Integration test using WebTestClient (90%+ coverage)
- Perf simulation using Gatling

### Potential enhancements
- JUnits (Must)
- Circuit Breakers (resilience4j)
- Retries
- Monitoring using Prometheus/Grafana
- Performance Tests using Gatling ([Readme](gatling/README.md))
- Front-end
