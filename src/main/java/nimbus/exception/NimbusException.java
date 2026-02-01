package nimbus.exception;

/**
 * Represents an application-level error in Nimbus, typically caused by invalid user input
 * or an invalid command format.
 */
public class NimbusException extends Exception {
    /**
     * Constructs a NimbusException with the specified message.
     *
     * @param message Error message to show to the user.
     */
    public NimbusException(String message) {
        super(message);
    }
}
