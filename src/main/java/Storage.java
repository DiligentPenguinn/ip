import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    String filePath = "";
    String directoryPath = "";

    public Storage(String directoryPath, String filePath) {
        this.filePath = filePath;
        this.directoryPath = directoryPath;
    }

    public void save(TaskList tasks) throws ChatBotException {
        try {
            FileWriter fw = new FileWriter(this.filePath);
            ArrayList<Task> arrayTask = tasks.getAllTasks();
            for (Task task: arrayTask) {
                fw.write(task.toSavedString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new ChatBotException("Oops! Something went wrong, I cannot save the tasks list! "
                    + e.getMessage());
        }
    }
}
