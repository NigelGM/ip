# Nimbus User Guide

**Nimbus** is a weather-themed desktop task manager. It is optimized for users who prefer a Command Line Interface (CLI) but enjoy the visual feedback of a Graphical User Interface (GUI). Nimbus helps you navigate the "clouds" of your schedule with a chill, atmospheric personality.

## Quick Start

1. Ensure you have Java 17 or above installed on your computer.
2. Download the latest `nimbus.jar` from the [releases page](https://github.com/nigelgm/ip/releases).
3. Copy the file to the folder you want to use as the *home folder* for your task manager.
4. Double-click the file to start the application.
5. Type the command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help menu.
6. Refer to the features below for details of each command.

## Features

### Adding a todo: `todo`
Adds a standard task to your list without any specific time constraints.

Format: `todo <description>`

**Example:**
`todo read lecture notes`

**Expected outcome:**
Got it. I've added this task:
[T][ ] read lecture notes
Now you have 1 task in the list.

### Adding a deadline: `deadline`
Adds a task that needs to be done before a specific date and time. The date format must be `yyyy-mm-dd HHmm`.

Format: `deadline <description> /by <date>`

**Example:**
`deadline return library book /by 2026-02-20 1800`

**Expected outcome:**
Got it. I've added this task:
[D][ ] return library book (by: Feb 20 2026 6:00 PM)
Now you have 2 tasks in the list.

### Adding an event: `event`
Adds a task that occurs over a specific time range. Both start and end times are required in the format `yyyy-mm-dd HHmm`.

Format: `event <description> /from <start> /to <end>`

**Example:**
`event project meeting /from 2026-02-21 1400 /to 2026-02-21 1600`

**Expected outcome:**
Got it. I've added this task:
[E][ ] project meeting (from: Feb 21 2026 2:00 PM to: 4:00 PM)
Now you have 3 tasks in the list.

### Listing tasks: `list`
Displays all tasks currently in your list, showing their index, type, status, and details.

Format: `list`

**Expected outcome:**
Here are the clouds floating in your sky:

[T][ ] read lecture notes

[D][ ] return library book (by: Feb 20 2026 6:00 PM)

[E][ ] project meeting (from: Feb 21 2026 2:00 PM to: 4:00 PM)

### Finding tasks: `find`
Finds tasks whose descriptions contain the given keyword.

Format: `find <keyword>`

**Example:**
`find book`

**Expected outcome:**
Here are the matching tasks in your list:

[D][ ] return library book (by: Feb 20 2026 6:00 PM)

### Updating a task: `update`
Updates the details of an existing task without deleting it. You can update the description, the deadline time, or the event start/end times.

Format: `update <index> <flag> <value>`

**Examples:**
* `update 1 read new book` (Updates description)
* `update 2 /by 2026-12-25 1200` (Updates deadline)

**Expected outcome:**
I've updated this task for you:
[D][ ] return library book (by: Dec 25 2026 12:00 PM)

### Marking a task as done: `mark`
Marks an existing task as completed.

Format: `mark <index>`

**Example:**
`mark 1`

**Expected outcome:**
Nice! I've marked this task as done:
[T][X] read lecture notes

### Unmarking a task: `unmark`
Marks a completed task as not done yet.

Format: `unmark <index>`

**Example:**
`unmark 1`

**Expected outcome:**
OK, I've marked this task as not done yet:
[T][ ] read lecture notes

### Deleting a task: `delete`
Removes the specified task from the list permanently.

Format: `delete <index>`

**Example:**
`delete 3`

**Expected outcome:**
Noted. I've removed this task:
[E][ ] project meeting (from: Feb 21 2026 2:00 PM to: 4:00 PM)
Now you have 2 tasks in the list.

### Getting help: `help`
Displays a list of all available commands if you are unsure of the syntax.

Format: `help`

**Expected outcome:**
Lost in the clouds? Here's how to navigate:

1. todo <desc> - Add a simple task

2. deadline <desc> /by <time> - Add a task with a deadline
...

### Exiting the program: `bye`
Exits the program and saves your data.

Format: `bye`

**Expected outcome:**
Bye. Hope to see you again soon!

## FAQ

**Q**: How do I transfer my data to another computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file `data/nimbus.txt` from your previous computer.

**Q**: Can I resize the window?
**A**: Yes, the interface is responsive and will adjust to your screen size.