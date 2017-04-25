package org.ferdi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ferdinand.Szekeresch on 20.04.2017.
 */
@Controller
public class MovieController {

    private static final String BASE_URL = "http://www.omdbapi.com/?";

    // @RequestParam()
    @RequestMapping("/search")
    public String search(@RequestParam(value = "name", required = false, defaultValue = "Star Wars") String name,
        Model model) throws IOException {
        final String query = UriUtils.encodeQuery("s=" + name, "UTF-8");
        URI searchUri = URI.create(BASE_URL + query);
        final OMDBResponseWrapper wrapper = doSearch(searchUri);
        List<Movie> movies = wrapper.getSearch();

        movies.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return m1.getYear().compareTo(m2.getYear());
            }
        });

//		movies.sort((m1, m2) -> m1.getYear().compareTo(m2.getYear()));

//		movies.sort(this::compareMovies);

        model.addAttribute("movies", movies);
        return "search";
    }

    private int compareMovies(Movie m1, Movie m2) {
        return m1.getYear().compareTo(m2.getYear());
    }

    private OMDBResponseWrapper doSearch(URI searchUri) throws IOException {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        final ClientHttpRequest request = factory.createRequest(searchUri, HttpMethod.GET);
        final ClientHttpResponse execute = request.execute();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.fromJson(new InputStreamReader(execute.getBody()), new TypeToken<OMDBResponseWrapper>() {}.getType());
    }
}
