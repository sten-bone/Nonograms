/**
 * A Solver class is instantiated to solve a given Board with specified solving algorithm.
 *
 * @author Ben Stone
 */
public class Solver {

    public Solver() {}

    /**
     * Solves the given Board using the given SolvingAlgorithm.
     * @param algorithm a SolvingAlgorithm Object used to specify the algorithm used to solve the Board.
     */
    public void solve( SolvingAlgorithm algorithm) {
        algorithm.solve();
    }
}
