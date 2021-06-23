package com.example.bakingapp.Entries;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StepsEntry {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "short_description")
    private String shortDescription;
    private String description;
    @ColumnInfo (name = "video_url")
    private String videoUrl;
    @ColumnInfo (name = "thumbnail_url")
    private String thumbnailUrl;
}
