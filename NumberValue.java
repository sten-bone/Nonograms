import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * A NumberValue Object refers to a number within the row and column clues of the Board. The NumberValue Object keeps
 * track of its value and whether or not it is checked, with the ability to toggle checked or not.
 *
 * @author Ben Stone
 */

public class NumberValue {
    // stores this NumberValue's integer value
    private int value;
    // a boolean whether or not this NumberValue is checked, meaning its corresponding set it present in row/column
    private boolean isChecked;

    public NumberValue(int value) {
        this.value = value;
        isChecked = false;
    }

    /**
     * @return this NumberValue's integer value
     */
    public int getValue() {
        return value;
    }

    /**
     * @return this NumberValue's boolean isChecked value
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Toggles this NumberValue's isChecked field to true
     */
    public void check() {
        isChecked = true;
    }

    /**
     * Toggles this NumberValue's isChecked field to false
     */
    public void uncheck() {
        isChecked = false;
    }

    /**
     * Get a String representation of this NumberValue in the form "[int]" if isChecked is false and
     * "/[int]" if isChecked is true
     * @return String representation of this NumberValue
     */
    public String toString() {
        return (isChecked ? "/" : "") + value;
    }
}
