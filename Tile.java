/**
 * An object which represents a tile on a Nonogram board. Tile can be in one of three states: empty, filled, or
 * slashed. Tile objects can be freely changed between these three states.
 *
 * @author Ben Stone
 */

public class Tile {
    // stores the state of this tile with 0 being empty, 1 being filled, and 2 being slashed
    private int state;

    /**
     * Default constructor which creates a new Tile set to empty by default
     */
    public Tile() {
        state = 0;
    }

    /**
     * Constructor for a new Tile object
     * @param state an integer value representing the state of this Tile
     */
    public Tile(int state) {
        this.state = state;
    }

    /**
     * @return the current state of this Tile as an integer
     */
    public int getState() {
        return state;
    }

    /**
     * Sets this Tile's state to empty
     */
    public void empty() {
        state = 0;
    }

    /**
     * Sets this Tile's state to filled
     */
    public void fill() {
        state = 1;
    }

    /**
     * Sets this Tile's state to slashed
     */
    public void slash() {
        state = 2;
    }

    /**
     * @return a String representation of this Tile object based on its state
     */
    public String toString() {
        switch(state) {
            case 1:
                return "N";
            case 2:
                return "/";
            default:
                return "\t";
        }
    }
}
