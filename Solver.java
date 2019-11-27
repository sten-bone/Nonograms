/**
 * A Solver class is instantiated to solve a given Board with specified solving algorithm.
 *
 * @author Ben Stone
 */
public class Solver {

    public Solver() {}

    /**
     * Solves the given Board using the given SolvingAlgorithm.
     * @param board a Board Object to be solved.
     * @param algorithm a SolvingAlgorithm Object used to specify the algorithm used to solve the Board.
     */
    public void solve(Board board, SolvingAlgorithm algorithm) {
        algorithm.solve(board);
    }
}
