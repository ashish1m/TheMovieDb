package com.example.themoviedb.ui.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.themoviedb.R;
import com.example.themoviedb.ui.movie_list.MovieListActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNewsListActivity();
            }
        }, 3000);
    }

    private void navigateToNewsListActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
