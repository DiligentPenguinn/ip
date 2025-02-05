package diligentpenguin.task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Represent unit tests for class <code>Deadline</code>
 */
public class DeadlineTest {
    /**
     * Tests the <code>toSavedString</code> method for an incomplete task.
     */
    @Test
    public void testToSavedString1() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        Deadline task = new Deadline("homework", deadline);
        String expectedOutput = "D |   | homework | 20/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    /**
     * Tests the <code>toSavedString</code> method for a completed task.
     */
    @Test
    public void testToSavedString2() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        Deadline task = new Deadline("homework", deadline);
        task.setDone();
        String expectedOutput = "D | X | homework | 20/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    /**
     * Tests the <code>toString</code> method for an incomplete task.
     */
    @Test
    public void testToString1() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        Deadline task = new Deadline("homework", deadline);
        String expectedOutput = "[D][ ] homework (by: Saturday, December 20, 2025)";
        assertEquals(expectedOutput, task.toString());
    }

    /**
     * Tests the <code>toString</code> method for a completed task.
     */
    @Test
    public void testToString2() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.getInputFormatter());
        Deadline task = new Deadline("homework", deadline);
        task.setDone();
        String expectedOutput = "[D][X] homework (by: Saturday, December 20, 2025)";
        assertEquals(expectedOutput, task.toString());
    }
}
