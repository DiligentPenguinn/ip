package diligentpenguin.task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for class <code>Event</code>
 */
public class EventTest {
    /**
     * Tests the <code>toSavedString</code> method for an incomplete task.
     */
    @Test
    public void testToSavedString1() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.getInputFormatter());
        Event task = new Event("homework", startTime, endTime);
        String expectedOutput = "E |   | homework | 20/12/2025 | 21/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    /**
     * Tests the <code>toSavedString</code> method for a completed task.
     */
    @Test
    public void testToSavedString2() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.getInputFormatter());
        Event task = new Event("homework", startTime, endTime);
        task.setDone();
        String expectedOutput = "E | X | homework | 20/12/2025 | 21/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    /**
     * Tests the <code>toString</code> method for an incomplete task.
     */
    @Test
    public void testToString1() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.getInputFormatter());
        Event task = new Event("homework", startTime, endTime);
        String expectedOutput = "[E][ ] homework "
                + "(from: Saturday, December 20, 2025 to: Sunday, December 21, 2025)";
        assertEquals(expectedOutput, task.toString());
    }

    /**
     * Tests the <code>toString</code> method for a completed task.
     */
    @Test
    public void testToString2() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.getInputFormatter());
        Event task = new Event("homework", startTime, endTime);
        task.setDone();
        String expectedOutput = "[E][X] homework "
                + "(from: Saturday, December 20, 2025 to: Sunday, December 21, 2025)";
        assertEquals(expectedOutput, task.toString());
    }
}
