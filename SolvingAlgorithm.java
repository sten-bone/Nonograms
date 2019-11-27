/**
 * An abstraction interface for an algorithm used to solve a Nonogram puzzle. A SolvingAlgorithm's only job is to
 * find a/the correct solution for a given puzzle Board.
 *
 * @author Ben Stone
 */
public interface SolvingAlgorithm {

    /**
     * Solves the given Board using this SolvingAlgorithm's specific algorithm.
     * @param board a Board Object
     */
    void solve(Board board);
}
