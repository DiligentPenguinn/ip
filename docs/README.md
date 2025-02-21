# DiligentPenguin Chatbot User Guide
<div style="text-align: center;">
<img src=Ui.png alt="A screenshot of the project GUI" width="250">
</div>

Diligent Penguin is a simple and efficient chatbot application designed to 
help users manage their daily tasks. 
The chatbot enables users to add, edit, delete, and view their task list effortlessly through an interactive conversation.

## Features
### Add a todo task: `todo`
Add a new task with a description to the task list.

Format: `todo <description>`

Examples:

- `todo submit report`

### Add a deadline task: `deadline`
Add a new task with a description and a deadline to the task list.

Format: `deadline <description> /by <deadline>`

Note that the deadline format is `dd/mm/yyyy`

Examples:

- `deadline grade homework /by 19/02/2025`

### Add an event task: `event`
Add a new task with a description, a start time and an end time to the task list

Format: `deadline <description> /from <start_time> /to <end_time>`

Note that start time and end time format is `dd/mm/yyyy`

Examples:

- `event attend hackathon /from 20/02/2025 /to 21/02/2025`

### List all tasks: `list`
List all pending and completed tasks.

Format: `list`

<div style="text-align: center;">
<img src=listExample.png alt="A screenshot example for list" width="250">
</div>

### Mark task as completed: `mark`
Mark a task at a given index as completed

Format: `mark <task_index>`

Examples:

- `mark 3`

### Mark task as uncompleted: `unmark`
Mark a task at a given index as uncompleted

Format: `unmark <task_index>`

Examples:

- `unmark 3`

### Delete a task: `delete`
Remove a completed or unnecessary task at a given index from the list

Format: `delete <task_index>`

Examples:

- `delete 3`

### Update a task: `update`
Update a task at a given index from the list

Note that you cannot change the task type

Format 1 (short command): `update <task_index>`

This command prefill your next command with the a detailed update command with the current description. 
You can edit the task description and send the command to perform the update

Format 2 (detailed command): `update-<task_index> <task description>`

This command updates the task at the given index with the new description

Examples:

<div style="text-align: center;">
<img src=updateExample.png alt="A screenshot for update short command" width="250">
</div>

<div style="text-align: center;">
<img src=updateExample2.png alt="A screenshot for update long command" width="250">
</div>

### Locate tasks by keyword: `find`
Locate all tasks in the list that contain a given keyword

Format: `find <key_word>`

Examples:

<div style="text-align: center;">
<img src="findExample.png" alt="A screenshot for find command" width="250">
</div>

### Automatic Saving and loading data
DiligentPenguin automatically saves the task list after each command. There is no need to save manually

Once opened, DiligentPenguin automatically loads data from previous session (if exist)

Examples:

<div style="text-align: center;">
<img src="saveAndLoadExample.png" alt="A screenshot for save and load" width="250">
</div>

## Installation
1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `.jar` file from [here](https://github.com/DiligentPenguinn/ip/releases/tag/A-Release)
3. Copy the file to the folder you want to use as the home folder for your DiligentPenguin chatbot.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar diligentpenguin-v1.0.jar` 
command to run the application.