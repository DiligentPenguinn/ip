package diligentpenguin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import diligentpenguin.task.TaskList;

public class DiligentPenguinTest {
    private DiligentPenguin chatbot;

    @BeforeEach
    void setUp() {
        chatbot = new DiligentPenguin("src/main/data/", "tasks.txt");
    }

    /**
     * Test if the chatbot initializes correctly.
     */
    @Test
    void testConstructor() {
        assertNotNull(chatbot);
        assertNotNull(chatbot.getTasks());
        assertFalse(chatbot.isOver());
    }

    /**
     * Test if `getTasks()` returns a valid TaskList object.
     */
    @Test
    void testGetTasks() {
        TaskList tasks = chatbot.getTasks();
        assertNotNull(tasks);
    }

    /**
     * Test if `isOver()` is `false` initially.
     */
    @Test
    void testIsOverInitialState() {
        assertFalse(chatbot.isOver());
    }

    /**
     * Test if `setOver()` properly updates `isOver()` to `true`.
     */
    @Test
    void testSetOver() {
        chatbot.setOver();
        assertTrue(chatbot.isOver());
    }
}
