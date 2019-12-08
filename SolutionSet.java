import java.util.ArrayList;

/**
 * A SolutionSet is a storage of the possible LineSolution Objects for a row or column.
 *
 * @author Ben Stone
 */
public class SolutionSet {
    // holds the LineSolution Objects
    private ArrayList<LineSolution> solutions;

    public SolutionSet() {
        solutions = new ArrayList<>();
    }

    /**
     * @return the size of this SolutionSet.
     */
    public int size() {
        return solutions.size();
    }

    /**
     * Adds a new LineSolution to this SolutionSet.
     * @param newSolution a LineSolution Object
     */
    public void add(LineSolution newSolution) {
        solutions.add(newSolution);
    }

    /**
     * Gets the LineSolution at the given index.
     * @param index an int index within the SolutionSet
     * @return the LineSolution at index
     * @throws IndexOutOfBoundsException if index is not valid within this SolutionSet
     */
    public LineSolution get(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " is not a valid index for this SolutionSet!");
        }
        return solutions.get(index);
    }

    /**
     * Removes the LineSolution at the given index.
     * @param index an int index within the SolutionSet
     * @return the LineSolution removed at index
     * @throws IndexOutOfBoundsException if index is not valid within this SolutionSet
     */
    public LineSolution remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index + " is not a valid index for this SolutionSet!");
        }
        return solutions.remove(index);
    }
}
