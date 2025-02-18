package diligentpenguin.task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represent unit tests for class <code>Deadline</code>
 */
class DeadlineTest {
    // This code is adapted from a conversation with chatGPT
    private Deadline deadlineTask;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2025, 3, 10);
        deadlineTask = new Deadline("Submit Report", date);
    }

    @Test
    void testDeadlineConstructor() {
        assertEquals("Submit Report", deadlineTask.getName());
    }

    @Test
    void testToString() {
        String expected = String.format("[D][ ] Submit Report (by: %s)",
                date.format(Task.getOutputFormatter()));
        assertEquals(expected, deadlineTask.toString());
    }

    @Test
    void testToSavedString() {
        String expected = String.format("D |   | Submit Report | %s",
                date.format(Task.getInputFormatter()));
        assertEquals(expected, deadlineTask.toSavedString());
    }

    @Test
    void testToEditString() {
        String expected = String.format("update-1 Submit Report /by %s",
                date.format(Task.getInputFormatter()));
        assertEquals(expected, deadlineTask.toEditString(1));
    }
}
