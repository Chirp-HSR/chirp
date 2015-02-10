package chirp.logging;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessLogFilter implements ContainerResponseFilter {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(AccessLogFilter.class);

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res)
			throws IOException {
		LOGGER.info("{} /{} - {} {}", req.getMethod(), req.getUriInfo()
				.getPath(), res.getStatus(), res.getStatusInfo());
	}
}
