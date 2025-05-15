package com.example.movieseriesv2.data.network;

import com.example.movieseriesv2.data.model.Movie;
import com.example.movieseriesv2.data.model.MovieResponse;
import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.data.model.SeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("query") String query,
            @Query("api_key") String apiKey
    );


    @GET("search/tv")
    Call<SeriesResponse> searchSeries(
            @Query("query") String query,
            @Query("api_key") String apiKey
    );
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/popular")
    Call<SeriesResponse> getSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieById(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<Serie> getSerieById(@Path("tv_id") int tvId, @Query("api_key") String apiKey);


}
