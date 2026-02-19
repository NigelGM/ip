# Nimbus Task Manager ☁️✅

![Java](https://img.shields.io/badge/Java-17-blue.svg) ![Gradle](https://img.shields.io/badge/Gradle-8.5-green.svg) ![JavaFX](https://img.shields.io/badge/UI-JavaFX-orange)

**Nimbus** is a weather-themed personal assistant chatbot designed to help you track tasks with a "chill" cloud aesthetic. Whether you are managing deadlines or organizing events, Nimbus keeps your schedule clear and your mind fog-free.

> _"I can help you navigate the clouds!"_

---

## 🚀 Features

### 🖥️ Interactive GUI
* **Weather-Themed Interface:** Enjoy a clean, cloud-inspired design with distinct dialog boxes for the user and Nimbus.
* **Visual Feedback:** Error messages appear as "stormy" alerts, while success messages are "clear skies."

### 📝 Smart Task Management
* **Standard Tasks:** Add `todo`, `deadline`, and `event` tasks easily.
* **Duplicate Detection:** Nimbus warns you if you try to add the exact same task twice.
* **Chronological Checks:** Events are validated to ensure the start time is before the end time (no time paradoxes allowed!).

### 🔍 Search & Edit
* **Find:** Quickly search for tasks using keywords (e.g., `find book`).
* **Update:** Modify existing tasks without deleting them (e.g., change a deadline or description).

### 🛡️ Robust Error Handling
* **Friendly Guidance:** If you type an unknown command or an empty message, Nimbus gently guides you back with helpful tips.
* **Help Command:** Stuck? Just type `help` to see a summary of what Nimbus can do.

---

## 🕹️ Command Summary

| Command | Usage | Example |
| :--- | :--- | :--- |
| **Add Todo** | `todo <desc>` | `todo read lecture notes` |
| **Add Deadline** | `deadline <desc> /by <yyyy-mm-dd HHmm>` | `deadline return book /by 2026-02-20 1800` |
| **Add Event** | `event <desc> /from <start> /to <end>` | `event team meeting /from 2026-02-21 1400 /to 1600` |
| **List** | `list` | `list` |
| **Find** | `find <keyword>` | `find notes` |
| **Update** | `update <index> <flag> <value>` | `update 1 /by 2026-02-25 1200` |
| **Mark/Unmark** | `mark <index>` / `unmark <index>` | `mark 1` |
| **Delete** | `delete <index>` | `delete 3` |
| **Help** | `help` | `help` |
| **Exit** | `bye` | `bye` |

---

## 🛠️ Installation & Setup

### Prerequisites
* JDK 17 or higher
* IntelliJ IDEA (Recommended)

### Run with Gradle (Recommended)
1.  Clone the repository.
2.  Open the terminal in the project root.
3.  Run the application:
    * **Windows:** `.\gradlew.bat run`
    * **Mac/Linux:** `./gradlew run`

### Run in IntelliJ
1.  Open the project folder in IntelliJ.
2.  Locate `src/main/java/nimbus/Launcher.java`.
3.  Right-click and select **Run 'Launcher.main()'**.

---

## 🧪 Testing

Nimbus comes with a comprehensive JUnit test suite covering parsers, task logic, and error handling.

To run the tests:
* **Windows:** `.\gradlew.bat test`
* **Mac/Linux:** `./gradlew test`

---

## 📜 Acknowledgements
* Inspired by the [Duke Project](https://se-education.org/duke-project/).
* Built with JavaFX and Gradle.
