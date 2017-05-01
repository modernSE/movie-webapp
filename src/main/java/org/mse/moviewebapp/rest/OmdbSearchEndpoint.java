package org.mse.moviewebapp.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * Created by ferdi on 01.05.17.
 */
@Component
public class OmdbSearchEndpoint {


    private static final String BASE_URL = "http://www.omdbapi.com/?";

    public OMDBResponseWrapper search(final String name) throws IOException {
        final String query = UriUtils.encodeQuery("s=" + name, "UTF-8");
        URI searchUri = URI.create(BASE_URL + query);
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        final ClientHttpRequest request = factory.createRequest(searchUri, HttpMethod.GET);
        final ClientHttpResponse execute = request.execute();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.fromJson(new InputStreamReader(execute.getBody()), new TypeToken<OMDBResponseWrapper>() {}.getType());
    }

    public MovieDetail details(final String imdbId) throws IOException {
        final String query = UriUtils.encodeQuery("i=" + imdbId, "UTF-8");
        URI searchUri = URI.create(BASE_URL + query);
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        final ClientHttpRequest request = factory.createRequest(searchUri, HttpMethod.GET);
        final ClientHttpResponse execute = request.execute();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.fromJson(new InputStreamReader(execute.getBody()), new TypeToken<MovieDetail>() {}.getType());
    }
}
