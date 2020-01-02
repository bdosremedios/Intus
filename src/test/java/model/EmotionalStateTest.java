package model;

import exceptions.InvalidIntegerException;
import org.junit.jupiter.api.Test;
import recording.JournalRecording;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmotionalStateTest {

    @Test
    public void testConstructor() {
        try {
            JournalRecording journaltemp = new JournalRecording();
            journaltemp.setEntryDate(new Date(1));
            journaltemp.setEntryText("aaaaaaa");
            EmotionalState emoteState = new EmotionalState(1,2,3,4, journaltemp);
            ArrayList<Emotion> moodVector = emoteState.getMoodVector();
            ArrayList<String> nameList = new ArrayList<String>();
            Collections.addAll(nameList, "Joy", "Sadness", "Fear", "Anger", "Surprise", "Anticipation", "Trust", "Loathing");
            List<Integer> countList = Arrays.asList(0, 1, 2, 3);
            for (int i : countList) {
                Emotion currentEmotion = moodVector.get(i);
                assertEquals(nameList.get(2*i), currentEmotion.getHighEndEmotion());
                assertEquals(nameList.get(2*i+1), currentEmotion.getLowEndEmotion());
                assertEquals(i+1, currentEmotion.getScaleValue());
            }
            assertEquals(0, emoteState.getJournal().getEntryDate().compareTo(new Date(1)));
            assertEquals("aaaaaaa", emoteState.getJournal().getEntryText());
        } catch (InvalidIntegerException e) {
            fail("Integers are valid so should be no exception.");
        }
    }

    @Test
    public void testConstructorInvalidInt() {
        try {
            EmotionalState emoteState = new EmotionalState(-1,0,0,0,  new JournalRecording());
            fail("Integers invalid so should have thrown exception.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testConstructorInvalidIntSecond() {
        try {
            EmotionalState emoteState = new EmotionalState(0,-1,0,0,  new JournalRecording());
            fail("Integers invalid so should have thrown exception.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testConstructorInvalidIntThird() {
        try {
            EmotionalState emoteState = new EmotionalState(0,0,-1,0,  new JournalRecording());
            fail("Integers invalid so should have thrown exception.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testConstructorInvalidIntFourth() {
        try {
            EmotionalState emoteState = new EmotionalState(0,0,0,-1,  new JournalRecording());
            fail("Integers invalid so should have thrown exception.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testOverloadedConstructor() {
        EmotionalState emoteState = new EmotionalState();
        ArrayList<Emotion> moodVector = emoteState.getMoodVector();
        ArrayList<String> nameList = new ArrayList<String>();
        Collections.addAll(nameList, "Joy", "Sadness", "Fear", "Anger", "Surprise", "Anticipation", "Trust", "Loathing");
        List<Integer> countList = Arrays.asList(0, 1, 2, 3);
        for (int i : countList) {
            Emotion currentEmotion = moodVector.get(i);
            assertEquals(nameList.get(2*i), currentEmotion.getHighEndEmotion());
            assertEquals(nameList.get(2*i+1), currentEmotion.getLowEndEmotion());
            assertEquals(0, currentEmotion.getScaleValue());
        }
    }

    @Test
    public void testAlterEmotionalState() {
        EmotionalState emoState = new EmotionalState();
        emoState.alterEmotionalState(25,25,25,25);
        for (Emotion e : emoState.getMoodVector()) {
            assertEquals(e.getScaleValue(), 25);
        }
        assertTrue(emoState.getJournal().getEmoteState().equals(emoState));
    }

    @Test
    public void testGeneralEmotionalStateExtension() {
        try {
            GeneralEmotionalState generalEmotionalStateTest = new EmotionalState(1, 2, 3, 4,  new JournalRecording());
            Emotion joySadness = generalEmotionalStateTest.makeJoySadnessEmotion(20);
            Emotion fearAnger = generalEmotionalStateTest.makeFearAngerEmotion(20);
            Emotion surpriseAnticipation = generalEmotionalStateTest.makeSurpriseAnticipationEmotion(20);
            Emotion trustLoathing = generalEmotionalStateTest.makeTrustLoathingEmotion(20);
            ArrayList<Emotion> moodVector = generalEmotionalStateTest.makeMoodVector();
            List<Integer> countList = Arrays.asList(0, 1, 2, 3);
            for (int i : countList) {
                Emotion emotionTemp = moodVector.get(i);
                assertEquals(i+1, emotionTemp.getScaleValue());
            }
            assertEquals(20, joySadness.getScaleValue());
            assertEquals(20, fearAnger.getScaleValue());
            assertEquals(20, surpriseAnticipation.getScaleValue());
            assertEquals(20, trustLoathing.getScaleValue());
            generalEmotionalStateTest.print();
        } catch (InvalidIntegerException e) {
            fail("All integers are valid so should not be exception");
        }
    }

    @Test
    public void testSaveLoad() {
        // Test exception
        try {
            File file = new File("./data/emotestatetest.txt");
            file.delete();
        } catch (Exception e) {
            ;
        }
        try {
            EmotionalState emoteState = new EmotionalState(1,2,3,4,  new JournalRecording());
            emoteState.save("./data/emotestatetest.txt");
            EmotionalState newEmoteState = new EmotionalState(40,40,40,40,  new JournalRecording());
            newEmoteState.load("./data/emotestatetest.txt");
            ArrayList<Emotion> moodVector = newEmoteState.getMoodVector();
            List<Integer> countList = Arrays.asList(0, 1, 2, 3);
            for (int i : countList) {
                Emotion currentEmotion = moodVector.get(i);
                assertEquals(i+1, currentEmotion.getScaleValue());
            }
        } catch (InvalidIntegerException e) {
            fail("All integers are valid so should not be exception");
        }

        // Test exception
        EmotionalState emoteState = new EmotionalState();
        emoteState.load("./data/doesntexistemostate.txt");

    }

    @Test
    public void testPrintDoesNotBreak() {
        try {
            EmotionalState emoteState = new EmotionalState(1,2,3,4,  new JournalRecording());
            emoteState.print();
        } catch (InvalidIntegerException e) {
            fail("All integers are valid so should not be exception");
        }
    }

    @Test
    public void testEqualsHashcode() {
        try {
            EmotionalState emoteState1 = new EmotionalState(1,2,3,4,  new JournalRecording());
            EmotionalState emoteState2 = new EmotionalState(1,2,3,4,  new JournalRecording());
            EmotionalState emoteState3 = new EmotionalState(10,2,3,4,  new JournalRecording());
            EmotionalState emoteState4 = new EmotionalState(1,20,3,4,  new JournalRecording());
            EmotionalState emoteState5 = new EmotionalState(1,2,30,4,  new JournalRecording());
            assertTrue(emoteState1.equals(emoteState1));
            assertTrue(emoteState1.equals(emoteState2));
            assertFalse(emoteState1.equals(null));
            assertFalse(emoteState1.equals("Wrong Object"));
            assertFalse(emoteState1.equals(emoteState3));
            assertFalse(emoteState1.equals(emoteState4));
            assertFalse(emoteState1.equals(emoteState5));
            assertEquals(emoteState1.hashCode(), emoteState2.hashCode());
        } catch (InvalidIntegerException e) {
            fail("All integers are valid so should not be exception");
        }
    }

    @Test
    public void testPrintDoesntBreak() {
        EmotionalState emoteState1 = new EmotionalState(1,2,3,4,  new JournalRecording());
        emoteState1.print();
    }

}
