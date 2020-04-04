package com.example.themoviedb.repository.remote.model.configuration;

import java.util.List;

public class ConfigurationResponse {

    private Images images;

    private List<String> change_keys;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getChange_keys() {
        return change_keys;
    }

    public void setChange_keys(List<String> change_keys) {
        this.change_keys = change_keys;
    }
}
