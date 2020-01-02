package recording;

import model.EmotionalState;
import model.Observer;
import model.Subject;

import java.io.*;
import java.util.Date;

// Represents an individual journal recording
public class JournalRecording extends Subject implements Recordable, Serializable, Observer {
    private String entryText;
    private EmotionalState emoteState;
    private JRDateManager dateManager;

    // EFFECTS: Creates JournalRecording object setting entryText to a blank string and entryDate to epoch date
    //          and a blank emotional state;
    public JournalRecording() {
        this.dateManager = new JRDateManager();
        this.entryText = "";
        this.emoteState = new EmotionalState(this);
        addObserver(this.emoteState);
    }

    // EFFECTS: Creates JournalRecording object with a corresponding emotionalState
    public JournalRecording(EmotionalState emoteState) {
        this.dateManager = new JRDateManager();
        this.entryText = "";
        this.emoteState = emoteState;
        addObserver(this.emoteState);
    }

    // EFFECTS: return current entryDate
    public Date getEntryDate() {
        return dateManager.getEntryDate();
    }

    // EFFECTS: returns current entryText
    public String getEntryText() {
        return this.entryText;
    }

    // EFFECTS: returns current emotionalState
    public EmotionalState getEmoteState() {
        return this.emoteState;
    }

    // EFFECTS: returns current hashDateIdentifier
    public String getHashDateIdentifier() {
        return dateManager.getHashDateIdentifier();
    }

    // MODIFIES: this
    // EFFECTS: set dateManager to given date
    public void setEntryDate(Date d) {
        dateManager.setEntryDate(d);
    }

    // MODIFIES: this
    // EFFECTS: set entryText to given string
    public void setEntryText(String s) {
        this.entryText = s;
    }

    // MODIFIES: this
    // EFFECTS: sets dateManager to currentDate
    public void setToCurrentDate() {
        dateManager.setToCurrentDate();
    }

    // MODIFIES: this
    // EFFECTS: records new journal by setting entryDate to current date and time, and setting entryText to given
    //          string
    public void recordJournalRecordingEntry(String message) {
        dateManager.setToCurrentDate();
        this.setEntryText(message);
        notifyObservers();
    }

    // EFFECTS: returns true if current JournalRecording is of an earlier entryDate then given JournalRecording
    public boolean checkEarlierEntry(JournalRecording otherJournal) {
        return dateManager.checkEarlierEntry(otherJournal);
    }

    // EFFECTS: prints JournalRecording entryText and entryDate
    public void print() {
        System.out.println("Journal Recorded on: " + dateManager.getEntryDate() + "\n" + this.entryText);
    }

    // EFFECTS: saves the JournalRecording
    public void save(String filename) {
        try {
            FileOutputStream fout = new FileOutputStream(new File(filename));
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this);
            fout.close();
            oout.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a journal recording changing current journal recording's entries to the one loaded
    public void load(String filename) {
        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(fin);
            JournalRecording loadedJournal = (JournalRecording) oin.readObject();
            setFromJournal(loadedJournal);
            fin.close();
            oin.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: substitutes journal's text and date to that of given journal
    private void setFromJournal(JournalRecording loadedJournal) {
        dateManager.setEntryDate(loadedJournal.getEntryDate());
        this.setEntryText(loadedJournal.getEntryText());
        notifyObservers();
    }

    // EFFECTS: saves journal and journal's emoteState in data with unique hashdate names
    public void saveCurrentWithEmotionalState() {
        this.save("./data/journal" + dateManager.getHashDateIdentifier() + ".txt");
        this.emoteState.save("./data/emotionalstate" + dateManager.getHashDateIdentifier() + ".txt");
    }

    // EFFECTS: prints that the EmotionalState JournalRecording is related to, has been updated
    @Override
    public void update() {
        System.out.println("Associated EmotionalState has been updated.");
    }

    // EFFECTS: returns true if entry text and date match
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JournalRecording that = (JournalRecording) o;

        if (!entryText.equals(that.entryText)) {
            return false;
        } else {
            return getEntryDate().equals(that.getEntryDate());
        }
    }

    // EFFECTS: returns hashcode of JournalRecording
    @Override
    public int hashCode() {
        int result = entryText.hashCode();
        result = 31 * result + getEntryDate().hashCode();
        return result;
    }

}
