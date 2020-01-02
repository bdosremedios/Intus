package model;

import exceptions.InvalidIntegerException;
import recording.JournalRecording;
import recording.Recordable;

import java.io.Serializable;
import java.util.ArrayList;

// Specific singular emotional state
public class EmotionalState extends GeneralEmotionalState implements Recordable, Serializable, Observer {

    private JournalRecording journal;
    private EmoteStateRecorder emoteStateRecorder;

    // EFFECTS: creates Emotion objects and a mood vector of <JoySadness, FearAnger, SurpriseAnticipation,
    // TrustLoathing>
    public EmotionalState(int joySadScale, int fearAngerScale, int surpriseAnticipationScale,
                          int trustLoathingScale, JournalRecording journal) throws InvalidIntegerException {
        this.joySadness = this.makeJoySadnessEmotion(joySadScale);
        this.fearAnger = this.makeFearAngerEmotion(fearAngerScale);
        this.surpriseAntic = this.makeSurpriseAnticipationEmotion(surpriseAnticipationScale);
        this.trustLoathing = this.makeTrustLoathingEmotion(trustLoathingScale);
        this.moodVector = this.makeMoodVector();
        this.emoteStateRecorder = new EmoteStateRecorder(this);
        this.journal = journal;
        addObserver(this.journal);
    }

    // EFFECTS: creates Emotion objects and a mood vector of <JoySadness, FearAnger, SurpriseAnticipation,
    //          TrustLoathing> all of scale value 0 and sets the given journal as journal
    public EmotionalState(JournalRecording journal) {
        this.joySadness = this.makeJoySadnessEmotion(0);
        this.fearAnger = this.makeFearAngerEmotion(0);
        this.surpriseAntic = this.makeSurpriseAnticipationEmotion(0);
        this.trustLoathing = this.makeTrustLoathingEmotion(0);
        this.moodVector = this.makeMoodVector();
        this.emoteStateRecorder = new EmoteStateRecorder(this);
        this.journal = journal;
        addObserver(this.journal);
    }

    // EFFECTS: creates Emotion objects and a mood vector of <JoySadness, FearAnger, SurpriseAnticipation,
    //          TrustLoathing> all of scale value 0 and sets an empty journal
    public EmotionalState() {
        this.joySadness = this.makeJoySadnessEmotion(0);
        this.fearAnger = this.makeFearAngerEmotion(0);
        this.surpriseAntic = this.makeSurpriseAnticipationEmotion(0);
        this.trustLoathing = this.makeTrustLoathingEmotion(0);
        this.moodVector = this.makeMoodVector();
        this.emoteStateRecorder = new EmoteStateRecorder(this);
        this.journal = new JournalRecording(this);
        addObserver(this.journal);
    }

    // EFFECTS: returns journalRecording of EmotionalState
    public JournalRecording getJournal() {
        return this.journal;
    }

    // MODIFIES: this
    // EFFECTS: changes values of emotionalStates four moodVector to those given
    public void alterEmotionalState(int js, int fa, int sa, int tl) {
        this.joySadness.setScaleValue(js);
        this.fearAnger.setScaleValue(fa);
        this.surpriseAntic.setScaleValue(sa);
        this.trustLoathing.setScaleValue(tl);
        notifyObservers();
    }

    // REQUIRES: makeJoySadness, makeFearAnger, makeSurpriseAnticipation, makeTrustLoathing called before
    // EFFECTS: return a mood vector of Emotions
    public ArrayList<Emotion> makeMoodVector() {
        ArrayList<Emotion> vector = new ArrayList<Emotion>();
        populateEmotionVector(vector);
        return vector;
    }

    private void populateEmotionVector(ArrayList<Emotion> vector) {
        vector.add(joySadness);
        vector.add(fearAnger);
        vector.add(surpriseAntic);
        vector.add(trustLoathing);
    }

    // EFFECTS: saves the EmotionalState
    public void save(String filename) {
        emoteStateRecorder.save(filename);
    }

    // MODIFIES: this
    // EFFECTS: loads a EmotionalState changing current one to the one that is being loaded
    public void load(String filename) {
        emoteStateRecorder.load(filename);
    }

    // EFFECTS: prints that the JournalRecording EmotionalState is related to, has been updated
    @Override
    public void update() {
        System.out.println("Associated JournalRecording has been updated.");
    }

}
