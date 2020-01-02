package recording;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JournalLogTest {
    JournalRecording journal1;
    JournalRecording journal2;
    JournalRecording journal3;
    JournalLog log;

    @BeforeEach
    public void runBefore() {
        // Create journal1, journal2, and journal3 such that the entryDates are differing and later respectively with
        // the labelled number
        journal1 = new JournalRecording();
        journal1.setEntryDate(new Date(1000));
        journal2 = new JournalRecording();
        journal2.setEntryDate(new Date(2000));
        journal3 = new JournalRecording();
        journal3.setEntryDate(new Date(3000));
        log = new JournalLog();
    }

    @Test
    public void testConstructor() {
        assertEquals(log.getSize(), 0);
    }

    @Test
    public void testCheckJournalRecordingInLog() {
        assertFalse(log.checkJournalRecordingInLog(journal1));
        log.addJournalRecording(journal1);
        assertTrue(log.checkJournalRecordingInLog(journal1));
        log.addJournalRecording(journal2);
        assertTrue(log.checkJournalRecordingInLog(journal1));
        assertTrue(log.checkJournalRecordingInLog(journal2));
    }

    @Test
    public void testAddJournalRecording() {
        journal1.setEntryText("Test string is maintained.");
        assertTrue(log.addJournalRecording(journal1));
        assertEquals(1, log.getSize());
        assertTrue(log.addJournalRecording(journal2));
        assertEquals(2, log.getSize());
        assertFalse(log.addJournalRecording(journal1));
        assertFalse(log.addJournalRecording(journal2));
        assertEquals(2, log.getSize());

        HashMap<String, JournalRecording> internalLog = log.getLog();
        JournalRecording firstJournal = internalLog.get(journal1.getHashDateIdentifier());
        assertEquals("Test string is maintained.", firstJournal.getEntryText());

    }

    @Test
    public void testRemoveJournalRecording() {
        log.addJournalRecording(journal1);
        assertEquals(1, log.getSize());
        log.removeJournalRecording(journal1.getHashDateIdentifier());
        assertEquals(0, log.getSize());
        log.addJournalRecording(journal1);
        log.addJournalRecording(journal2);
        assertEquals(2, log.getSize());
        log.removeJournalRecording(journal2.getHashDateIdentifier());
        assertTrue(log.checkJournalRecordingInLog(journal1));
        assertFalse(log.checkJournalRecordingInLog(journal2));
    }

}
