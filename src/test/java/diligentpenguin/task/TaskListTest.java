package diligentpenguin.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTest {
    // This code is adapted from a conversation with chatGPT
    private TaskList taskList;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        task1 = new ToDo("Read book");
        task2 = new Deadline("Submit assignment", LocalDate.of(2025, 6, 15));
        taskList.add(task1);
        taskList.add(task2);
    }

    @Test
    void testAddTask() {
        assertEquals(2, taskList.getSize());
        assertEquals(task1, taskList.get(0));
    }

    @Test
    void testRemoveTask() {
        taskList.remove(0);
        assertEquals(1, taskList.getSize());
    }

    @Test
    void testFindTask() {
        TaskList found = taskList.find("Read");
        assertEquals(1, found.getSize());
        assertEquals(task1, found.get(0));
    }

    @Test
    void testFinishTask() {
        taskList.finish(1);
        assertTrue(taskList.get(1).isDone());
    }

    @Test
    void testUnfinishTask() {
        taskList.finish(0);
        taskList.unfinish(0);
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void testIsEmpty() {
        assertFalse(taskList.isEmpty());
        taskList.remove(1);
        taskList.remove(0);
        assertTrue(taskList.isEmpty());
    }

    @Test
    void testGetAllTasks() {
        assertEquals(2, taskList.getAllTasks().size());
    }
}
