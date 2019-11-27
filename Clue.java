/**
 * A Clue object represents the number clues for a row or column, defined by the user when creating Nonogram puzzles.
 * A clue is defined in terms of an array, for example: [3, 5, 9]. In this example, there are 3 filled Tiles together,
 * possibly with some number of slashed tiles before (from left to right), then some amount of slashed Tiles before 5
 * filled Tiles together, again followed with some number of slashed Tiles followed by 9 filled Tiles together.
 *
 * In a 20x20 board, where N is a filled tile and / is a crossed tile, this row could look like:
 * / N N N / N N N N N / N N N N N N N N N
 *
 * @author Ben Stone
 */

public class Clue {
    // stores the clues for this Clue object
    private NumberValue[] numbers;

    /**
     * A Clue object can be defined with its internal array of numbers
     * @param numbers an array of NumberValues representing the numbers for this row or column
     */
    public Clue(NumberValue[] numbers) {
        this.numbers = numbers;
    }

    /**
     * A Clue Object can be defined also with an array of integers
     * @param numbers an array of ints representing the numbers for this row or column
     */
    public Clue(int[] numbers) {
        NumberValue[] numVals = new NumberValue[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numVals[i] = new NumberValue(numbers[i]);
        }
        this.numbers = numVals;
    }

    /**
     * @return this Clue's numbers
     */
    public NumberValue[] getClue() {
        return numbers;
    }

    /**
     * @return the number of NumberValue Objects in this clue
     */
    public int size() {
        return numbers.length;
    }

    /**
     * @return a String representation of this Clue object
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(numbers[i]);
        }
        sb.append(")");
        return sb.toString();
    }
}
