package nimbus.task;

public class Todo extends Task {

    public Todo(String description) {
        super(TaskType.TODO, description); // IMPORTANT: (TaskType, String)
    }

    @Override
    public String toString() {
        return super.toString();
    }
}



