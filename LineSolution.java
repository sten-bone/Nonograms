/**
 * A LineSolution represents a solution for a given row or column. Internally, a 1 represents a guaranteed filled Tile
 * while a 0 represents an unknown state for the Tile, whether slashed or filled.
 */

public class LineSolution {
    private int size;
    private int[] solution;

    public LineSolution(int size) {
        this.size = size;
        solution = new int[size];
    }

}
