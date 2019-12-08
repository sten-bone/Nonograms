import java.util.ArrayList;
import java.util.Arrays;

/**
 * The SmartBruteForceAlgorithm class is a variation on the BruteForceAlgorithm, which increases speed by eliminating
 * impossible row solutions. It does this by first using a left-right overlap approach to the columns and rows, then
 * eliminating possible row solutions which do not contain those guaranteed filled Tiles.
 *
 * Has slightly more front-end computation, but potential payoff is great in efficiency.
 *
 * @author Ben Stone
 */

public class SmartBruteForceAlgorithm extends BruteForceAlgorithm {
    private BoardPainter painter;

    public SmartBruteForceAlgorithm(BoardPainter boardPainter) {
        super(boardPainter);
        this.painter = boardPainter;
    }

    @Override
    public void solve() {
        long start = System.currentTimeMillis();
        System.out.println("Beginning to solve Board...");
        SolutionMatrix rowSolutions = removeImpossibleSolutions(findAllPossibleSolutions(painter.getBoard()));
        System.out.println("All possible row solutions found...");
        // holds current solution index for each row
        int[] currentSolutionIndices = new int[rowSolutions.size()];
        // holds the max index for each row's solutions
        int[] maxSolutionIndices = getMaxRowSolutionIndices(rowSolutions);
        iterateToSolve(start, rowSolutions, currentSolutionIndices, maxSolutionIndices);

    }

    /**
     * Removes any solutions which are not possible based on the right-left solution for each row and column.
     * @param possibleSolutions a SolutionMatrix Object
     * @return the edited SolutionMatrix Object
     */
    private SolutionMatrix removeImpossibleSolutions(SolutionMatrix possibleSolutions) {
        SolutionSet fullOverlaps = findFullOverlappingRows();
        // keeps track of how many solutions were removed
        int numRemoved = 0;
        // go through each overlapping solution
        for (int i = 0; i < fullOverlaps.size(); i++) {
            // must check each possible solution
            int j = 0;
            while (j < possibleSolutions.get(i).size()) {
                LineSolution currentPossibleSolution = possibleSolutions.get(i).get(j);
                for (int k = 0; k < currentPossibleSolution.size(); k++) {
                    // if the known solution and possible solution are not filled at the same index, the possible
                    // solution must be removed from the set
                    if (fullOverlaps.get(i).isFilled(k) && !currentPossibleSolution.isFilled(k)) {
                        possibleSolutions.get(i).remove(j);
                        numRemoved++;
                        j--;
                        break;
                    }
                }
                j++;
            }
        }
        System.out.println("Removed " + numRemoved + " impossible solutions!");
        return possibleSolutions;
    }

    /**
     * Gives the fully overlapped solution for each row, taking into account what was found by overlapping columns too.
     * @return an Array of Arrays pertaining to the int represented solution for each row with overlaps
     */
    private SolutionSet findFullOverlappingRows() {
        SolutionSet colOverlaps = findAllOverlapsForRowsOrColumns(painter.getBoard().getColClues(),
                painter.getBoard().height());
        SolutionSet rowOverlaps = findAllOverlapsForRowsOrColumns(painter.getBoard().getRowClues(),
                painter.getBoard().width());
        // fill the overlaps for each row from colOverlaps
        for (int i = 0; i < rowOverlaps.size(); i++) {
            for (int j = 0; j < rowOverlaps.get(i).size(); j++) {
                // if the Tile in the row is not guaranteed to be filled but the column overlap does, make it filled
                // in the row too
                if (!(rowOverlaps.get(i).isFilled(j)) && colOverlaps.get(j).isFilled(i)) {
                    rowOverlaps.get(i).setFilled(j);
                }
            }
        }
        return rowOverlaps;
    }

    /**
     * Finds the left-right overlapping solution of the left- and rightmost solutions for a given row or column.
     * @param clue a Clue Object for the row or column
     * @param rowColLength the size of the row or column
     * @return a LineSolution Object
     */
    private LineSolution findOverlap(Clue clue, int rowColLength) {
        int[] leftmostIndices = getLeftMostIndices(clue);
        int[] rightmostIndices = getRightmostIndices(clue, rowColLength);
        LineSolution overlappingSolution = new LineSolution(rowColLength);
        for (int i = 0; i < clue.getClue().length; i++) {
            int currentClue = clue.getClue()[i].getValue();
            int leftExtreme = rightmostIndices[i];
            int rightExtreme = leftmostIndices[i] + currentClue - 1;
            for (int j = leftExtreme; j <= rightExtreme; j++) {
                overlappingSolution.setFilled(j);
            }
        }
        return overlappingSolution;
    }

    /**
     * Gets all the right-left overlaps for each row or column from the given Clue
     * @param clues an Array of Clue Objects
     * @param rowColLength the length of the row or column
     * @return an SolutionSet of the right-left overlaps for each row or column
     */
    private SolutionSet findAllOverlapsForRowsOrColumns(Clue[] clues, int rowColLength) {
        SolutionSet allOverlaps = new SolutionSet();
        for (int i = 0; i < clues.length; i++) {
            // can only find overlap if the sum of the Clues is greater than half the row or column's length
            if (sumClue(clues[i]) > rowColLength / 2) {
                allOverlaps.add(findOverlap(clues[i], rowColLength));
            }
            else allOverlaps.add(new LineSolution(rowColLength));
        }
        return allOverlaps;
    }

    /**
     * Finds the indices which correspond to the leftmost solving of this row or column.
     * @param clue a Clue Object for the row or column
     * @return an Array of ints corresponding to the leftmost index of each Clue
     */
    private int[] getLeftMostIndices(Clue clue) {
        // stores the leftmost indices
        int[] leftmostIndices = new int[clue.getClue().length];
        int currentIndex = 0;
        // go through each clue and find its index
        for (int i = 0; i < clue.getClue().length; i++) {
            int currentClue = clue.getClue()[i].getValue();
            leftmostIndices[i] = currentIndex;
            // account for at least 1 space
            currentIndex += currentClue + 1;
        }
        return leftmostIndices;
    }

    /**
     * Utility method for computing the total number of filled Tiles for a given Clue.
     * @param clue a Clue Object
     * @return an int corresponding to the sum of the Clue
     */
    private int sumClue(Clue clue) {
        int sum = 0;
        for (NumberValue n : clue.getClue()) {
            sum += n.getValue();
        }
        return sum;
    }
}
