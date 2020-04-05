package com.example.themoviedb.util;

import com.example.themoviedb.repository.remote.model.configuration.Images;
import com.example.themoviedb.shared_prefs.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

    public static String getFormattedTime(long timeInMins) {
        long hrs = TimeUnit.MINUTES.toHours(timeInMins);
        long mins = timeInMins % 60;
        return hrs + " hours " + mins + " mins";
    }

    public static String getFormattedDate(String dateString){

        System.out.println("Given date is " + dateString);

        SimpleDateFormat sourceSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat destSdf = new SimpleDateFormat("dd MMM YYYY", Locale.getDefault());
        Date date = null;
        try {
            date = sourceSdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return dateString;
        }
        return destSdf.format(date);
    }
}
