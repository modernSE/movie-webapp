package org.ferdi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ferdinand.Szekeresch on 21.04.2017.
 */
public class OMDBResponseWrapper {

	private List<Movie> search;

	@SerializedName("totalResults")
	private int totalResults;

	private boolean response;

	public List<Movie> getSearch() {
		return search;
	}

	public void setSearch(List<Movie> search) {
		this.search = search;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}
}
