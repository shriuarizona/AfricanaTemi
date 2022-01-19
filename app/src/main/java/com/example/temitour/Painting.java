package com.example.temitour;

import com.google.gson.Gson;

/**
 * This enum contains information about each painting in the art gallery
 */
public class Painting {

    private Artist artist;
    private String location;
    private int artistId;
    private String year;
    private String medium;
    private String measurements;
    private String description;

    public Painting(String location, int artistId, String year, String medium, String measurements,
                    String description) {
        this.location = location;
        this.artistId = artistId;
        this.year = year;
        this.medium = medium;
        this.measurements = measurements;
        this.description = description;
    }

    /**
     * Getter for the artist associated with this painting
     * @return artist of the painting
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Getter for the string describing when the painting was created
     * @return year string
     */
    public String getYear() {
        return year;
    }

    /**
     * Getter for the string describing the medium of the painting
     * @return medium string
     */
    public String getMedium() {
        return medium;
    }

    /**
     * Getter for the string describing the measurements of the painting
     * @return measurements string
     */
    public String getMeasurements() {
        return measurements;
    }

    /**
     * Getter for the string describing the painting
     * @return description string
     */
    public String getDescription() {
        return description;
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
