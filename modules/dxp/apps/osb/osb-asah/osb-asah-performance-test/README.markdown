# osb-asah-performance-test

Asah uses Gatling for performance tests.

To run a performance test on a specific scenario, execute the following command:

`./gradlew gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.<SIMPLE CLASS NAME OF SIMULATION> -Dgatling.baseUrl=<BASE URL OF TESTING SERVICE> -Dosb.asah.projectId=<PROJECT ID> -Dosb.asah.security.token=<SECURITY TOKEN>`

For example, this command executes `PostAnalyticsEventsSpikeSimulation`:

`./gradlew gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsSpikeSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`

## Docker

To run Gatling tests inside a Docker container, run the following commands:

### Build

`docker build -f Dockerfile ../ -t gatling-runner`

### Run

`docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.<SIMPLE CLASS NAME OF SIMULATION> -Dgatling.baseUrl=<BASE URL OF TESTING SERVICE> -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -Dosb.asah.projectId=<PROJECT ID> -Dosb.asah.security.token=<SECURITY TOKEN>`

For example, this command executes `PostAnalyticsEventsSpikeSimulation`:

`docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatling.simulation.PostAnalyticsEventsLoadSimulation -Dgatling.baseUrl=http://192.168.0.23:8084 -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -Dosb.asah.projectId=osbasah -Dosb.asah.security.token=123456789`