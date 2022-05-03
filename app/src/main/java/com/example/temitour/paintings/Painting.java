package com.example.temitour.paintings;

import com.example.temitour.R;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * This class represents a painting in the art gallery
 *
 * @author Gavin Vogt
 */
public class Painting {

    private Artist artist;
    @SerializedName("location")
    private String location;
    @SerializedName("artistId")
    private int artistId;
    @SerializedName("year")
    private String year;
    @SerializedName("medium")
    private String medium;
    @SerializedName("measurements")
    private String measurements;
    @SerializedName("description")
    private String description;
    @SerializedName("qrCode")
    private String qrCode;
    @SerializedName("altLocation")
    private String alternateLocation;

    /**
     * Creates the painting
     * @param location is the string representing the location of the painting in Temi's map
     * @param artistId is the ID of the Artist that created this painting
     * @param year is the year the painting was created
     * @param medium is the medium on which the painting was created
     * @param measurements are the measurements of the painting
     * @param description is the description of the painting
     * @param qrCode is the name of the QR code file
     * @param alternateLocation is the name of alternate location to go to, or null if irrelevant
     */
    public Painting(String location, int artistId, String year, String medium, String measurements,
                    String description, String qrCode, String alternateLocation) {
        this.location = location;
        this.artistId = artistId;
        this.year = year;
        this.medium = medium;
        this.measurements = measurements;
        this.description = description;
        this.qrCode = qrCode;
        this.alternateLocation = alternateLocation;
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
     * Getter for the string representing the name of the location on Temi's map.
     * Uses the alternate location if it is not null (such as "painting1 and painting2"),
     * assuming it to represent a more optimal location to use in the physical tour. This
     * would be if paintings 1 and 2 are directly adjacent, so it would be optimal for
     * Temi to stand in the middle for both instead of having to move.
     * @return name of the location
     */
    public String getLocation() {
        return (alternateLocation == null) ? location : alternateLocation;
    }

    /**
     * Getter for the resource ID of the image drawable for this painting
     * @return drawable painting image ID
     */
    public int getImageId() {
        try {
            Field field = R.drawable.class.getDeclaredField(location);
            return field.getInt(field);
        } catch (Exception e) {
            throw new RuntimeException("Painting image resource not found for " + location);
        }
    }

    /**
     * Getter for the resource ID of the image drawable for the QR code
     * @return drawable QR code image ID
     */
    public int getQrCodeId() {
        try {
            Field field = R.drawable.class.getDeclaredField(qrCode);
            return field.getInt(field);
        } catch (Exception e) {
            throw new RuntimeException("");
        }
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
    public static class InvalidArtistException extends Exception {
        public InvalidArtistException(String message) {
            super(message);
        }
    }

}
