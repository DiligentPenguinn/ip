package diligentpenguin.task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
    // This code is adapted from a conversation with chatGPT
    private Event eventTask;
    private LocalDate startTime;
    private LocalDate endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDate.of(2025, 5, 1);
        endTime = LocalDate.of(2025, 5, 3);
        eventTask = new Event("Conference", startTime, endTime);
    }

    @Test
    void testEventConstructor() {
        assertEquals("Conference", eventTask.getName());
    }

    @Test
    void testToString() {
        String expected = String.format("[E][ ] Conference (from: %s to: %s)",
                startTime.format(Task.getOutputFormatter()),
                endTime.format(Task.getOutputFormatter()));
        assertEquals(expected, eventTask.toString());
    }

    @Test
    void testToSavedString() {
        String expected = String.format("E |   | Conference | %s | %s",
                startTime.format(Task.getInputFormatter()),
                endTime.format(Task.getInputFormatter()));
        assertEquals(expected, eventTask.toSavedString());
    }

    @Test
    void testToEditString() {
        String expected = String.format("update-2 Conference /from %s /to %s",
                startTime.format(Task.getInputFormatter()),
                endTime.format(Task.getInputFormatter()));
        assertEquals(expected, eventTask.toEditString(2));
    }
}
