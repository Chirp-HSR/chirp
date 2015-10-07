# Chirp Demo Servie

Chirp is a small webservice using Jersey/Grizzly/Redis to demonstrate concepts and design decisions of distributed systems.

## Getting Started

The following software must be available to setup and run the Chirp service:

* JDK 8 or newer
* Maven 3 or newer
* Eclipse JDT
* Redis (optional)

    A windows distribution of Redis is available at https://github.com/MSOpenTech/redis/releases

### Setup

1. Check out the project with `...` into your workspace directory.
2. The repository includes Eclipse project settings. Thus, you can simply import the project folder into an Eclipse workspace.

    If you made changes to the `pom.xml`, you may have to newly generate the project settings. You can do this by running `mvn eclipse:eclipse`.

### Executables

The `chirp.cli` package provides two main classes that can be used to test various deployments of the service:

`chirp.cli.ChirpFrontendService`
:   Starts an instance of the chirp frontend. The frontend can be configured in `chirp.cli.FrontendConfig`. The default configuration starts a non-ditributed version of Chirp that requires neither a running backend service nor a running Redis instance.

`chirp.cli.ChirpBackendService`
:   Starts an instance of the chirp backend. See `chirp.cli.BackendConfig` for configuration options. The backend can be used to distribute the tweet repository to a separate machine.

Both executable can be started from Eclipse with the "Run as" -> "Java Application" command or via maven with `mvn exec:java -Dexec.mainClass="chirp.cli.ChirpFrontendService"` or `mvn exec:java -Dexec.mainClass="chirp.cli.ChirpBackendService"` respectively.