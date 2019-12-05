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
    private BoardPainter painter;

    public BruteForceAlgorithm(BoardPainter boardPainter) {
        this.painter = boardPainter;
    }

    /**
     * Solves the given Board.
     */
    public void solve() {
        long start = System.currentTimeMillis();
        System.out.println("Beginning to solve Board...");
        ArrayList<ArrayList<int[]>> rowSolutions = findAllPossibleSolutions(painter.getBoard());
        System.out.println("All possible row solutions found...");
        // holds current solution index for each row
        int[] currentSolutionIndices = new int[rowSolutions.size()];
        // holds the max index for each row's solutions
        int[] maxSolutionIndices = getMaxRowSolutionIndices(rowSolutions);
        iterateToSolve(start, rowSolutions, currentSolutionIndices, maxSolutionIndices);
    }

    /**
     * Helper method which does the work of solving, which will be same for subclasses.
     * @param startTime a long for the System time the algorithm was started (for end analysis)
     * @param rowSolutions all the possible solutions for the Board
     * @param currentSolutionIndices the starting indices for each row's lsolution
     * @param maxSolutionIndices the maximum possible index for each row's solution
     */
    protected void iterateToSolve(long startTime, ArrayList<ArrayList<int[]>> rowSolutions, int[] currentSolutionIndices,
                                  int[] maxSolutionIndices) {
        // keeps track of whether or not to check a row, or if just moving back in Array
        boolean movingBack = false;
        // start with rows in first positions, then check them off for algorithm
        for (int k = 0; k < currentSolutionIndices.length; k++) {
            editRowFromSolution(k, rowSolutions.get(k).get(currentSolutionIndices[k]));
            painter.getBoard().checkRow(k);
        }
        System.out.println("Beginning to solve...");
        // while yet to go through all possible solutions
        while (!Arrays.equals(currentSolutionIndices, maxSolutionIndices)) {
            System.out.println(Arrays.toString(currentSolutionIndices));
            for (int i = currentSolutionIndices.length - 1; i >= 0; i--) {
                if (!movingBack) {
                    editRowFromSolution(i, rowSolutions.get(i).get(currentSolutionIndices[i]));
                    if (checkAll()) {
                        System.out.println("Board solved correctly in " + (System.currentTimeMillis() - startTime) / 1000f +
                                " seconds!");
                        return;
                    }
                }
                // increment index
                if (currentSolutionIndices[i] < maxSolutionIndices[i]) {
                    currentSolutionIndices[i]++;
                    movingBack = false;
                    break;
                }
                else {
                    // if not at the first row and the row above the current has not yet reached its max
                    if (i > 0 && currentSolutionIndices[i - 1] < maxSolutionIndices[i - 1]) {
                        // increment previous row, then reset everything to end of currentSolutionIndices to 0
                        currentSolutionIndices[i - 1]++;
                        editRowFromSolution(i - 1, rowSolutions.get(i - 1).get(currentSolutionIndices[i - 1]));
                        for (int j = i; j < currentSolutionIndices.length; j++) {
                            currentSolutionIndices[j] = 0;
                            editRowFromSolution(j, rowSolutions.get(j).get(0));
                        }
                        movingBack = false;
                        break;
                    }
                    else movingBack = true;
                }
            }
        }
        // finally, check the last possible solution
        for (int m = 0; m < maxSolutionIndices.length; m++) {
            editRowFromSolution(m, rowSolutions.get(m).get(maxSolutionIndices[m]));
        }
        if (checkAll()) {
            System.out.println("Board solved correctly in " + (System.currentTimeMillis() - startTime) / 1000f +
                    " seconds!");
            return;
        }
        else System.out.println("Searched for " + (System.currentTimeMillis() - startTime) / 1000f + " seconds, but" +
                " the algorithm was unable to find a solution to this puzzle.");
    }

    /**
     * Finds all the possible solutions for each row within a given Board.
     * @param board a Board Object.
     * @return an ArrayList of ArrayLists, with each outer ArrayList corresponding to a row in the Board, and each inner
     * ArrayList corresponding to all the possible solutions for the given row stored as Arrays of ints, where a 1 is a
     * filled Tile and a 0 is a slashed Tile
     */
    protected ArrayList<ArrayList<int[]>> findAllPossibleSolutions(Board board) {
        ArrayList<ArrayList<int[]>> allSolutions = new ArrayList<>();
        for (Clue clue : board.getRowClues()) {
            allSolutions.add(findPossibleRowSolutions(clue, board.width()));
        }
        return allSolutions;
    }

    protected ArrayList<int[]> findPossibleRowSolutions(Clue clue, int rowLength) {
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
    protected int[] getRightmostIndices(Clue clue, int rowLength) {
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
    protected int[] completeSolutionFromIndices(Clue clue, int[] indices, int rowLength) {
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

    /**
     * Edits the specific row based on the given Array solution. Also repaints the Board.
     * @param row an int corresponding to a specific row
     * @param solution an Array of ints corresponding to a possible solution
     */
    protected void editRowFromSolution(int row, int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                painter.getBoard().fillTile(row, i);
            }
            else painter.getBoard().slashTile(row, i);
        }
        painter.repaint();
    }

    /**
     * Gets the number of possible solutions for each row.
     * @param solutions all of the possible solutions for the Board
     * @return an Array of ints with the number of possible solutions for each row
     */
    protected int[] getMaxRowSolutionIndices(ArrayList<ArrayList<int[]>> solutions) {
        int[] maxSolutionIndices = new int[solutions.size()];
        for (int i = 0; i < solutions.size(); i++) {
            maxSolutionIndices[i] = solutions.get(i).size() - 1;
        }
        return maxSolutionIndices;
    }

    /**
     * Checks all the columns in the board for correctness, returning true if the Board is solved correctly
     * @return true if the Board is solved and false if otherwise
     */
    protected boolean checkAll() {
        for (int i = 0; i < painter.getBoard().width(); i++) {
            painter.getBoard().checkCol(i);
        }
        return painter.getBoard().isSolved();
    }
}
