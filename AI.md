# AI-Assisted Programming

This project was significantly enhanced using AI tools (Gemini) to assist with feature implementation, complex debugging, code refactoring, and test automation.

## 1. Feature Implementation: Update Command (C-Update)
**Goal:** Implement a command to edit existing tasks and upgrade their types (e.g., converting a Todo to a Deadline).

* **Logic Design:** The AI proposed and refined the logic for `UpdateCommand.java`. This included a state-transition mechanism where providing a date (`/by`) to a Todo automatically converts it into a Deadline, and providing a range (`/from`, `/to`) converts it into an Event.
* **Parser Logic:** The AI provided the logic for `Parser.java` to handle optional flags order-independently. It introduced string manipulation techniques to extract the description cleanly by locating the first occurrence of any flag (e.g., `/by`), preventing "dirty" descriptions where command flags were accidentally saved as text.
* **UI Integration:** The AI helped modify `Ui.java` to return `String` responses instead of void prints, enabling the command execution flow to populate the JavaFX GUI dialog bubbles correctly.

## 2. Debugging & Error Handling
**Goal:** Fix silent crashes and logical errors during task updates.

* **Silent GUI Crashes:**
    * *Issue:* The GUI would show empty dialog bubbles when an exception occurred.
    * *AI Solution:* Diagnosed that `Nimbus.java` was catching exceptions but not returning the error message string to the GUI controller. The AI provided the fix to ensure `ui.showError(e.getMessage())` is returned properly.
* **Parser "Dirty" Reads:**
    * *Issue:* Updating a task description (e.g., `update 1 read book`) failed or captured unwanted text if flags were missing.
    * *AI Solution:* Identified that the regex splitting logic was too rigid. Refactored `prepareUpdate` in `Parser.java` to dynamically calculate the "cut-off" index for descriptions based on the presence of `/by`, `/from`, or `/to`.
* **Data Integrity:**
    * *Issue:* Tasks were not updating correctly in the list.
    * *AI Solution:* Identified missing `setTask` methods in `TaskList.java` and provided a robust implementation with index bounds checking.

## 3. Refactoring & Code Quality
**Goal:** Improve readability and adhere to Modern Java standards.

* **Reducing Nesting (Arrowhead Anti-Pattern):** The initial implementation of `UpdateCommand.execute` had deep `if-else` nesting. The AI refactored this into flat helper methods (`createNewDeadline`, `createNewEvent`, `createPreservedTypeTask`), improving readability and maintainability.
* **Modern Java Syntax:** The AI identified warnings related to explicit casting and suggested using **Pattern Matching for `instanceof`** (e.g., `if (original instanceof Event oldEvent)`), which made the code cleaner and resolved IDE warnings.
* **Dead Code Removal:** The AI pointed out that the buffering logic in `Ui.java` (`getBufferedOutput`) was rendered obsolete by the new String-return architecture and advised removing it to reduce technical debt.

## 4. Testing (JUnit)
**Goal:** Ensure robustness of the new features.

* **Stubbing for GUI:** The AI demonstrated how to create a `TestUi` stub class in JUnit to verify `String` return values, allowing logic to be tested without launching the actual JavaFX application.
* **Regression Testing:** The AI suggested specific test cases to prevent regression, such as verifying that updating a description *without* flags does not accidentally wipe out existing dates for Deadlines or Events.

## 5. Tools Used
* **Gemini:** Primary tool for logic generation, debugging, code review, and documentation.