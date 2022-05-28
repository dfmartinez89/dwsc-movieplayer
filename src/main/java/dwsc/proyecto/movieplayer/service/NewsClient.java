package dwsc.proyecto.movieplayer.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import dwsc.proyecto.movieplayer.domain.News;

@Service
@FeignClient(name = "SERVLET", url = "http://localhost:8080/ProductorConsumidor/Servlet") // not very robust, see
																							// https://stackoverflow.com/questions/49624372/how-to-register-external-service-non-msa-on-eureka-discovery-registry
public interface NewsClient {
	@GetMapping("/")
	public ResponseEntity<List<News>> getAllNews();// exponer en el servlet un recurso para leer noticias
}
