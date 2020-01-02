package recording;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// Date manager for a JournalRecording object
public class JRDateManager implements Serializable {

    private Date entryDate;
    private String hashDateIdentifier;

    // EFFECTS: created a JRDateManager, sets entry date and generates a corresponding hashDate
    public JRDateManager() {
        this.entryDate = new Date(0);
        this.hashDateIdentifier = createStringDate(this.entryDate);
    }

    // EFFECTS: return current entryDate
    public Date getEntryDate() {
        return entryDate;
    }

    // EFFECTS: returns current hashDateIdentifier
    public String getHashDateIdentifier() {
        return this.hashDateIdentifier;
    }

    // MODIFIES: this
    // EFFECTS: set entryDate to given date and update hashDateIdentifier accordingly
    public void setEntryDate(Date d) {
        this.entryDate = d;
        this.hashDateIdentifier = createStringDate(this.entryDate);
    }

    // MODIFIES: this
    // EFFECTS: set entryDate to current date and update hashDateIdentifier accordingly
    public void setToCurrentDate() {
        this.entryDate = new Date();
        this.hashDateIdentifier = this.createStringDate(this.entryDate);
    }

    // EFFECTS: creates a String identifier for a given date for hashMap in form yyMMddHHmmss
    public String createStringDate(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(d);
    }

    // EFFECTS: returns true if current entryDate is of an earlier time then of given JournalRecording
    public boolean checkEarlierEntry(JournalRecording otherJournal) {
        int dateIntDiff = this.entryDate.compareTo(otherJournal.getEntryDate());
        return (dateIntDiff < 0);
    }

}
