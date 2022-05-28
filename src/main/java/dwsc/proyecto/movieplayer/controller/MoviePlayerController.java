package dwsc.proyecto.movieplayer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dwsc.proyecto.movieplayer.domain.Comment;
import dwsc.proyecto.movieplayer.domain.Movie;
import dwsc.proyecto.movieplayer.domain.News;
import dwsc.proyecto.movieplayer.service.FindMovieClient;
import dwsc.proyecto.movieplayer.service.MovieCommentClient;
import dwsc.proyecto.movieplayer.service.NewsClient;

@Controller
public class MoviePlayerController {
	@Autowired
	FindMovieClient movieClient;

	@Autowired
	MovieCommentClient commentClient;

	@Autowired
	NewsClient newsClient;

	@GetMapping("/")
	public String getNews(Map<String, List<News>> model) throws Exception {

		try {
			ResponseEntity<List<News>> news = newsClient.getAllNews();
			model.put("news", news.getBody());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "index";
	}

	@GetMapping("/movies")
	public String getMovies(Map<String, List<Movie>> model,
			@RequestParam(value = "title", required = false) String title) throws Exception {
		if (title != null) {
			try {
				ResponseEntity<List<Movie>> movies = movieClient.getMoviesByTitle(title);
				model.put("movies", movies.getBody());
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {

			try {
				ResponseEntity<List<Movie>> movies = movieClient.getAllMovies();
				model.put("movies", movies.getBody());
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}

		return "movies";
	}

	@GetMapping("/movies/movie/{movieId}")
	public String getMovieDetails(Model model, @PathVariable String movieId) throws Exception {
		try {
			ResponseEntity<Movie> movieRes = movieClient.getMoviesById(movieId);
			Movie movie = movieRes.getBody();
			model.addAttribute("movie", movie);
			ResponseEntity<Iterable<Comment>> commentRes = commentClient.getCommentsByMovieId(movieId);
			Iterable<Comment> comments = commentRes.getBody();
			model.addAttribute("comments", comments);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "movie";
	}

	@GetMapping("/movies/new-comment/{movieId}")
	public String createCommentForm(Model model, @PathVariable String movieId) {
		model.addAttribute("comment", new Comment());
		model.addAttribute("movieId", movieId);
		return "newComment";
	}

	@PostMapping("/movies/comments/{movieId}")
	public String addComment(@ModelAttribute Comment comment, @PathVariable String movieId) throws Exception {
		try {
			commentClient.addComment(movieId, comment);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/movies/" + movieId;
	}
}
