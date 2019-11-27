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
        ArrayList<ArrayList<int[]>> rowSolutions = findAllPossibleSolutions(board);
    }

    /**
     * Finds all the possible solutions for each row within a given Board.
     * @param board a Board Object.
     * @return an ArrayList of ArrayLists, with each outer ArrayList corresponding to a row in the Board, and each inner
     * ArrayList corresponding to all the possible solutions for the given row stored as Arrays of ints, where a 1 is a
     * filled Tile and a 0 is a slashed Tile
     */
    private ArrayList<ArrayList<int[]>> findAllPossibleSolutions(Board board) {
        ArrayList<ArrayList<int[]>> allSolutions = new ArrayList<>();
        for (Clue clue : board.getRowClues()) {
            allSolutions.add(findPossibleRowSolutions(clue, board.getBoard()[0].length));
        }
        return allSolutions;
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
        // an Array of ints used to keep track of starting indices
        int[][] indices = new int[clue.getClue().length][clue.getClue().length];
        // computes starting indices
        int currentIndex = 0;
        for (int i = 0; i < clue.getClue().length; i++) {
            int currentClue = clue.getClue()[i].getValue();
            // add to all the initial Arrays
            for (int[] arr: indices) {
                arr[i] = currentIndex;
            }
            // add 1 for leftmost solving
            currentIndex += currentClue + 1;
        }
        // compute the rightmost possible indices to signal when to step
        int[] rightmostIndices = getRightmostIndices(clue, rowLength);
        // while the current indices have not become the same as the rightmost indices
        while (!Arrays.equals(indices[indices.length - 1], rightmostIndices)) {
            // always starts at the farthest right index
            for (int i = indices.length - 1; i >= 0; i--) {
                // move over one permutation position to the right until the given index has hit the rightmost
                while (indices[i][i] <= rightmostIndices[i]) {
                    // add the permutation, then increase the current index
                    int[] addedSolution = completeSolutionFromIndices(clue, indices[i], rowLength);
                    // only add a solution if it has not just been added
                    if (possibleSolutions.size() == 0 ||
                            !Arrays.equals(possibleSolutions.get(possibleSolutions.size() - 1), addedSolution)) {
                        possibleSolutions.add(addedSolution);
                    }
                    if (indices[i][i] < rightmostIndices[i]) {
                        indices[i][i]++;
                    }
                    else break;
                }
                // if this index is not the leftmost index and the index before is not already at its rightmost index,
                // increase the starting index of the one before
                if (i > 0 && indices[i - 1][i - 1] < rightmostIndices[i - 1]) {
                    // if the clue to the left is the leftmost, everything must move over 1 from leftmost
//                    if (i == 1) {
//                        for (int j = 0; j < leftmostStartingIndices.length; j++) {
//                            leftmostStartingIndices[j]++;
//                            startingIndices[j] = leftmostStartingIndices[j];
//                            currentIndices[j] = leftmostStartingIndices[j];
//                        }
//                        break;
//                    }
                    int j = i - 1;
                    // increase the starting indices from the one previous
                    for (int k = j; k < indices.length; k++) {
                        // increment, then reset
                        indices[j][k]++;
                        for (int m = j + 1; m < indices.length; m++) {
                            indices[m][k] = indices[j][k];
                        }
                    }
                    // must break so everything can restart
                    break;
                }
            }
        }
        // finally add the rightmost solution if not already done so
        int[] rightmostSolution = completeSolutionFromIndices(clue, rightmostIndices, rowLength);
        if (possibleSolutions.size() == 0 ||
                !Arrays.equals(possibleSolutions.get(possibleSolutions.size() - 1), rightmostSolution)) {
            possibleSolutions.add(rightmostSolution);
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
