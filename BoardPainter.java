/**
 * A BoardPainter class handles the drawing of a board to the screen. It uses an abstract Board object as its internal
 * structure.
 */

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class BoardPainter extends JPanel implements MouseListener, KeyListener {
    private Board board;
    int tileSize, maxClues;
    boolean filling;
    Font font;
    final Color bgColor = new Color(200, 200, 200);
    final Color tileColor = new Color( 220, 220, 220);
    final Color fillColor = new Color(127, 96, 175);
    final Color borderColor = Color.black;
    final Color filledBorderColor = Color.black;
    final Color fontBackground = new Color (60, 60, 60);
    final Color fontColor = Color.white;
    final Color fontColorSlashed = new Color(100, 60, 140);

    /**
     * Creates a BoardPainter Object for the given Board within the given JFrame
     * @param b a Board Object
     * @param frame a JFrame Object
     */
    public BoardPainter(Board b, JFrame frame) {
        board = b;
        setBackground(bgColor);
        // initialize BoardPainter
        init(frame);
        // add mouse and key listeners
        addMouseListener(this);
        this.addKeyListener(this);
    }

    public Board getBoard() {
        return board;
    }

    private void init(JFrame frame) {
        int width = frame.getWidth();
        int height = frame.getHeight();
        int boardSize = board.getBoard().length;
        maxClues = findMostClues();
        // uses the smallest dimension to determine Tile size
        tileSize = width / (boardSize + maxClues);
        // set the font for the Board
        font = new Font("Helvetica", Font.PLAIN, tileSize / 3);
        filling = true;
    }

    /**
     * Draws this Board to the screen
     * @param g a Graphics object (which will be cast as a Graphics2D Object)
     */
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        drawCurrentFillMode(g2d);
        drawRowClues(g2d);
        drawColClues(g2d);
        drawTiles(g2d);
    }

    /**
     * Helper method used to draw all row Clues
     * @param g2d a Graphics2D object used for drawing
     */
    private void drawRowClues(Graphics2D g2d) {
        // draw each row clue
        for (int i = 0; i < board.getRowClues().length; i++) {
            // the current Clue row being worked on
            Clue currentClue = board.getRowClues()[i];
            // used for determining the starting x position
            int startX = maxClues - currentClue.getClue().length;
            for (int j = 0; j < currentClue.getClue().length; j++) {
                // get this current NumberValue
                NumberValue currentNumberValue = currentClue.getClue()[j];
                // coordinates of upper right corner of Clue box
                int x = (startX + j) * tileSize;
                int y = (maxClues + i) * tileSize;
                // draw in number depending on whether or not it is checked, change color
                g2d.setPaint(fontBackground);
                g2d.fillRect(x, y, tileSize, tileSize);
                if (currentNumberValue.isChecked()){
                    g2d.setPaint(fontColorSlashed);
                }
                else g2d.setPaint(fontColor);
                // draw the int clue
                g2d.setFont(font);
                drawCenteredString(currentNumberValue.getValue() + "", tileSize, tileSize, x, y, g2d);
            }
            // draw a separating rectangle for visual differentiation
            g2d.setPaint(bgColor);
            g2d.setStroke(new BasicStroke(tileSize / 25 + 1));
            g2d.drawRect(0, (maxClues + i) * tileSize, maxClues * tileSize, tileSize);
        }
    }

    /**
     * Helper method used to draw all column Clues
     * @param g2d a Graphics2D object used for drawing
     */
    private void drawColClues(Graphics2D g2d) {
        // draw each column clue
        for (int i = 0; i < board.getColClues().length; i++) {
            // the current Clue column being worked on
            Clue currentClue = board.getColClues()[i];
            // used for determining the starting x position
            int startY = maxClues - currentClue.getClue().length;
            for (int j = 0; j < currentClue.getClue().length; j++) {
                // get this current NumberValue
                NumberValue currentNumberValue = currentClue.getClue()[j];
                // coordinates of upper right corner of Clue box
                int x = (maxClues + i) * tileSize;
                int y = (startY + j) * tileSize;
                // draw in number depending on whether or not it is checked, change color
                g2d.setPaint(fontBackground);
                g2d.fillRect(x, y, tileSize, tileSize);
                if (currentNumberValue.isChecked()){
                    g2d.setPaint(fontColorSlashed);
                }
                else g2d.setPaint(fontColor);
                // draw the int clue
                g2d.setFont(font);
                drawCenteredString(currentNumberValue.getValue() + "", tileSize, tileSize, x, y, g2d);
            }
            // draw a separating rectangle for visual differentiation
            g2d.setPaint(bgColor);
            g2d.setStroke(new BasicStroke(tileSize / 25 + 1));
            g2d.drawRect( (maxClues + i) * tileSize,0, tileSize, maxClues * tileSize);
        }
    }

    /**
     * Helper method used to draw all of the tiles for this Board
     * @param g2d a Graphics2D Object
     */
    private void drawTiles(Graphics2D g2d) {
        // the location of the upper left corner of the Tiles
        int startCorner = maxClues * tileSize;
        for (int r = 0; r < board.getRowClues().length; r++) {
            for (int c = 0; c < board.getColClues().length; c++) {
                Tile currentTile = board.getBoard()[r][c];
                // x and y coordinates of this Tiles upper left corner
                int x = startCorner + (c * tileSize);
                int y = startCorner + (r * tileSize);
                // draw the Tile based on its state
                int currentState = currentTile.getState();
                if (currentState == 1) {
                    drawFilledTile(x, y, tileSize, g2d);
                }
                else if (currentState == 2) {
                    drawSlashedTile(x, y, tileSize, g2d);
                }
                else {
                    drawEmptyTile(x, y, tileSize, g2d);
                }
            }
        }
    }

    /**
     * A helper method which draws current fill mode visual indicator in upper left corner
     * @param g2d a Graphics2D Object
     */
    private void drawCurrentFillMode(Graphics2D g2d) {
        // x and y coordinate for upper left corner of rect
        int corner = (maxClues * tileSize) / 4;
        // size of width and height
        int size = corner * 2;
        if (filling) {
            // draw filling icon
            drawFilledTile(corner, corner, size, g2d);
        }
        else {
            // draw slashing icon
            drawSlashedTile(corner, corner, size, g2d);
        }
    }

    /**
     * Draws a filled Tile
     * @param x x coordinate of the upper left corner
     * @param y y coordinate of the upper left corner
     * @param size size of the Tile
     * @param g2d a Graphics2D Object
     */
    private void drawFilledTile(int x, int y, int size, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(tileSize / 40 + 1));

        g2d.setPaint(fillColor);
        g2d.fillRect(x, y, size, size);

        g2d.setPaint(filledBorderColor);
        g2d.drawRect(x, y, size, size);
    }

    /**
     * Draws an empty Tile
     * @param x x coordinate of the upper left corner
     * @param y y coordinate of the upper left corner
     * @param size size of the Tile
     * @param g2d a Graphics2D Object
     */
    private void drawEmptyTile(int x, int y, int size, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(tileSize / 40 + 1));

        g2d.setPaint(tileColor);
        g2d.fillRect(x, y, size, size);

        g2d.setPaint(borderColor);
        g2d.drawRect(x, y, size, size);
    }

    /**
     * Draws a slashed Tile
     * @param x x coordinate of the upper left corner
     * @param y y coordinate of the upper left corner
     * @param size size of the Tile
     * @param g2d a Graphics2D Object
     */
    private void drawSlashedTile(int x, int y, int size, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(tileSize / 40 + 1));

        g2d.setPaint(tileColor);
        g2d.fillRect(x, y, size, size);

        g2d.setPaint(borderColor);
        g2d.drawRect(x, y, size, size);
        g2d.drawLine(x, y, x + size, y + size);
        g2d.drawLine(x, y + size, x + size, y);
    }

    /**
     * Helper method used to draw a String centered within a rectangle
     * @param s a String to be drawn
     * @param w int width of a bounding rectangle
     * @param h int height of a bounding rectangle
     * @param g a Graphics Object
     */
    public void drawCenteredString(String s, int w, int h, int initialX, int initialY, Graphics g) {
        // get the FontMetrics for main font
        FontMetrics fm = g.getFontMetrics(font);
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x + initialX, y + initialY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }


    /**
     * Finds the most clues in the row and column used for proper sizing of the board
     * @return an int which is the most NumberValues in a single Clue
     */
    private int findMostClues() {
        int currentMax = 0;
        // go through each row/column and find the Clue with the most NumberValues
        for (Clue c: board.getRowClues()) {
            if (c.getClue().length > currentMax) {
                currentMax = c.getClue().length;
            }
        }
        for (Clue c: board.getColClues()) {
            if (c.getClue().length > currentMax) {
                currentMax = c.getClue().length;
            }
        }
        return currentMax;
    }

    /**
     * Affects the Tile at the given x and y coordinates based on the current filling mode
     * @param x an int x coordinate
     * @param y an int y coordinate
     * @param isFilling a boolean where true is currently fill mode and false is currently in slash mode
     */
    private void affectTile(int x, int y, boolean isFilling) {
        // get the Tile which is currently being edited to make it easier to check
        Tile editingTile = board.getBoard()[y][x];
        // if in filling mode and already filled or in slashing mode and already slashed, empty Tile
        if ((board.getBoard()[y][x].getState() == 1 && isFilling) || (editingTile.getState() == 2 && !isFilling)) {
            board.emptyTile(y, x);
        }
        // otherwise fill if in filling mode or slash if in slashing mode
        else {
            if (isFilling) {
                board.fillTile(y, x);
            }
            else{
                board.slashTile(y, x);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // change the tile clicked on if the click was within the board
        if (e.getX() >= maxClues * tileSize && e.getY() >= maxClues * tileSize) {
            affectTile((e.getX() / tileSize) - maxClues, (e.getY() / tileSize) - maxClues, filling);
            board.checkCol((e.getX() / tileSize) - maxClues);
            board.checkRow((e.getY() / tileSize) - maxClues);
            if (board.isSolved()) {
                System.out.println("Solved!");
            }
        }
        revalidate();
        repaint();
    }

    @Override
    /**
     * Any key pressed will toggle whether filling or slashing Tiles
     */
    public void keyPressed(KeyEvent e) {
        filling = !filling;
        revalidate();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
