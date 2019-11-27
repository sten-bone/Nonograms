/**
 * TestBoards is a class not meant to be instantiated, but instead provides a host of Board objects which can be used
 * for testing.
 *
 * @author Ben Stone
 */
public class TestBoards {
    static final Board TEST1 = new Board(2,
            new Clue[] {new Clue(new int[] {2}), new Clue(new int[] {1})},
            new Clue[] {new Clue(new int[] {2}), new Clue(new int[] {1})});

    static final Board TEST2 = new Board(3,
            new Clue[] {new Clue(new int[] {3}), new Clue(new int[] {1, 1}), new Clue(new int[] {3})},
            new Clue[] {new Clue(new int[] {3}), new Clue(new int[] {1, 1}), new Clue(new int[] {3})});

    static final Board TEST3 = new Board(5,
            new Clue[] {new Clue(new int[] {5}), new Clue(new int[] {2, 2}), new Clue(new int[] {1, 1, 1}),
                    new Clue(new int[] {2, 2}), new Clue(new int[] {5})},
            new Clue[] {new Clue(new int[] {5}), new Clue(new int[] {2, 2}), new Clue(new int[] {1, 1, 1}),
                    new Clue(new int[] {2, 2}), new Clue(new int[] {5})});

    static final Board TEST4 = new Board(10,
            new Clue[] {new Clue(new int[] {1, 1, 1}), new Clue(new int[] {3}), new Clue(new int[] {1, 1, 1}),
                new Clue(new int[] {1, 1, 1}), new Clue(new int[] {0}), new Clue(new int[] {1, 1, 1, 1}),
                new Clue(new int[] {1, 1, 1, 1}), new Clue(new int[] {1, 1, 6}), new Clue(new int[] {1, 1, 1, 3}),
                new Clue(new int[] {0})},
            new Clue[] {new Clue(new int[] {4, 3}), new Clue(new int[] {1, 1}), new Clue(new int[] {4, 1}),
                new Clue(new int[] {1}), new Clue(new int[] {1, 2, 3}), new Clue(new int[] {2}),
                new Clue(new int[] {1}), new Clue(new int[] {4}), new Clue(new int[] {2}), new Clue(new int[] {4})});
}
