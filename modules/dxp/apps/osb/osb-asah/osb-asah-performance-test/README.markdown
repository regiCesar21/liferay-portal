# osb-asah-performance-test

Asah uses Gatling for performance tests.

To run a performance test on a specific scenario, execute the following command:

To run for example `PostAnalyticsEventsSpikeSimulation` simulation, execute:

`./gradlew gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsSpikeSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`

## Docker

To run Gatling tests inside a Docker container, execute the following commands:

### Build

`docker build -f Dockerfile ../ -t gatling-runner`

### Run

To run for example `PostAnalyticsEventsSpikeSimulation` simulation, execute:

`docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsLoadSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`