package com.example.themoviedb.repository.remote.model.movie_list;

import com.example.themoviedb.repository.db.entity.Movie;

import java.util.List;

public class MovieListResponse {

    private String page;

    private String total_pages;

    private List<Movie> results;

    private String total_results;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }
}
