package model;

import exceptions.NoRecordedEmotionalStates;
import recording.Recordable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

// Average of multiple emotionalState's represented as a singular EmotionalState
public class AveragedEmotionalState extends GeneralEmotionalState implements Recordable, Serializable {

    private ArrayList<EmotionalState> loadedEmotionalStates = new ArrayList<EmotionalState>();
    private int sumJoySadnessScale = 0;
    private int sumFearAngerScale = 0;
    private int sumSurpriseAnticipationScale = 0;
    private int sumTrustLoathingScale = 0;
    private EmoteStateRecorder emoteStateRecorder;

    // EFFECTS: creates averaged Emotion objects and an averaged mood vector of <JoySadness, FearAnger,
    //          SurpriseAnticipation, TrustLoathing> of all saved EmotionalStates with keyword in file name
    public AveragedEmotionalState(String directory, String keyword) throws NoRecordedEmotionalStates {
        File[] recordings = new File(directory).listFiles();
        for (File f : recordings) {
            String pathname = f.getAbsolutePath();
            if (pathname.contains(keyword)) {
                EmotionalState currentEmoState = new EmotionalState();
                currentEmoState.load(pathname);
                loadedEmotionalStates.add(currentEmoState);
                ArrayList<Emotion> moodVector = currentEmoState.getMoodVector();
                increaseScaleValuesByVector(moodVector);
            }
        }

        if (loadedEmotionalStates.size() == 0) {
            throw new NoRecordedEmotionalStates();
        }

        this.emoteStateRecorder = new EmoteStateRecorder(this);
        this.moodVector = this.makeMoodVector();
    }

    private void increaseScaleValuesByVector(ArrayList<Emotion> moodVector) {
        this.sumJoySadnessScale += moodVector.get(0).getScaleValue();
        this.sumFearAngerScale += moodVector.get(1).getScaleValue();
        this.sumSurpriseAnticipationScale += moodVector.get(2).getScaleValue();
        this.sumTrustLoathingScale += moodVector.get(3).getScaleValue();
    }

    // EFFECTS: create Emotions based on averages and return a mood vector of Emotions
    public ArrayList<Emotion> makeMoodVector() {
        int numEmoStates = loadedEmotionalStates.size();
        ArrayList<Emotion> vector = new ArrayList<Emotion>();
        generateAndSetAveragedEmotions(numEmoStates);
        populateEmotionVector(vector);
        return vector;
    }

    private void generateAndSetAveragedEmotions(int numEmoStates) {
        this.joySadness = this.makeJoySadnessEmotion(this.sumJoySadnessScale / numEmoStates);
        this.fearAnger = this.makeFearAngerEmotion(this.sumFearAngerScale / numEmoStates);
        this.surpriseAntic = this.makeSurpriseAnticipationEmotion(this.sumSurpriseAnticipationScale
                / numEmoStates);
        this.trustLoathing = this.makeTrustLoathingEmotion(this.sumTrustLoathingScale / numEmoStates);
    }

    private void populateEmotionVector(ArrayList<Emotion> vector) {
        vector.add(joySadness);
        vector.add(fearAnger);
        vector.add(surpriseAntic);
        vector.add(trustLoathing);
    }

    // EFFECTS: saves the AveragedEmotionalState
    public void save(String filename) {
        emoteStateRecorder.save(filename);
    }

    // MODIFIES: this
    // EFFECTS: loads a AveragedEmotionalState changing current one to the one that is being loaded
    public void load(String filename) {
        emoteStateRecorder.load(filename);
    }
}
