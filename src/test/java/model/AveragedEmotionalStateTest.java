package model;

import exceptions.InvalidIntegerException;
import exceptions.NoRecordedEmotionalStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recording.JournalRecording;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AveragedEmotionalStateTest {
    EmotionalState emoState1;
    EmotionalState emoState2;
    EmotionalState emoState3;
    EmotionalState emoState100;

    @BeforeEach
    public void runBefore() {
        try {
            emoState1 = new EmotionalState(10, 0, 10, 100, new JournalRecording());
            emoState2 = new EmotionalState(10, 0, 20, 100, new JournalRecording());
            emoState3 = new EmotionalState(10, 0, 31, 100, new JournalRecording());
            emoState100 = new EmotionalState(100, 100, 100, 100, new JournalRecording());
            emoState1.save("./data/testemostate1.txt");
            emoState2.save("./data/testemostate2.txt");
            emoState3.save("./data/testemostate3.txt");
            emoState3.save("./data/testnotusedemostate4.txt");
            emoState100.save("./data/testemostate4.txt");
            emoState100.save("./data/testemostate5.txt");
        } catch (InvalidIntegerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() {
        // Try and remove any previous adds of testemostate4 and 5
        try {
            new File("./data/testemostate4.txt").delete();
            new File("./data/testemostate5.txt").delete();
        } catch (Exception e) {
            ;
        }
        try {
            AveragedEmotionalState avgEmoState = new AveragedEmotionalState("./data/", "testemostate");
            ArrayList<Emotion> moodVector = avgEmoState.getMoodVector();
            assertEquals(10, moodVector.get(0).getScaleValue());
            assertEquals(0, moodVector.get(1).getScaleValue());
            assertEquals(20, moodVector.get(2).getScaleValue());
            assertEquals(100, moodVector.get(3).getScaleValue());
        } catch (NoRecordedEmotionalStates e) {
            fail("Should be three emotional states for constructor to load so should not fail.");
        }
    }

    @Test
    public void testNoEmoteStateConstructor() {
        try {
            AveragedEmotionalState mtTest = new AveragedEmotionalState("./data/", "nokeyword");
            fail("Should be no emotional states loadable in the constructor");
        } catch (NoRecordedEmotionalStates e) {
            ;
        }
    }

    @Test
    public void testSaveLoad() {
        AveragedEmotionalState avgEmoteState = null;

        // Try and remove any previous adds of testemostate4 and 5
        try {
            new File("./data/testemostate4.txt").delete();
            new File("./data/testemostate5.txt").delete();
        } catch (Exception e) {
            ;
        }

        try {
            avgEmoteState = new AveragedEmotionalState("./data/", "testemostate");
        } catch (NoRecordedEmotionalStates e) {
            fail("Should be emotional states for the constructor.");
        }

        avgEmoteState.save("./data/avgemostatetest.txt");

        try {
            emoState100 = new EmotionalState(100, 100, 100, 100, new JournalRecording());
            emoState100.save("./data/testemostate4.txt");
            emoState100.save("./data/testemostate5.txt");
        } catch (InvalidIntegerException e) {
            e.printStackTrace();
        }

        try {
            avgEmoteState = new AveragedEmotionalState("./data/", "testemostate");
        } catch (NoRecordedEmotionalStates e) {
            assertTrue(false);
        }
        avgEmoteState.load("./data/avgemostatetest.txt");

        ArrayList<Emotion> moodVector = avgEmoteState.getMoodVector();
        List<Integer> countList = Arrays.asList(0, 1, 2, 3);
        List<Integer> avgList = Arrays.asList(10, 0, 20, 100);
        for (int i : countList) {
            Emotion currentEmotion = moodVector.get(i);
            assertEquals(avgList.get(i), currentEmotion.getScaleValue());
        }

        // Test exception
        avgEmoteState.load("./data/doesntexistemostate.txt");
    }

}
