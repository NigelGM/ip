# Nimbus Task List Application ☁️✅

Welcome to **Nimbus**, a lightweight CLI task manager (inspired by the Duke project) that helps you track tasks quickly from the terminal. Nimbus supports adding tasks, listing them, and marking/unmarking completion — with a clean, consistent text UI.

---

## 🚀 Features

### ✅ Task Creation
Add tasks using:
- `todo <description>`
- `deadline <description> /by <when>`
- `event <description> /from <start> /to <end>`

### ✅ Task Management
- `list` — view all tasks
- `mark <index>` — mark a task as done
- `unmark <index>` — mark a task as not done

### ✅ Clean Text UI
Nimbus prints responses in a consistent “boxed” format for readability.

> Note: Nimbus is currently a **CLI (text UI)** application. Features like GUI and auto-saving should only be listed if you have implemented them.

---

## 🧩 Command Summary

| Command | Example |
|---|---|
| Add todo | `todo borrow book` |
| Add deadline | `deadline return book /by Sunday` |
| Add event | `event project meeting /from Mon 2pm /to 4pm` |
| List tasks | `list` |
| Mark done | `mark 2` |
| Unmark | `unmark 2` |
| Exit | `bye` |

---

## 🛠 Installation & Setup

### Prerequisites
- **JDK 17**
- IntelliJ IDEA (recommended)

### Run in IntelliJ
1. Open the project folder in IntelliJ.
2. Set Project SDK to **JDK 17**.
3. Run `Nimbus.main()` from `src/main/java/Nimbus.java`.

(Your original template instructions were here — you can keep or delete that section depending on how “personal” you want the README to be.)  
Template reminder: keep `src/main/java` as the root folder for Java files. :contentReference[oaicite:1]{index=1}

### Run from Terminal (if Gradle wrapper exists)
From project root:
- Windows:
  ```powershell
  .\gradlew.bat run
