package com.example.temitour;

import com.google.gson.Gson;

/**
 * This enum contains information about each painting in the art gallery
 */
public class Painting {

    private Artist artist;
    private String location;
    private int artistId;

    public Painting(String location, int artistId) {
        this.location = location;
        this.artistId = artistId;
    }

    /**
     * Getter for the artist associated with this painting
     * @return artist of the painting
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Setter for the artist associated with this painting (should only be used when
     * instantiating)
     * @param artist is the artist of the painting
     * @throws InvalidArtistException if it is set to null
     */
    public void setArtist(Artist artist) throws InvalidArtistException {
        if (artist == null) {
            throw new InvalidArtistException("Attempted to set artist to null");
        }
        this.artist = artist;
    }

    /**
     * Getter for the string representing the name of the location on Temi's map
     * @return name of the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for the artist ID, used for finding the correct Artist object which also
     * has an associated ID
     * @return artist ID for the painting
     */
    public int getArtistId() {
        return artistId;
    }

    public String toString() {
        return "Painting by " + artist.getName();
    }

    /**
     * Thrown if an invalid artist is provided
     */
    class InvalidArtistException extends Exception {
        public InvalidArtistException(String message) {
            super(message);
        }
    }

}
