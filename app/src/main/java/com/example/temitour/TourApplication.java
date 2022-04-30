package com.example.temitour;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TourApplication extends Application {

    // TODO: might be able to delete this if switch from Events activity to just use Africana's URL
    // instead of loading with Http requests
    ExecutorService executorService = Executors.newFixedThreadPool(1);

}
