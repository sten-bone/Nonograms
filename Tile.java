/**
 * An object which represents a tile on a Nonogram board. Tile can be in one of three states: empty, filled, or
 * slashed. Tile objects can be freely changed between these three states.
 *
 * @author Ben Stone
 */

public class Tile {
    enum State {
        FILLED,
        SLASHED,
        EMPTY
    }
    // stores the state of this Tile
    private State state;

    /**
     * Default constructor which creates a new Tile set to empty by default
     */
    public Tile() {
        state = State.EMPTY;
    }

    /**
     * @return the current state of this Tile as an integer
     */
    public State getState() {
        return state;
    }

    /**
     * Sets this Tile's state to empty
     */
    public void empty() {
        state = state.EMPTY;
    }

    /**
     * @return true if this Tile is empty
     */
    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Sets this Tile's state to filled
     */
    public void fill() {
        state = State.FILLED;
    }

    /**
     * @return true if this Tile is filled
     */
    public boolean isFilled() {
        return state == State.FILLED;
    }

    /**
     * Sets this Tile's state to slashed
     */
    public void slash() {
        state = State.SLASHED;
    }

    /**
     * @return true if this Tile is slashed
     */
    public boolean isSlashed() {
        return state == State.SLASHED;
    }

    /**
     * @return a String representation of this Tile object based on its state
     */
    public String toString() {
        switch(state) {
            case FILLED:
                return "N";
            case SLASHED:
                return "/";
            default:
                return "\t";
        }
    }
}
