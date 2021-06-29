package com.example.bakingapp.Entries;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class StepsEntry implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "short_description")
    private String shortDescription;
    private String description;
    @ColumnInfo (name = "video_url")
    private String videoUrl;
    @ColumnInfo (name = "thumbnail_url")
    private String thumbnailUrl;

    private int recipeId;
    public StepsEntry(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl, int recipeId) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.recipeId = recipeId;
    }

    @Ignore
    public StepsEntry(String shortDescription, String description, String videoUrl, String thumbnailUrl, int recipeId) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.recipeId = recipeId;
    }

    protected StepsEntry(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
        recipeId = in.readInt();
    }

    public static final Creator<StepsEntry> CREATOR = new Creator<StepsEntry>() {
        @Override
        public StepsEntry createFromParcel(Parcel in) {
            return new StepsEntry(in);
        }

        @Override
        public StepsEntry[] newArray(int size) {
            return new StepsEntry[size];
        }
    };

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

    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
        dest.writeInt(recipeId);
    }

}
