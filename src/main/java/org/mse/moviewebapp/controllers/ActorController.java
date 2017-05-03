package org.mse.moviewebapp.controllers;

import org.mse.moviewebapp.rest.Movie;
import org.mse.moviewebapp.rest.MovieDetail;
import org.mse.moviewebapp.rest.OMDBResponseWrapper;
import org.mse.moviewebapp.rest.OmdbSearchEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ferdinand.Szekeresch on 02.05.2017.
 */
@Controller
public class ActorController {

	@Autowired
	private OmdbSearchEndpoint omdbSearchEndpoint;

	// @RequestParam()
	@RequestMapping("/actors")
	public String search(@RequestParam(value = "name", required = false, defaultValue = "Star Wars") String name,
		Model model) throws IOException {

		final OMDBResponseWrapper wrapper = omdbSearchEndpoint.search(name);
		List<Movie> movies = wrapper.getSearch();

		List<MovieDetail> details = movies.stream().map(this::queryDetail).filter(Optional::isPresent).map(Optional::get).collect(
			Collectors.toList());

		final Map<String, List<MovieDetail>> actorMap = details.stream().flatMap(this::streamActors)
			.collect(Collectors.toMap(Actor::getName, Actor::getMovies, this::combineLists));

		model.addAttribute("actors", actorMap);

		return "actors";
	}

	private Optional<MovieDetail> queryDetail(final Movie movie) {
		try {
			return Optional.of(omdbSearchEndpoint.details(movie.getImdbID()));
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private Stream<Actor> streamActors(final MovieDetail movieDetail) {
		return Arrays.stream(movieDetail.getActors().split(", ")).map(Actor::new).peek(a -> a.getMovies().add(movieDetail));
	}

	private <T> List<T> combineLists(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<>();
		list.addAll(list1);
		list.addAll(list2);
		return list;
	}

}
