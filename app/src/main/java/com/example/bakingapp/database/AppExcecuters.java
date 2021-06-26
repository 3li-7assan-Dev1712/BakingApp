package com.example.bakingapp.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExcecuters {

    // using Executor to fill the database in a background thread.
    private static Executor recipeIO; // recipe Input Output
    private final static Object LOCK = new Object();
    private static  AppExcecuters sInstance;
    private AppExcecuters(Executor _recipeIO){
        this.recipeIO = _recipeIO;
    }
    public Executor recipeIO(){
        return recipeIO;
    }
    public static AppExcecuters getsInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExcecuters(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }
}
