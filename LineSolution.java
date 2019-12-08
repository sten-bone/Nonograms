import java.util.Arrays;

/**
 * A LineSolution represents a solution for a given row or column. A solution can have representations of Tiles which
 * are either known to be filled or unknown.
 *
 * @author Ben Stone
 */

public class LineSolution {
    enum State {
        FILLED,
        UNKNOWN
    }

    private State[] solution;

    public LineSolution(int size) {
        solution = new State[size];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = State.UNKNOWN;
        }
    }

    /**
     * @return the size of this LineSolution
     */
    public int size() {
        return solution.length;
    }

    /**
     * @return this LineSolution's solution
     */
    public State[] getSolution() {
        return solution;
    }

    /**
     * @param index an int index within this LineSolution
     * @return true if the LineSolution at index is filled
     * @throws IndexOutOfBoundsException if index is not valid for this LineSolution
     */
    public boolean isFilled(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " is not a valid index for this LineSolution!");
        }
        else return solution[index] == State.FILLED;
    }

    /**
     * Sets the representation at index to be filled.
     * @param index an int index
     * @throws IndexOutOfBoundsException if index is not in this solution's length
     */
    public void setFilled(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " is not a valid index for this solution!");
        }
        solution[index] = State.FILLED;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LineSolution)) {
            return false;
        }
        else return Arrays.equals(solution, ((LineSolution) other).getSolution());
    }

    @Override
    public String toString() {
        String str = "[";
        for (int i = 0; i < size(); i++) {
            str += solution[i] == State.FILLED ? "1" : "0";
            if (i != size() - 1) {
                str += ", ";
            }
        }
        return str + "]";
    }
}