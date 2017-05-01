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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ferdinand.Szekeresch on 20.04.2017.
 */
@Controller
public class SearchMovieController {

    @Autowired
    private OmdbSearchEndpoint omdbSearchEndpoint;

    // @RequestParam()
    @RequestMapping("/search")
    public String search(@RequestParam(value = "name", required = false, defaultValue = "Star Wars") String name,
                         Model model) throws IOException {

        final OMDBResponseWrapper wrapper = omdbSearchEndpoint.search(name);
        List<Movie> movies = wrapper.getSearch();

        sortMovies(movies);

        movies.stream().map(Movie::getImdbID).map(this::queryMovieDetail).filter(Optional::isPresent).map(Optional::get).map(MovieDetail::getActors).forEach(System.out::println);

        model.addAttribute("movies", movies);
        return "search";
    }

    Optional<MovieDetail> queryMovieDetail(final String imdbId) {
        try {
            return Optional.of(omdbSearchEndpoint.details(imdbId));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void sortMovies(List<Movie> movies) {
        movies.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return m1.getYear().compareTo(m2.getYear());
            }
        });
    }

    private int compareMovies(Movie m1, Movie m2) {
        return m1.getYear().compareTo(m2.getYear());
    }

}
