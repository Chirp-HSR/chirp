package chirp.api;

import java.io.IOException;
import java.util.function.Function;

import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import chirp.backend.RedisTweetRepository;

public class Serialization {
	private final ObjectMapper mapper = new ObjectMapper();

	private final static Logger LOGGER = LoggerFactory.getLogger(RedisTweetRepository.class);

	public <T> String serialize(T obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			LOGGER.error("Could not serialize {}", obj);
			throw new WebApplicationException();
		}
	}

	public <T> Function<String, T> deserializer(Class<T> clazz) {
		return (json) -> {
			try {
				return mapper.readValue(json, clazz);
			} catch (IOException e) {
				LOGGER.error("Could not create an instance of {} from {}", clazz.getSimpleName(), json);
				throw new WebApplicationException();
			}
		};
	}

	public <T> Function<String, T> deserializer(TypeReference<T> clazz) {
		return (json) -> {
			try {
				return mapper.readValue(json, clazz);
			} catch (IOException e) {
				LOGGER.error("Could not create an instance of {} from {}", clazz.getType().getTypeName(), json);
				throw new WebApplicationException();
			}
		};
	}
}
