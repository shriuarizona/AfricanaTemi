package com.example.temitour.paintings;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the artist of a painting in the art gallery.
 *
 * @author Gavin Vogt
 */
public class Artist {

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public Artist(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter for the artist name
     * @return artist name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the artist ID
     * @return integer representing the artist ID
     */
    public int getId() {
        return this.id;
    }

    public String toString() {
        return "Artist('" + this.name + "')";
    }
    
}
