import java.util.ArrayList;

/**
 * A SolutionMatrix is a structure holding all of the possible solutions for each row or column on a given Board.
 *
 * @author Ben
 */
public class SolutionMatrix {
    private ArrayList<SolutionSet> matrix;

    public SolutionMatrix(int size) {
        matrix = new ArrayList<>(size);
    }

    /**
     * @return the size of this SolutionMatrix
     */
    public int size() {
        return matrix.size();
    }

    /**
     * Adds the given SolutionSet to this SolutionMatrix.
     * @param set a SolutionSet Object
     */
    public void add(SolutionSet set) {
        matrix.add(set);
    }

    /**
     * Gets the SolutionSet at the given index.
     * @param index an int index within the SolutionMatrix
     * @return the SolutionSet at index
     * @throws IndexOutOfBoundsException if index is not valid for this SolutionMatrix
     */
    public SolutionSet get(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " is not a valid index for this SolutionMatrix!");
        }
        return matrix.get(index);
    }
}
