package diligentpenguin.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import diligentpenguin.DiligentPenguin;
import diligentpenguin.Storage;
import diligentpenguin.Ui;
import diligentpenguin.exception.ChatBotException;
import diligentpenguin.exception.InvalidDateTimeFormatException;
import diligentpenguin.exception.ToDoException;
import diligentpenguin.exception.UnknownCommandException;
import diligentpenguin.task.Deadline;
import diligentpenguin.task.Event;
import diligentpenguin.task.Task;
import diligentpenguin.task.ToDo;

public class ParserTest {
    // This code is adapted from a conversation with chatGPT
    private Parser parser;
    private DiligentPenguin chatbot;
    private Storage storage;
    private Ui ui;

    @BeforeEach
    void setUp() {
        chatbot = new DiligentPenguin("src/main/data/", "tasks.txt");
        storage = new Storage("src/main/data/", "tasks.txt");
        ui = new Ui();
        parser = new Parser(chatbot, ui, storage);
    }

    /**
     * Test processing a valid `ToDo` task.
     */
    @Test
    void testProcessToDoTask() throws ChatBotException {
        ToDo task = parser.processToDoTask("Read a book");
        assertNotNull(task);
        assertEquals("Read a book", task.getName());
    }

    /**
     * Test processing an invalid `ToDo` task (empty input).
     */
    @Test
    void testProcessToDoTaskInvalid() {
        assertThrows(ToDoException.class, () -> parser.processToDoTask(""));
    }

    /**
     * Test processing a valid `Deadline` task.
     */
    @Test
    void testProcessDeadlineTask() throws ChatBotException {
        Deadline task = parser.processDeadlineTask("Submit report /by 20/06/2025");
        assertNotNull(task);
        assertEquals("Submit report", task.getName());
        assertEquals(LocalDate.of(2025, 6, 20), task.getDeadline());
    }

    /**
     * Test processing an invalid `Deadline` task (wrong date format).
     */
    @Test
    void testProcessDeadlineTaskInvalidFormat() {
        assertThrows(InvalidDateTimeFormatException.class, () ->
                parser.processDeadlineTask("Submit report /by June 20, 2025"));
    }

    /**
     * Test processing a valid `Event` task.
     */
    @Test
    void testProcessEventTask() throws ChatBotException {
        Event task = parser.processEventTask("Conference /from 01/07/2025 /to 03/07/2025");
        assertNotNull(task);
        assertEquals("Conference", task.getName());
        assertEquals(LocalDate.of(2025, 7, 1), task.getStartTime());
        assertEquals(LocalDate.of(2025, 7, 3), task.getEndTime());
    }

    /**
     * Test processing an invalid `Event` task (wrong date format).
     */
    @Test
    void testProcessEventTaskInvalidFormat() {
        assertThrows(InvalidDateTimeFormatException.class, () ->
                parser.processEventTask("Conference /from July 1, 2025 /to July 3, 2025"));
    }

    /**
     * Test processing a task by type (`ToDo`, `Deadline`, `Event`).
     */
    @Test
    void testProcessTaskByTypeValid() throws ChatBotException {
        Task todo = parser.processTaskByType("T", "Do laundry");
        Task deadline = parser.processTaskByType("D", "Project submission /by 25/12/2025");
        Task event = parser.processTaskByType("E", "Hackathon /from 01/09/2025 /to 03/09/2025");

        assertEquals("Do laundry", todo.getName());
        assertEquals("Project submission", deadline.getName());
        assertEquals("Hackathon", event.getName());
    }

    /**
     * Test processing an invalid task type.
     */
    @Test
    void testProcessTaskByTypeInvalid() {
        assertThrows(ChatBotException.class, () -> parser.processTaskByType("X", "Some Task"));
    }

    /**
     * Test parsing an unknown command.
     */
    @Test
    void testParseUnknownCommand() {
        assertThrows(UnknownCommandException.class, () -> parser.parse("randomCommand"));
    }
}
