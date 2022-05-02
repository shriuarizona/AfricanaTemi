package com.example.temitour;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the overall application and provides a thread pool
 * for asynchronous requests
 *
 * @author Gavin Vogt
 */
public class TourApplication extends Application {

    // TODO: might be able to delete this if the ProfessorsActivity just uses the URL
    // instead of loading with Http requests
    ExecutorService executorService = Executors.newFixedThreadPool(1);

}
