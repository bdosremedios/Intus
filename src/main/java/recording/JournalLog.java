package recording;

import java.util.HashMap;

// Represents a log of journal recording entries
public class JournalLog {
    private HashMap<String, JournalRecording> log;

    // EFFECTS: creates HashMap<String, JournalRecording> object to store journalRecordings
    public JournalLog() {
        this.log = new HashMap<String, JournalRecording>();
    }

    // EFFECTS: return log HashMap
    public HashMap<String, JournalRecording> getLog() {
        return this.log;
    }

    // EFFECTS: return size of log
    public int getSize() {
        return this.log.size();
    }

    // EFFECTS: returns true if JournalRecording reference is in log
    public boolean checkJournalRecordingInLog(JournalRecording journal) {
        return this.log.containsValue(journal);
    }

    // MODIFIES: this
    // EFFECTS: adds given JournalRecording to log if JournalRecording is not currently in log, returns true if
    //          successful and false if not
    public boolean addJournalRecording(JournalRecording journal) {
        if (!this.checkJournalRecordingInLog(journal)) {
            this.log.put(journal.getHashDateIdentifier(), journal);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: journal is already in log
    // MODIFIES: this
    // EFFECTS: adds given JournalRecording to log
    public void removeJournalRecording(String journalKey) {
        this.log.remove(journalKey);
    }

}
