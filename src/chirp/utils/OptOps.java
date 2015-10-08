package chirp.utils;

import java.util.Optional;

public class OptOps {
	public static <T> Optional<T> tryCast(Object o, Class<T> clazz){
		if(clazz.isInstance(o)){
			return Optional.of(clazz.cast(o));
		} else {
			return Optional.empty();
		}
	}
}
