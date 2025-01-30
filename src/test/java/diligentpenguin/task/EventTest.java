package diligentpenguin.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testToSavedString1() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.inputFormatter);
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.inputFormatter);
        Event task = new Event("homework", startTime, endTime);
        String expectedOutput = "E |   | homework | 20/12/2025 | 21/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    @Test
    public void testToSavedString2() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.inputFormatter);
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.inputFormatter);
        Event task = new Event("homework", startTime, endTime);
        task.setDone();
        String expectedOutput = "E | X | homework | 20/12/2025 | 21/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    @Test
    public void testToString1() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.inputFormatter);
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.inputFormatter);
        Event task = new Event("homework", startTime, endTime);
        String expectedOutput = "[E][ ] homework " +
                "(from: Saturday, December 20, 2025 to: Sunday, December 21, 2025)";
        assertEquals(expectedOutput, task.toString());
    }

    @Test
    public void testToString2() {
        LocalDate startTime = LocalDate.parse("20/12/2025", Task.inputFormatter);
        LocalDate endTime = LocalDate.parse("21/12/2025", Task.inputFormatter);
        Event task = new Event("homework", startTime, endTime);
        task.setDone();
        String expectedOutput = "[E][X] homework " +
                "(from: Saturday, December 20, 2025 to: Sunday, December 21, 2025)";
        assertEquals(expectedOutput, task.toString());
    }
}
