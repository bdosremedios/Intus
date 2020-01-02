package recording;

import model.EmotionalState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JournalRecordingTest {
    JournalRecording journal;
    Date epochDate;

    @BeforeEach
    public void runBefore() {
        journal = new JournalRecording();
    }

    @Test
    public void testConstructor() {
        Date epochDate = new Date(0);
        assertEquals("", journal.getEntryText());
        assertEquals(0, journal.getEntryDate().compareTo(epochDate));
        assertTrue(journal.getEmoteState().equals(new EmotionalState()));
        assertEquals("700101000000", journal.getHashDateIdentifier());

        try {
            EmotionalState emoteState = new EmotionalState(10, 20, 30, 40, new JournalRecording());
            journal = new JournalRecording(emoteState);
            assertEquals("", journal.getEntryText());
            assertEquals(0, journal.getEntryDate().compareTo(epochDate));
            assertTrue(journal.getEmoteState().equals(emoteState));
            assertEquals("700101000000", journal.getHashDateIdentifier());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRecordJournalRecordingEntry() throws InterruptedException {
        // Test if message changes to correct message and date changes from initial date.
        journal.recordJournalRecordingEntry("This is a new message.");
        assertNotEquals(epochDate, journal.getEntryDate());
        assertEquals("This is a new message.", journal.getEntryText());

        // Test if a follow up call to method changes journal from the previous date and to a different entry text
        Date lastDate = journal.getEntryDate();
        TimeUnit.SECONDS.sleep(1);
        journal.recordJournalRecordingEntry("This is another message.");
        assertNotEquals(lastDate, journal.getEntryDate());
        assertEquals("This is another message.", journal.getEntryText());

        // Test if new line and tab can be properly recorded
        journal.recordJournalRecordingEntry("\n\t");
        assertEquals("\n\t", journal.getEntryText());
    }

    @Test
    public void testCheckEarlierEntry() {

        // Set journal earlier than journal2
        JournalRecording journal2 = new JournalRecording();
        journal.setEntryDate(new Date(100));
        journal2.setEntryDate(new Date(200));

        assertTrue(journal.checkEarlierEntry(journal2));
        assertFalse(journal2.checkEarlierEntry(journal));

        // Checks if returns false with same date
        assertFalse(journal.checkEarlierEntry(journal));
    }

    @Test
    public void testSaveLoad() {

        // Test exception
        try {
            File file = new File("./data/journalrecordingtest.txt");
            file.delete();
        } catch (Exception e) {
            System.out.println(e);
        }
        journal.load("./data/journalrecordingtest.txt");

        journal.setEntryText("aaaaaaa");
        journal.setEntryDate(new Date(1000000));
        journal.save("./data/journalrecordingtest.txt");

        journal.setEntryText("bbbbbbb");
        journal.setEntryDate(new Date(0));

        journal.load("./data/journalrecordingtest.txt");
        assertEquals("aaaaaaa", journal.getEntryText());
        assertEquals(0, journal.getEntryDate().compareTo(new Date(1000000)));
    }

    @Test
    public void testPrintDoesNotBreak() {
        journal.print();
    }

    @Test
    public void testSaveCurrentWithEmotionalState() {
        journal.saveCurrentWithEmotionalState();
        Date recordedDate = journal.getEntryDate();
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String stringDate = dateFormat.format(recordedDate);
        try {
            File filefound1 = new File("./data/journal" + stringDate + ".txt");
            System.out.println("./data/journal" + stringDate + ".txt");
            assertTrue(filefound1.canRead());
            File filefound2 = new File("./data/emotionalstate" + stringDate + ".txt");
            assertTrue(filefound2.canRead());
            filefound1.delete();
            filefound2.delete();
        } catch (Exception e) {
            fail("Error in finding files.");
        }
    }

    @Test
    public void testEqualsHashcode() {
        JournalRecording journal1 = new JournalRecording();
        JournalRecording journal2 = new JournalRecording();
        journal1.setEntryText("aaaaaaaa");
        journal1.setEntryDate(new Date(100));
        journal2.setEntryText("aaaaaaaa");
        journal2.setEntryDate(new Date(100));
        assertTrue(journal1.equals(journal1));
        assertTrue(journal1.equals(journal2));
        assertFalse(journal1.equals(null));
        assertFalse(journal1.equals("Wrong Object"));
        assertEquals(journal1.hashCode(), journal2.hashCode());
    }

    @Test
    public void testSetToCurrentDateNoBreak() {
        JournalRecording journal1 = new JournalRecording();
        journal1.setToCurrentDate();
    }

    @Test
    public void testCreateStringDate() {
        JRDateManager jrd = new JRDateManager();
        Date d = new Date(0);
        assertEquals("700101000000", jrd.createStringDate(d));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTF"));
            d = dateFormat.parse("120323165126");
            assertEquals("120323165126", jrd.createStringDate(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
