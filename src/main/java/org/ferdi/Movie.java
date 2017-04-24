package org.ferdi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ferdinand.Szekeresch on 20.04.2017.
 */
public class Movie {

	private String title;

	private String year;

	@SerializedName("imdbID")
	private String imdbID;

	private String type;

	private String poster;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
}
