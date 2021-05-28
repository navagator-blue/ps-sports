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
# Run on host machine
export SAPESPORTS_FOOTBALLAPI_APIKEY=<the-api-key>
./gradlew bootRun


# To run in a docker container
export SAPESPORTS_FOOTBALLAPI_APIKEY=<the-api-key>
docker build -t sapesports:v1 .
docker run -p 8081:8080 -e SAPESPORTS_FOOTBALLAPI_APIKEY=$SAPESPORTS_FOOTBALLAPI_APIKEY -it sapesports:v1

```

### What has been done so far
- Reactive microservice
- Favors immutability
- API Doc (Design First) ([doc](openapi.yaml))
- Integration test using WebTestClient (90%+ coverage)
- Perf simulation using Gatling

### Potential enhancements
- More tests
- Circuit Breakers (resilience4j)
- Retries
- Tracing
- Monitoring using Prometheus/Grafana
- Performance Tests using Gatling ([Readme](gatling/README.md))
- Front-end
