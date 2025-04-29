# osb-asah-performance-test

Asah uses Gatling for performance tests.

### Gradle

For example, to run a performance test on the `PostAnalyticsEventsLoadSimulation` scenario via Gradle, execute:

`./gradlew gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsSpikeSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`

## Docker

For example, to run a performance test on the `PostAnalyticsEventsLoadSimulation` scenario via Docker, execute:

`docker build -f Dockerfile ../ -t gatling-runner`

`docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsLoadSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`