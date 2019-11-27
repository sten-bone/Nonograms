/**
 * The main class for the Nonograms program, which handles the GUI, board completion, drawing, user interaction, and
 * controls game flow.
 *
 * @author Ben Stone
 */

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Nonograms extends JFrame {

    /**
     * Constructor simply calls the init() function to set everything up
     */
    public Nonograms(int width, int height) {
        init(width, height);
    }

    /**
     * Initializes the JPanel used for the Nonograms game
     */
    private void init(int width, int height) {
        setTitle("Nonograms by Ben Stone");
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds a Board to be displayed to the screen using the BoardPainter Object.
     * @param b a Board Object to be displayed
     */
    public void addBoard(Board b) {
        add(new BoardPainter(b, this));
    }

    /**
     * Adds a BoardPainter to be displayed to the screen
     * @param bp a BoardPainter Object to be displayed
     */
    public void addBoard(BoardPainter bp) {
        add(bp);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                // Set up the Frame
                Nonograms n = new Nonograms(800, 825);
                // Adding a board for testing
                BoardPainter testBoard = new BoardPainter(TestBoards.TEST4, n);
                n.addKeyListener(testBoard);
                n.addBoard(testBoard);
                n.setVisible(true);

                Solver s = new Solver();
                s.solve(testBoard.getBoard(), new BruteForceAlgorithm());
            }
        });
    }
}
