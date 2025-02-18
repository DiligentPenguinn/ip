package diligentpenguin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import diligentpenguin.exception.ChatBotException;
import diligentpenguin.task.Deadline;
import diligentpenguin.task.Event;
import diligentpenguin.task.TaskList;
import diligentpenguin.task.ToDo;


class StorageTest {
    // This code is adapted from a conversation with chatGPT
    private static final String TEST_DIR = "src/main/data/";
    private static final String TEST_FILE = "tasks.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        storage = new Storage(TEST_DIR, TEST_DIR + TEST_FILE);
        taskList = new TaskList();
        storage.createSavedDirectoryAndFile(); // Ensure the test file exists
    }

    @AfterEach
    void tearDown() {
        // Clean up test files after execution
        File file = new File(TEST_DIR + TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test if the storage creates the correct directory and file.
     */
    @Test
    void testCreateDirectoryAndFile() {
        File file = new File(TEST_DIR + TEST_FILE);
        assertTrue(file.exists());
    }

    /**
     * Test if tasks are correctly saved to the file.
     */
    @Test
    void testSaveTaskList() throws ChatBotException, IOException {
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new Deadline("Submit report", LocalDate.of(2025, 5, 15)));
        taskList.add(new Event("Conference", LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 3)));

        storage.save(taskList);

        File file = new File(TEST_DIR + TEST_FILE);
        assertTrue(file.exists());

        // Read the saved file and verify its content
        Scanner scanner = new Scanner(file);
        assertEquals("T |   | Buy groceries", scanner.nextLine());
        assertEquals("D |   | Submit report | 15/05/2025", scanner.nextLine());
        assertEquals("E |   | Conference | 01/06/2025 | 03/06/2025", scanner.nextLine());
        scanner.close();
    }

    /**
     * Test if tasks are correctly loaded from the file.
     */
    @Test
    void testLoadTaskList() throws ChatBotException, IOException {
        File file = new File(TEST_DIR + TEST_FILE);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("T | X | Buy groceries\n");
            writer.write("D |  | Submit report | 15/05/2025\n");
            writer.write("E |  | Conference | 01/06/2025 | 03/06/2025\n");
        }

        storage.loadTaskList(taskList);

        assertEquals(3, taskList.getSize());
        assertTrue(taskList.get(0).isDone());
        assertEquals("Submit report", taskList.get(1).getName());
        assertEquals("Conference", taskList.get(2).getName());
    }

    /**
     * Test handling of missing task file.
     */
    @Test
    void testLoadTaskListFileNotFound() {
        Storage nonExistentStorage = new Storage(TEST_DIR, "non_existent.txt");
        TaskList newTaskList = new TaskList();

        assertThrows(ChatBotException.class, () -> nonExistentStorage.loadTaskList(newTaskList));
    }

    /**
     * Test reading a ToDo task string correctly.
     */
    @Test
    void testReadToDoTask() {
        String line = "T | X | Read a book";
        ToDo task = Storage.readTodoTask(line);

        assertNotNull(task);
        assertEquals("Read a book", task.getName());
        assertTrue(task.isDone());
    }

    /**
     * Test reading a Deadline task string correctly.
     */
    @Test
    void testReadDeadlineTask() {
        String line = "D |  | Submit assignment | 20/06/2025";
        Deadline task = Storage.readDeadlineTask(line);

        assertNotNull(task);
        assertEquals("Submit assignment", task.getName());
        assertEquals(LocalDate.of(2025, 6, 20), task.getDeadline());
    }

    /**
     * Test reading an Event task string correctly.
     */
    @Test
    void testReadEventTask() {
        String line = "E | X | Project meeting | 10/07/2025 | 12/07/2025";
        Event task = Storage.readEventTask(line);

        assertNotNull(task);
        assertEquals("Project meeting", task.getName());
        assertEquals(LocalDate.of(2025, 7, 10), task.getStartTime());
        assertEquals(LocalDate.of(2025, 7, 12), task.getEndTime());
        assertTrue(task.isDone());
    }

    /**
     * Test handling of invalid task format.
     */
    @Test
    void testReadTaskInvalidFormat() {
        String invalidLine = "X | Invalid | Task";
        assertThrows(ChatBotException.class, () -> Storage.readTask(invalidLine));
    }
}
