package dwsc.proyecto.movieplayer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dwsc.proyecto.movieplayer.domain.Comment;

@Service
@FeignClient("COMMENT-MOVIE")
public interface MovieCommentClient {

	@GetMapping("/comment/{movieId}")
	public ResponseEntity<Iterable<Comment>> getCommentsByMovieId(@PathVariable String movieId);

	@PostMapping("/comment/{movieId}")
	public ResponseEntity<?> addComment(@PathVariable String movieId, @RequestBody Comment comment);
}
