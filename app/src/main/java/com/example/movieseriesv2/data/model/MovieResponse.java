package com.example.movieseriesv2.data.model;





import java.util.List;

import retrofit2.Callback;

public class MovieResponse {
    private List<Movie> results;

    // Getter and setter for 'results'
    public List<Movie> getResults() {
        return results;
    }


    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
