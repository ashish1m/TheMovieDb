package com.example.themoviedb.util;

import com.example.themoviedb.repository.remote.model.configuration.Images;
import com.example.themoviedb.shared_prefs.SharedPrefManager;

public class Utils {

    public static String getPosterUrl(String imgPath) {
        Images imagesConfig = SharedPrefManager.getInstance().getConfiguration().getImages();
        return imagesConfig.getBase_url()
                + imagesConfig.getPoster_sizes().get(1)
                + imgPath;
    }

    public static String getBackDropUrl(String imgPath) {
        Images imagesConfig = SharedPrefManager.getInstance().getConfiguration().getImages();
        return imagesConfig.getBase_url()
                + imagesConfig.getBackdrop_sizes().get(1)
                + imgPath;
    }
}
