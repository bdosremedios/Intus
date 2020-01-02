package model;

import exceptions.InvalidIntegerException;

import java.io.Serializable;
import java.util.ArrayList;

// Generalized abstract EmotionalState
public abstract class GeneralEmotionalState extends Subject implements Serializable {
    protected Emotion joySadness;
    protected Emotion fearAnger;
    protected Emotion surpriseAntic;
    protected Emotion trustLoathing;
    protected ArrayList<Emotion> moodVector;

    // REQUIRES: 0 <= scalePlacement <= 100
    // EFFECTS: return an Emotion with high end Joy low end Sadness and scale value between them
    public Emotion makeJoySadnessEmotion(int scalePlacement) throws InvalidIntegerException {
        return new Emotion("Joy", "Sadness", scalePlacement);
    }

    // REQUIRES: 0 <= scalePlacement <= 100
    // EFFECTS: return an Emotion with high end Fear low end Anger and scale value between them
    public Emotion makeFearAngerEmotion(int scalePlacement) throws InvalidIntegerException {
        return new Emotion("Fear", "Anger", scalePlacement);
    }

    // REQUIRES: 0 <= scalePlacement <= 100
    // EFFECTS: return an Emotion with high end Surprise low end Anticipation and scale value between them
    public Emotion makeSurpriseAnticipationEmotion(int scalePlacement) throws InvalidIntegerException {
        return new Emotion("Surprise", "Anticipation", scalePlacement);
    }

    // REQUIRES: 0 <= scalePlacement <= 100
    // EFFECTS: return an Emotion with high end Trust low end Loathing and scale value between them
    public Emotion makeTrustLoathingEmotion(int scalePlacement) throws InvalidIntegerException {
        return new Emotion("Trust", "Loathing", scalePlacement);
    }

    // EFFECTS: returns current moodVector
    public ArrayList<Emotion> getMoodVector() {
        return this.moodVector;
    }

    // REQUIRES: makeJoySadness, makeFearAnger, makeSurpriseAnticipation, makeTrustLoathing called prior
    // EFFECTS: return a mood vector of current Emotions
    public abstract ArrayList<Emotion> makeMoodVector();

    // EFFECTS: prints out moodVector values
    public void print() {
        System.out.println("Mood Vector Values:");
        for (Emotion e :moodVector) {
            System.out.println("    Emotion " + e.getHighEndEmotion() + "/" + e.getLowEndEmotion() + ": "
                    + e.getScaleValue());
        }
    }

    // EFFECTS: returns true if object given has same moodVector
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeneralEmotionalState generalEmotionalState = (GeneralEmotionalState) o;

        if (!joySadness.equals(generalEmotionalState.joySadness)) {
            return false;
        } else if (!fearAnger.equals(generalEmotionalState.fearAnger)) {
            return false;
        } else if (!surpriseAntic.equals(generalEmotionalState.surpriseAntic)) {
            return false;
        } else {
            return trustLoathing.equals(generalEmotionalState.trustLoathing);
        }
    }

    // EFFECTS: returns hashCode of GeneralEmotionalState
    @Override
    public int hashCode() {
        int result = joySadness.hashCode();
        result = 31 * result + fearAnger.hashCode();
        result = 31 * result + surpriseAntic.hashCode();
        result = 31 * result + trustLoathing.hashCode();
        return result;
    }

}
