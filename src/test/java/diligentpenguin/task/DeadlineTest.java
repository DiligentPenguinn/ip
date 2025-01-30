package diligentpenguin.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

public class DeadlineTest {
    @Test
    public void testToSavedString1() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.inputFormatter);
        Deadline task = new Deadline("homework", deadline);
        String expectedOutput = "D |   | homework | 20/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    @Test
    public void testToSavedString2() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.inputFormatter);
        Deadline task = new Deadline("homework", deadline);
        task.setDone();
        String expectedOutput = "D | X | homework | 20/12/2025";
        assertEquals(expectedOutput, task.toSavedString());
    }

    @Test
    public void testToString1() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.inputFormatter);
        Deadline task = new Deadline("homework", deadline);
        String expectedOutput = "[D][ ] homework (by: Saturday, December 20, 2025)";
        assertEquals(expectedOutput, task.toString());
    }

    @Test
    public void testToString2() {
        LocalDate deadline = LocalDate.parse("20/12/2025", Task.inputFormatter);
        Deadline task = new Deadline("homework", deadline);
        task.setDone();
        String expectedOutput = "[D][X] homework (by: Saturday, December 20, 2025)";
        assertEquals(expectedOutput, task.toString());
    }
}
