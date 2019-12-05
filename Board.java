/**
 * A Board object is a 2D Nonograms board of a specified size n by n. The Board object contains n Tile objects by n
 * Tile objects. The Board object also contains references to clues for both the rows and the columns in the Nonograms
 * puzzle the Board is displaying.
 *
 * The Board object has specific methods which can check the validity of a row or column based on that row or column's
 * Clue. For a row or column to be valid (correct) it must have all of the Clue's defined sections of filled Tiles
 * together with either empty or slashed blocks between them.
 *
 * @author Ben Stone
 */

public class Board {
    // the actual board
    private Tile[][] board;
    // reference to clues for each respective row or column
    private Clue[] rowClues, colClues;
    // holds the number of Clues slashed
    private int numbersUnslashed;

    /**
     * Creates a square Board object of dimensions size x size, creating default Tile objects for each location.
     * @param size the size of each side of the Board
     * @param rowClues the Clues for each row of this Board
     * @param colClues the Clues for each col of this Board
     */
    public Board(int size, Clue[] rowClues, Clue[] colClues) {
        board = new Tile[size][size];
        this.rowClues = rowClues;
        this.colClues = colClues;
        numbersUnslashed = countNumbersInClues(rowClues, colClues);
        // instantiate all Tiles as new blank tiles
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                board[r][c] = new Tile();
            }
        }
        checkForZeros();
    }

    /**
     * @return this Board object's board
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Returns the Tile in the Board at the given indices.
     * @param row an int row
     * @param col an int col
     * @return the Tile at the row and column
     */
    public Tile getTile(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= board.length) {
            throw new IndexOutOfBoundsException("Row " + row + " is not a valid row!");
        }
        if (col < 0 || col >= board[row].length) {
            throw new IndexOutOfBoundsException("Column " + col + " is not a valid column!");
        }
        return board[row][col];
    }

    /**
     * @return this Board's width
     */
    public int width() {
        return board[0].length;
    }

    /**
     * @return this Board's height
     */
    public int height() {
        return board.length;
    }

    /**
     * @return this Board object's row Clues
     */
    public Clue[] getRowClues() {
        return rowClues;
    }

    /**
     * @return this Board object's column Clues
     */
    public Clue[] getColClues() {
        return colClues;
    }

    /**
     * Empty the given Tile in this Board
     * @param r int row
     * @param c int column
     */
    public void emptyTile(int r, int c) {
        board[r][c].empty();
    }

    /**
     * Fill the given Tile in this Board
     * @param r int row
     * @param c int col
     */
    public void fillTile(int r, int c) {
        board[r][c].fill();
    }

    /**
     * Slash the given Tile in this Board
     * @param r int row
     * @param c int col
     */
    public void slashTile(int r, int c) {
        board[r][c].slash();
    }

    /**
     * Checks each Clue for a zero, if there is one present it slashes it
     */
    private void checkForZeros() {
        for (Clue clue: rowClues) {
            for (NumberValue n: clue.getClue()) {
                if (n.getValue() == 0) {
                    n.check();
                    numbersUnslashed--;
                }
            }
        }
        for (Clue clue: colClues) {
            for (NumberValue n: clue.getClue()) {
                if (n.getValue() == 0) {
                    n.check();
                    numbersUnslashed--;
                }
            }
        }
    }

    /**
     * Checks the given row
     * @param r an int index of a row in this board
     * @throws IndexOutOfBoundsException if r is not a valid row index
     */
    public void checkRow(int r) throws IndexOutOfBoundsException {
        // make sure r is a valid index
        if (r >= board.length) {
            throw new IndexOutOfBoundsException("Row " + r + " is not a valid row in this board!");
        }
        uncheckRow(r);
        checkRowFromLeft(r);
        checkRowFromRight(r);
    }

    /**
     * Unchecks all the NumberValues for the given row's Clue
     * @param r an int index of a row in board
     */
    private void uncheckRow(int r) {
        for (NumberValue n : rowClues[r].getClue()) {
            if (n.isChecked()) {
                n.uncheck();
                numbersUnslashed++;
            }
        }
    }

    /**
     * Checks the given row from left to right, only connecting filled Tiles with slashed Tiles
     * @param r an int pertaining to a given row in the Board
     */
    private void checkRowFromLeft(int r) {
        // the Clue Object for this row
        Clue rowClue = rowClues[r];
        // keeps track of which NumberValue in Clue is being counted
        int currentClue = 0;
        // holds the current count of tiles
        int currentCount = 0;

        for (int i = 0; i < board[r].length; i++) {
            Tile currentTile = board[r][i];

            // if all the clues have been satisfied or passed
            if (currentClue < 0 || currentClue >= rowClue.size()) {
                // if the tile is filled but not expected to be, uncheck all the Clues to let the user know there
                // is an error
                if (currentTile.isFilled()) {
                    uncheckRow(r);
                    return;
                }
                // move on if the tile is slashed
                else if (currentTile.isSlashed()){
                    continue;
                }
                else return;
            }

            // if current tile is filled, increment current count
            if (currentTile.isFilled()) {
                currentCount++;
            }

            // get this current clue
            NumberValue currentNumberValue = rowClue.getClue()[currentClue];
            // if the number of tiles in a row matches this number value
            if (currentCount == currentNumberValue.getValue()) {
                // look to see if this is at the right end of the row or has an empty or slashed tile right after,
                // which would denote the end of this grouping and means it is correct
                if (i + 1 >= board[r].length || board[r][i + 1].isEmpty() || board[r][i + 1].isSlashed()) {
                    // if the current NumberValue has not been checked off yet
                    if (!currentNumberValue.isChecked()) {
                        // check off this clue
                        currentNumberValue.check();
                        // decrease number of clues left to be slashed
                        numbersUnslashed--;
                    }
                    // increment to the next NumberValue in the Clue
                    currentClue++;
                    // reset the count
                    currentCount = 0;
                    continue;
                }
            }

            // if the expected number of Tiles has not been met or has been exceeded but the current tile is empty
            // or slashed
            if (currentCount > 0 && currentCount != currentNumberValue.getValue() && !currentTile.isFilled()) {
                // uncheck the clue if necessary
                if (currentNumberValue.isChecked()) {
                    currentNumberValue.uncheck();
                    numbersUnslashed++;
                }
                // if the tile is slashed keep going
                if (currentTile.isSlashed()) {
                    // move to the next NumberValue in the Clue
                    currentClue++;
                    // reset number count
                    currentCount = 0;
                    // decrease number of clues left to be slashed
                }
            }

            // if this is the last tile not empty but all the clues have not been met, uncheck all to alert user
            if (i == board[r].length - 1 && currentClue < rowClue.size() && !currentTile.isEmpty()) {
                uncheckRow(r);
            }

            // end if this is an empty tile
            if (currentTile.isEmpty()) {
                return;
            }
        }
    }

    /**
     * Checks the given row from right to left, only connecting filled Tiles with slashed Tiles
     * @param r an int pertaining to a given row in the Board
     */
    private void checkRowFromRight(int r) {
        // the Clue Object for this row
        Clue rowClue = rowClues[r];
        // keeps track of which NumberValue in Clue is being counted
        int currentClue = rowClue.size() - 1;
        // holds the current count of tiles
        int currentCount = 0;

        for (int i = board[r].length - 1; i >= 0; i--) {
            Tile currentTile = board[r][i];

            // if all the clues have been satisfied or passed
            if (currentClue < 0 || currentClue >= rowClue.size()) {
                // if the tile is filled but not expected to be, uncheck all the Clues to let the user know there
                // is an error
                if (currentTile.isFilled()) {
                    uncheckRow(r);
                    return;
                }
                // move on if the tile is slashed
                else if (currentTile.isSlashed()){
                    continue;
                }
                else return;
            }

            // if current tile is filled, increment current count
            if (currentTile.isFilled()) {
                currentCount++;
            }

            // get this current clue
            NumberValue currentNumberValue = rowClue.getClue()[currentClue];
            // if the number of tiles in a row matches this number value
            if (currentCount == currentNumberValue.getValue()) {
                // look to see if this is at the left end of the row or has an empty or slashed tile right after (to
                // the left), which would denote the end of this grouping and means it is correct
                if (i - 1 < 0 || board[r][i - 1].isEmpty() || board[r][i - 1].isSlashed()) {
                    // if the current NumberValue has not been checked off yet
                    if (!currentNumberValue.isChecked()) {
                        // check off this clue
                        currentNumberValue.check();
                        // decrease number of clues left to be slashed
                        numbersUnslashed--;
                    }
                    // increment to the next NumberValue in the Clue
                    currentClue--;
                    // reset the count
                    currentCount = 0;
                    continue;
                }
            }

            // if the expected number of Tiles has not been met or has been exceeded but the current tile is empty
            // or slashed
            if (currentCount > 0 && currentCount != currentNumberValue.getValue() && !currentTile.isFilled()) {
                // uncheck the clue if necessary
                if (currentNumberValue.isChecked()) {
                    currentNumberValue.uncheck();
                    numbersUnslashed++;
                }
                // if the tile is slashed keep going
                if (currentTile.isSlashed()) {
                    // move to the next NumberValue in the Clue
                    currentClue--;
                    // reset number count
                    currentCount = 0;
                    // decrease number of clues left to be slashed
                }
            }

            // if this is the last tile not empty but all the clues have not been met, uncheck all to alert user
            if (i == 0 && currentClue >= 0 && !currentTile.isEmpty()) {
                uncheckRow(r);
            }

            // end if this is an empty tile
            if (currentTile.isEmpty()) {
                return;
            }
        }
    }

    /**
     * Checks the given column
     * @param r an int index of a column in this board
     * @throws IndexOutOfBoundsException if r is not a valid column index
     */
    public void checkCol(int r) throws IndexOutOfBoundsException {
        // make sure r is a valid index
        if (r >= board.length) {
            throw new IndexOutOfBoundsException("Column " + r + " is not a valid column in this board!");
        }
        uncheckCol(r);
        checkColFromTop(r);
        checkColFromBottom(r);
    }

    /**
     * Unchecks all the NumberValues for the given column's Clue
     * @param r an int index of a row in column
     */
    private void uncheckCol(int r) {
        for (NumberValue n : colClues[r].getClue()) {
            if (n.isChecked()) {
                n.uncheck();
                numbersUnslashed++;
            }
        }
    }

    /**
     * Checks the given column from top to bottom, only connecting filled Tiles with slashed Tiles
     * @param r an int pertaining to a given column in the Board
     */
    private void checkColFromTop(int r) {
        // the Clue Object for this column
        Clue colClue = colClues[r];
        // keeps track of which NumberValue in Clue is being counted
        int currentClue = 0;
        // holds the current count of tiles
        int currentCount = 0;

        for (int i = 0; i < board.length; i++) {
            Tile currentTile = board[i][r];

            // if all the clues have been satisfied or passed
            if (currentClue < 0 || currentClue >= colClue.size()) {
                // if the tile is filled but not expected to be, uncheck all the Clues to let the user know there
                // is an error
                if (currentTile.isFilled()) {
                    uncheckCol(r);
                    return;
                }
                // move on if the tile is slashed
                else if (currentTile.isSlashed()){
                    continue;
                }
                else return;
            }

            // if current tile is filled, increment current count
            if (currentTile.isEmpty()) {
                currentCount++;
            }

            // get this current clue
            NumberValue currentNumberValue = colClue.getClue()[currentClue];
            // if the number of tiles in a row matches this number value
            if (currentCount == currentNumberValue.getValue()) {
                // look to see if this is at the right end of the row or has an empty or slashed tile right after,
                // which would denote the end of this grouping and means it is correct
                if (i + 1 >= board.length || board[i + 1][r].isEmpty() || board[i + 1][r].isSlashed()) {
                    // if the current NumberValue has not been checked off yet
                    if (!currentNumberValue.isChecked()) {
                        // check off this clue
                        currentNumberValue.check();
                        // decrease number of clues left to be slashed
                        numbersUnslashed--;
                    }
                    // increment to the next NumberValue in the Clue
                    currentClue++;
                    // reset the count
                    currentCount = 0;
                }
            }

            // if the expected number of Tiles has not been met or has been exceeded but the current tile is empty
            // or slashed
            if (currentCount > 0 && currentCount != currentNumberValue.getValue() && !currentTile.isFilled()) {
                // uncheck the clue if necessary
                if (currentNumberValue.isChecked()) {
                    currentNumberValue.uncheck();
                    numbersUnslashed++;
                }
                // if this is the last Tile
                if (i == board.length - 1) {
                    // uncheck all from the first Clue to the currentClue
                    for (int j = 0; j <= currentClue; j++) {
                        if (colClue.getClue()[j].isChecked()) {
                            colClue.getClue()[j].uncheck();
                            numbersUnslashed++;
                        }
                    }
                }
                // if the tile is slashed keep going
                if (currentTile.isSlashed()) {
                    // move to the next NumberValue in the Clue
                    currentClue++;
                    // reset number count
                    currentCount = 0;
                    // decrease number of clues left to be slashed
                }
            }

            // if this is the last tile not empty but all the clues have not been met, uncheck all to alert user
            if (i == board.length - 1 && currentClue < colClue.size() && !currentTile.isEmpty()) {
                uncheckCol(r);
            }

            // end if this is an empty tile
            if (currentTile.isEmpty()) {
                return;
            }
        }
    }

    /**
     * Checks the given column from bottom to top, only connecting filled Tiles with slashed Tiles
     * @param r an int pertaining to a given column in the Board
     */
    private void checkColFromBottom(int r) {
        // the Clue Object for this row
        Clue colClue = colClues[r];
        // keeps track of which NumberValue in Clue is being counted
        int currentClue = colClue.size() - 1;
        // holds the current count of tiles
        int currentCount = 0;

        for (int i = board.length - 1; i >= 0; i--) {
            Tile currentTile = board[i][r];

            // if all the clues have been satisfied or passed
            if (currentClue < 0 || currentClue >= colClue.size()) {
                // if the tile is filled but not expected to be, uncheck all the Clues to let the user know there
                // is an error
                if (currentTile.isFilled()) {
                    uncheckCol(r);
                    return;
                }
                // move on if the tile is slashed
                else if (currentTile.isSlashed()){
                    continue;
                }
                else return;
            }

            // if current tile is filled, increment current count
            if (currentTile.isFilled()) {
                currentCount++;
            }

            // get this current clue
            NumberValue currentNumberValue = colClue.getClue()[currentClue];
            // if the number of tiles in a row matches this number value
            if (currentCount == currentNumberValue.getValue()) {
                // look to see if this is at the left end of the row or has an empty or slashed tile right after (to
                // the left), which would denote the end of this grouping and means it is correct
                if (i - 1 < 0 || board[i - 1][r].isEmpty() || board[i - 1][r].isSlashed()) {
                    // if the current NumberValue has not been checked off yet
                    if (!currentNumberValue.isChecked()) {
                        // check off this clue
                        currentNumberValue.check();
                        // decrease number of clues left to be slashed
                        numbersUnslashed--;
                    }
                    // increment to the next NumberValue in the Clue
                    currentClue--;
                    // reset the count
                    currentCount = 0;
                }
            }

            // if the expected number of Tiles has not been met or has been exceeded but the current tile is empty
            // or slashed
            if (currentCount > 0 && currentCount != currentNumberValue.getValue() && !currentTile.isFilled()) {
                // uncheck the clue if necessary
                if (currentNumberValue.isChecked()) {
                    currentNumberValue.uncheck();
                    numbersUnslashed++;
                }
                // if the tile is slashed keep going
                if (currentTile.isSlashed()) {
                    // move to the next NumberValue in the Clue
                    currentClue--;
                    // reset number count
                    currentCount = 0;
                    // decrease number of clues left to be slashed
                }
            }

            // if this is the last tile not empty but all the clues have not been met, uncheck all to alert user
            if (i == 0 && currentClue >= 0 && !currentTile.isEmpty()) {
                uncheckCol(r);
            }

            // end if this is an empty tile
            if (currentTile.isEmpty()) {
                return;
            }
        }
    }

    /**
     * Tests whether this Board is correctly solved, meaning all of the NumberValues in all of the Clues should be
     * slashed
     * @return true if this Board has been solved correctly, false if not
     */
    public boolean isSolved() {
        return numbersUnslashed == 0;
    }

    /**
     * Counts all the numbers in the row and column Clues
     * @param rowClues an array of Clues
     * @param colClues an array of Clues
     * @return an integer value pertaining to the number of Clue NumberValues in this board
     */
    private int countNumbersInClues(Clue[] rowClues, Clue[] colClues) {
        int count = 0;
        for (Clue c: rowClues) {
            count += c.getClue().length;
        }
        for (Clue c: colClues) {
            count += c.getClue().length;
        }
        return count;
    }

    /**
     * @return a String representation of this Board
     */
    public String toString() {
        // maintains the String to be returned
        StringBuilder strBoard = new StringBuilder("\t");
        // add all the column clues
        for (Clue c: colClues) {
            strBoard.append(c + "\t");
        }
        // line return
        strBoard.append("\n");
        // go through all the rows in the board
        for (int r = 0; r < board.length; r++) {
            // add the row's clue
            strBoard.append(rowClues[r] + "\t");
            // add the row's tiles
            for (int c = 0; c < board[r].length; c++) {
                strBoard.append(board[r][c] + "\t");
            }
            // line return
            strBoard.append("\n");
        }
        return strBoard.toString();
    }
}
