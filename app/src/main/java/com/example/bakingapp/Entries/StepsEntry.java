package com.example.bakingapp.Entries;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    public StepsEntry(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Ignore
    public StepsEntry(String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
