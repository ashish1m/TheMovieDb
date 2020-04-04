package com.example.themoviedb.repository.remote.model.configuration;

import java.util.List;

public class Images {

    private List<String> poster_sizes;

    private String secure_base_url;

    private List<String> backdrop_sizes;

    private String base_url;

    private List<String> logo_sizes;

    private List<String> still_sizes;

    private List<String> profile_sizes;

    public List<String> getPoster_sizes() {
        return poster_sizes;
    }

    public void setPoster_sizes(List<String> poster_sizes) {
        this.poster_sizes = poster_sizes;
    }

    public String getSecure_base_url() {
        return secure_base_url;
    }

    public void setSecure_base_url(String secure_base_url) {
        this.secure_base_url = secure_base_url;
    }

    public List<String> getBackdrop_sizes() {
        return backdrop_sizes;
    }

    public void setBackdrop_sizes(List<String> backdrop_sizes) {
        this.backdrop_sizes = backdrop_sizes;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public List<String> getLogo_sizes() {
        return logo_sizes;
    }

    public void setLogo_sizes(List<String> logo_sizes) {
        this.logo_sizes = logo_sizes;
    }

    public List<String> getStill_sizes() {
        return still_sizes;
    }

    public void setStill_sizes(List<String> still_sizes) {
        this.still_sizes = still_sizes;
    }

    public List<String> getProfile_sizes() {
        return profile_sizes;
    }

    public void setProfile_sizes(List<String> profile_sizes) {
        this.profile_sizes = profile_sizes;
    }
}
