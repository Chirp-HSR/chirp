package chirp.cli.utils;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.logging.AccessLogFilter;
import chirp.logging.ExceptionLogger;
import chirp.logging.RequestTracingFilter;

public class ServiceUtils {

	private ServiceUtils() {
	}

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceUtils.class);

	public static void executeServer(String hostUri, Object... resources)
			throws IOException {
		LOGGER.info("Start Chirp instance on {}", hostUri);

		ResourceConfig rc = new ResourceConfig()
				.register(RequestTracingFilter.class)
				.register(AccessLogFilter.class)
				.register(ExceptionLogger.class).register(JacksonFeature.class);

		for (Object resource : resources) {
			rc = rc.register(resource);
		}

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at hostUri
		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(hostUri), rc);

		LOGGER.info("Chirp instance {} started", hostUri);

		System.in.read();
		server.shutdownNow();
	}
}
