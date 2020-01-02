package model;

import exceptions.InvalidIntegerException;

import java.io.Serializable;

// Represents the value of an Emotion between two opposing ends of emotional extremes (e.g. between joy and sadness)
// with one end being 0 and the other being 100
public class Emotion implements Serializable {

    private String highEndEmotion;
    private String lowEndEmotion;
    private int scaleValue;

    // EFFECTS: creates an Emotion object setting names for the high end emotion and the low end emotion, and the
    //          scale value between 0 and 100 between the two extremes
    public Emotion(String highEnd, String lowEnd, int scalePlacement) throws InvalidIntegerException {
        if (scalePlacement < 0 | scalePlacement > 100) {
            throw new InvalidIntegerException();
        }
        this.highEndEmotion = highEnd;
        this.lowEndEmotion = lowEnd;
        this.scaleValue = scalePlacement;
    }

    // EFFECTS: returns name of highEndEmotion
    public String getHighEndEmotion() {
        return this.highEndEmotion;
    }

    // EFFECTS: returns name of lowEndEmotion
    public String getLowEndEmotion() {
        return this.lowEndEmotion;
    }

    // MODIFIES: this
    // EFFECTS : sets scaleValue to given int
    public void setScaleValue(int i) {
        this.scaleValue = i;
    }

    // EFFECTS: returns scaleValue
    public int getScaleValue() {
        return this.scaleValue;
    }

    // EFFECTS: returns true if high end emotion, low end emotion, and scale value are the same
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Emotion emotion1 = (Emotion) o;

        if (scaleValue != emotion1.scaleValue) {
            return false;
        } else if (!highEndEmotion.equals(emotion1.highEndEmotion)) {
            return false;
        } else {
            return lowEndEmotion.equals(emotion1.lowEndEmotion);
        }
    }

    // EFFECTS: returns hashCode of emotion
    @Override
    public int hashCode() {
        int result = highEndEmotion.hashCode();
        result = 31 * result + lowEndEmotion.hashCode();
        result = 31 * result + scaleValue;
        return result;
    }
}
