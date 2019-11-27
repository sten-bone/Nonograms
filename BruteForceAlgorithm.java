import java.util.ArrayList;
import java.util.Arrays;

/**
 * The BruteForceAlgorithm class solves a Board using a brute force approach, checking every possible combination
 * of valid rows until a correct solution is found.
 *
 * NOTE: This is an EXTREMELY inefficient algorithm, but is also guaranteed to find a solution given enough time.
 *
 * @author Ben Stone
 */
public class BruteForceAlgorithm implements SolvingAlgorithm {

    public void solve(Board board) {
        ArrayList<int[]> rowSolutions = findPossibleRowSolutions(board.getRowClues()[0], board.getBoard().length);
        for (int[] arr: rowSolutions) {
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * Finds all the possible solutions for each row within a given Board.
     * @param board a Board Object.
     * @return an ArrayList of ArrayLists, with each outer ArrayList corresponding to a row in the Board, and each inner
     * ArrayList corresponding to all the possible solutions for the given row stored as Arrays of ints, where a 1 is a
     * filled Tile and a 0 is a slashed Tile
     */
    private ArrayList<ArrayList<int[]>> findAllPossibleSolutions(Board board) {
        return null;
    }

    private ArrayList<int[]> findPossibleRowSolutions(Clue clue, int rowLength) {
        // setup the ArrayList for storage
        ArrayList<int[]> possibleSolutions = new ArrayList<>();
        // initial check to see if this is a zero row
        if (clue.getClue().length == 1 && clue.getClue()[0].getValue() == 0) {
            // add a row with all zeros (all must be slashed)
            possibleSolutions.add(new int[rowLength]);
            return possibleSolutions;
        }
        // an Array of ints used to keep track of where each filled block must originally start
        int[] startingIndices = new int[clue.getClue().length];
        // an Array of ints used to keep track of where each filled block is currently starting
        int[] currentIndices = new int[clue.getClue().length];
        // computes starting indices
        int currentIndex = 0;
        for (int i = 0; i < clue.getClue().length; i++) {
            int currentClue = clue.getClue()[i].getValue();
            startingIndices[i] = currentIndex;
            currentIndices[i] = currentIndex;
            // add 1 for leftmost solving
            currentIndex += currentClue + 1;
        }
        // compute the rightmost possible indices to signal when to step
        int[] rightmostIndices = getRightmostIndices(clue, rowLength);
        // while the current indices have not become the same as the rightmost indices
        while (!Arrays.equals(currentIndices, rightmostIndices)) {
            System.out.println("rightmost: " + Arrays.toString(rightmostIndices));
            System.out.println(Arrays.toString(currentIndices));
            // always starts at the farthest right index
            for (int i = currentIndices.length - 1; i >= 0; i--) {
                // move over one permutation position to the right until the given index has hit the rightmost
                while (currentIndices[i] <= rightmostIndices[i]) {
                    // add the permutation, then increase the current index
                    possibleSolutions.add(completeSolutionFromIndices(clue, currentIndices, rowLength));
                    if (currentIndices[i] < rightmostIndices[i]) {
                        currentIndices[i]++;
                    }
                    else break;
                }
                // if this index is not the leftmost index and the index before is not already at its rightmost index,
                // increase the starting index of the one before
                if (i > 0 && startingIndices[i - 1] < rightmostIndices[i - 1]) {
                    // increase the starting indices from the one previous
                    for (int j = i - 1; j < startingIndices.length; j++) {
                        // increment, then reset
                        startingIndices[j]++;
                        currentIndices[j] = startingIndices[j];
                    }
                    // must break so everything can restart
                    break;
                }
            }
        }
        return possibleSolutions;
    }

    /**
     * Finds the indices which correspond to the rightmost solving of this row.
     * @param clue a Clue Object for the row
     * @param rowLength the length of the row
     * @return an Array of ints corresponding to the rightmost index of each Clue
     */
    private int[] getRightmostIndices(Clue clue, int rowLength) {
        // stores the rightmost indices
        int[] rightmostIndices = new int[clue.getClue().length];
        int currentIndex = rowLength;
        // go through each clue and find its index
        for (int j = clue.getClue().length - 1; j >= 0; j--) {
            int currentClue = clue.getClue()[j].getValue();
            currentIndex -= currentClue;
            rightmostIndices[j] = currentIndex;
            // account for at least 1 space
            currentIndex--;
        }
        return rightmostIndices;
    }

    /**
     * Computes the row based on the indices and clues given.
     * @param clue a Clue Object pertaining to a row
     * @param indices the indices for each Clue
     * @param rowLength the length of the given row
     * @return an Array of ints where a 1 corresponds to a filled tile and a 0 corresponds to a slashed tile
     */
    private int[] completeSolutionFromIndices(Clue clue, int[] indices, int rowLength) {
        int index = 0;
        int[] solution = new int[rowLength];
        for (int i = 0; i < clue.getClue().length; i++) {
            int currentClue = clue.getClue()[i].getValue();
            int targetIndex = indices[i];
            // before getting to the left index of the next clue, fill with slashed markers (0s)
            while (index < targetIndex) {
                solution[index] = 0;
                index++;
            }
            // fill in the clue chunk
            for (int j = 0; j < currentClue; j++) {
                solution[index] = 1;
                index++;
            }
        }
        // fill in any remaining with slashed markers
        while (index < rowLength) {
            solution[index] = 0;
            index++;
        }
        return solution;
    }
}
