package dwsc.proyecto.movieplayer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCommentException extends ResponseStatusException{
	private static final long serialVersionUID = 1L;

	public InvalidCommentException(HttpStatus code, String message) {
		super(code, message);
	}
}
