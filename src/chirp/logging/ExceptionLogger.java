package chirp.logging;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogger implements ExceptionMapper<Exception> {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExceptionLogger.class);

	@Override
	public Response toResponse(Exception e) {
		if (e instanceof WebApplicationException) {
			return ((WebApplicationException) e).getResponse();
		} else {
			LOGGER.error("Unhandled exception", e);
			return Response.serverError().build();
		}
	}

}
