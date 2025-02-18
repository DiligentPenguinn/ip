package diligentpenguin.task;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToDoTest {
    // This code is adapted from a conversation with chatGPT
    private ToDo todoTask;

    @BeforeEach
    void setUp() {
        todoTask = new ToDo("Buy groceries");
    }

    @Test
    void testToDoConstructor() {
        assertEquals("Buy groceries", todoTask.getName());
    }

    @Test
    void testToString() {
        assertEquals("[T][ ] Buy groceries", todoTask.toString());
    }

    @Test
    void testToSavedString() {
        assertEquals("T |   | Buy groceries", todoTask.toSavedString());
    }

    @Test
    void testToEditString() {
        assertEquals("update-1 Buy groceries", todoTask.toEditString(1));
    }
}
