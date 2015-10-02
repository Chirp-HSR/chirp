package chirp.logging;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class RequestTracingFilter implements ContainerRequestFilter {

	private final String serverName;

	private final Random rand = new Random();;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RequestTracingFilter.class);

	public RequestTracingFilter() {
		String serverName;
		try {
			serverName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			serverName = Integer.toString(rand.nextInt());
			LOGGER.warn("unable to find hostname, used {} instead", serverName);
		}
		this.serverName = serverName;

		LOGGER.info("initialized request tracking for {}", serverName);
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		if (requestContext.getHeaderString("X-Request-ID") == null) {
			final String requestId = createRequestId();
			MDC.put("requestId", requestId);
			LOGGER.debug("new request id");
		} else {
			MDC.put("requestId", requestContext.getHeaderString("X-Request-ID"));
		}
	}

	private String createRequestId() {
		final StringBuilder sb = new StringBuilder(serverName);
		sb.append(':');
		sb.append(System.currentTimeMillis());
		sb.append(':');
		sb.append(rand.nextInt(9999));
		return sb.toString();
	}

}
