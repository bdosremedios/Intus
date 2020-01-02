package model;

import java.io.*;

// Recording manager for GeneralEmotionalState objects
public class EmoteStateRecorder implements Serializable {
    private GeneralEmotionalState emoteStateRecordingFor;

    // EFFECTS: sets the emotionalState to record for
    public EmoteStateRecorder(GeneralEmotionalState emoteStateRecordingFor) {
        this.emoteStateRecordingFor = emoteStateRecordingFor;
    }

    // EFFECTS: saves the GeneralEmotionalState
    public void save(String filename) {
        try {
            FileOutputStream fout = new FileOutputStream(new File(filename));
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(emoteStateRecordingFor);
            fout.close();
            oout.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a GeneralEmotionalState changing this to the one that is being loaded
    public void load(String filename) {
        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(fin);
            GeneralEmotionalState loadEmoteState = (GeneralEmotionalState) oin.readObject();
            setAllFromEmoteState(loadEmoteState);
            fin.close();
            oin.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void setAllFromEmoteState(GeneralEmotionalState loadEmoteState) {
        emoteStateRecordingFor.joySadness = loadEmoteState.joySadness;
        emoteStateRecordingFor.fearAnger = loadEmoteState.fearAnger;
        emoteStateRecordingFor.surpriseAntic = loadEmoteState.surpriseAntic;
        emoteStateRecordingFor.trustLoathing = loadEmoteState.trustLoathing;
        emoteStateRecordingFor.moodVector = loadEmoteState.moodVector;
    }

}
