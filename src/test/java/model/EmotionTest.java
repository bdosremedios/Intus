package model;

import exceptions.InvalidIntegerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmotionTest {

    @Test
    public void testConstructor() {
        try {
            Emotion midEmotion = new Emotion("a", "b", 50);
            assertEquals("a", midEmotion.getHighEndEmotion());
            assertEquals("b", midEmotion.getLowEndEmotion());
            assertEquals(50, midEmotion.getScaleValue());
        } catch (InvalidIntegerException e) {
            fail("Valid integer so no exception should be thrown.");
        }
    }

    @Test
    public void testConstructorMin() {
        try {
            Emotion midEmotion = new Emotion("a", "b", 0);
            assertEquals("a", midEmotion.getHighEndEmotion());
            assertEquals("b", midEmotion.getLowEndEmotion());
            assertEquals(0, midEmotion.getScaleValue());
        } catch (InvalidIntegerException e) {
            fail("Valid integer so no exception should be thrown.");
        }
    }

    @Test
    public void testConstructorMax() {
        try {
            Emotion midEmotion = new Emotion("a", "b", 100);
            assertEquals("a", midEmotion.getHighEndEmotion());
            assertEquals("b", midEmotion.getLowEndEmotion());
            assertEquals(100, midEmotion.getScaleValue());
        } catch (InvalidIntegerException e) {
            fail("Valid integer so no exception should be thrown.");
        }
    }

    @Test
    public void testConstructorExceptionIntTooLow() {
        try {
            Emotion midEmotion = new Emotion("a", "b", -1);
            fail("Integer Invalid so exception should have been thrown.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testConstructorExceptionIntTooHigh() {
        try {
            Emotion midEmotion = new Emotion("a", "b", 101);
            fail("Integer Invalid so exception should have been thrown.");
        } catch (InvalidIntegerException e) {
            ;
        }
    }

    @Test
    public void testEqualsHashcode() {
        try {
            Emotion emo1 = new Emotion("a", "b", 50);
            Emotion emo2 = new Emotion("a", "b", 50);
            Emotion emo3 = new Emotion("b", "b", 50);
            Emotion emo4 = new Emotion("a", "c", 50);
            Emotion emo5 = new Emotion("a", "b", 51);
            assertTrue(emo1.equals(emo1));
            assertTrue(emo1.equals(emo2));
            assertFalse(emo1.equals(null));
            assertFalse(emo1.equals("Wrong Object"));
            assertFalse(emo1.equals(emo3));
            assertFalse(emo1.equals(emo4));
            assertFalse(emo1.equals(emo5));
            assertEquals(emo1.hashCode(), emo2.hashCode());
        } catch (InvalidIntegerException e) {
            fail("Valid integer so no exception should be thrown.");
        }
    }


}
