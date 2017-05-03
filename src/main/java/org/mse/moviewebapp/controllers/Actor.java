package org.mse.moviewebapp.controllers;

import org.mse.moviewebapp.rest.MovieDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferdinand.Szekeresch on 02.05.2017.
 */
public class Actor {

	private String name;

	private List<MovieDetail> movies;

	public Actor(final String name) {
		this.name = name;
		this.movies = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MovieDetail> getMovies() {
		return movies;
	}
}
