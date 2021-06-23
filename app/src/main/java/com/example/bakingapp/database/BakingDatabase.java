package com.example.bakingapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class BakingDatabase extends RoomDatabase {
    // creating the database with all the entities
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "bakingDb";
    private static BakingDatabase sInstance;

    public static BakingDatabase getsInstance(Context context){
        if(sInstance == null) {
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), BakingDatabase.class,
                        DATABASE_NAME)
                        .build();

            }
        }
        return sInstance;

    }
    // create the DAOs so we can refer to each table just from the database




}
