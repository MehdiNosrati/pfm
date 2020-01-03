package io.mns.mpfm.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags_table")
public class Tag extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String color;

    @Ignore
    public Tag(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public Tag(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
