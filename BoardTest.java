/**
 * A class used for the abstract testing of a board class. Only uses String output, the "backend" portion.
 */

public class BoardTest {

    public static void main(String[] args) {
        Board b = TestBoards.TEST3;
        System.out.println(b);

        b.fillTile(0, 0);
        b.fillTile(0, 1);
        b.fillTile(0, 2);

        b.fillTile(1, 0);;
        b.slashTile(1, 1);
        b.fillTile(1, 2);

        b.fillTile(2, 0);
        b.fillTile(2, 1);
        b.fillTile(2, 2);

        for (int i = 0; i < b.getBoard().length; i++) {
            b.checkCol(i);
            b.checkRow(i);
        }

        System.out.println(b);
        System.out.println(b.isSolved());
    }
}
