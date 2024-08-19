To execute specific scenario execute the following command:

```
./gradlew gatlingRun-com.liferay.osb.asah.backend.gatlin.simulation.<SIMPLE CLASS NAME OF SIMULATION> -DbaseUrl=<BASE URL OF TESTING SERVICE> -DosbAsahProjectId=<PROJECT ID> -DosbAsahSecurityToken=<SECURITY TOKEN>
```

Here is an example for calling `PostAnalyticsEventsSpikeSimulation`:

```
./gradlew gatlingRun-com.liferay.osb.asah.backend.gatlin.simulation.PostAnalyticsEventsSpikeSimulation -DbaseUrl=http://192.168.0.23:8084 -DosbAsahProjectId=osbasah -DosbAsahSecurityToken=123456789
```

## Docker Runner

To run Gatling tests inside Docker container run the following commands:

### Build

```
docker build -f Dockerfile ../ -t gatling-runner
```

### Run

```
docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatlin.simulation.<SIMPLE CLASS NAME OF SIMULATION> -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -DbaseUrl=<BASE URL OF TESTING SERVICE> -DosbAsahProjectId=<PROJECT ID> -DosbAsahSecurityToken=<SECURITY TOKEN>
```

Here is an example for calling `PostAnalyticsEventsSpikeSimulation`:

```
docker run -it -v ./reports:/gatling/reports/ gatling-runner gatlingRun-com.liferay.osb.asah.backend.gatlin.simulation.PostAnalyticsEventsLoadSimulation -Dgatling.core.outputDirectoryBaseName=/gatling/reports/gatling -DbaseUrl=http://192.168.0.23:8084 -DosbAsahProjectId=osbasah -DosbAsahSecurityToken=123456789
```