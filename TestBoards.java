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

    static final Board PUMPKIN = new Board(15,
            new Clue[] {new Clue(new int[] {3}), new Clue(new int[] {3}), new Clue(new int[] {5}),
                new Clue(new int[] {11}), new Clue(new int[] {2, 5, 2}), new Clue(new int[] {2, 2, 2, 2}),
                new Clue(new int[] {1, 2, 2, 1}), new Clue(new int[] {1, 1, 1, 1}), new Clue(new int[] {1, 1, 1, 1}),
                new Clue(new int[] {1, 1, 1, 1}), new Clue(new int[] {1, 1, 1, 1}), new Clue(new int[] {1, 1, 1, 1}),
                new Clue(new int[] {1, 2, 2, 1}), new Clue(new int[] {2, 2, 2, 2}), new Clue(new int[] {13})},
            new Clue[] {new Clue(new int[] {9}), new Clue(new int[] {2, 2}), new Clue(new int[] {2, 1}),
                new Clue(new int[] {1, 7, 1}), new Clue(new int[] {1, 2, 3}), new Clue(new int[] {4, 2}),
                new Clue(new int[] {4, 1}), new Clue(new int[] {5, 1}), new Clue(new int[] {5, 1}),
                new Clue(new int[] {1, 4, 2}), new Clue(new int[] {1, 2, 3}), new Clue(new int[] {1, 7, 1}),
                new Clue(new int[] {2, 1}), new Clue(new int[] {2, 2}), new Clue(new int[] {9})});

    static final Board SNAIL = new Board(15,
            new Clue[] {new Clue(new int[] {2, 2}), new Clue(new int[] {1, 1}), new Clue(new int[] {1, 1, 6}),
                    new Clue(new int[] {4, 8}), new Clue(new int[] {2, 11}), new Clue(new int[] {1, 3, 3}),
                    new Clue(new int[] {1, 2, 4, 2}), new Clue(new int[] {1, 1, 6, 1}), new Clue(new int[] {1, 4, 2, 1}),
                    new Clue(new int[] {1, 3, 1, 2, 1}), new Clue(new int[] {1, 2, 5, 1}), new Clue(new int[] {1, 2, 4, 2}),
                    new Clue(new int[] {2, 3, 3}), new Clue(new int[] {1, 8}), new Clue(new int[] {12})},
            new Clue[] {new Clue(new int[] {1, 9}), new Clue(new int[] {5, 3}), new Clue(new int[] {1, 1}),
                    new Clue(new int[] {1, 1, 1}), new Clue(new int[] {5, 1}), new Clue(new int[] {9, 1}),
                    new Clue(new int[] {4, 7}), new Clue(new int[] {4, 3, 3}), new Clue(new int[] {3, 3, 2, 2}),
                    new Clue(new int[] {3, 2, 3, 2}), new Clue(new int[] {3, 2, 2, 2}), new Clue(new int[] {3, 6, 2}),
                    new Clue(new int[] {4, 4, 3}), new Clue(new int[] {4, 3}), new Clue(new int[] {9})});

    static final Board GOAT = new Board(15,
            new Clue[] {new Clue(new int[] {2, 3, 1, 3, 2}), new Clue(new int[] {2, 1, 1, 1, 2}),
                new Clue(new int[] {4, 1, 1, 1, 4}), new Clue(new int[] {2, 2}), new Clue(new int[] {1, 11, 1}),
                new Clue(new int[] {13}), new Clue(new int[] {1, 9, 1}), new Clue(new int[] {9}),
                new Clue(new int[] {1, 2, 3, 2, 1}), new Clue(new int[] {2, 2, 3, 2, 2}), new Clue(new int[] {3, 7, 3}),
                new Clue(new int[] {3, 2, 2, 3}), new Clue(new int[] {3, 3, 3, 3}), new Clue(new int[] {4, 2, 2, 4}),
                new Clue(new int[] {5, 3, 5})},
            new Clue[] {new Clue(new int[] {5, 1, 6}), new Clue(new int[] {4, 1, 7}), new Clue(new int[] {1, 2, 5}),
                new Clue(new int[] {1, 1, 6, 2}), new Clue(new int[] {1, 9, 1}), new Clue(new int[] {3, 4, 4}),
                new Clue(new int[] {7, 3}), new Clue(new int[] {3, 7, 1}), new Clue(new int[] {7, 3}),
                new Clue(new int[] {3, 4, 4}), new Clue(new int[] {1, 9, 1}), new Clue(new int[] {1, 1, 6, 2}),
                new Clue(new int[] {1, 2, 5}), new Clue(new int[] {4, 1, 7}), new Clue(new int[] {5, 1, 6})});

    static final Board HOURGLASS = new Board(15,
            new Clue[] {new Clue(new int[] {15}), new Clue(new int[] {1, 1}),
                    new Clue(new int[] {1, 1, 1}), new Clue(new int[] {1, 1, 1}), new Clue(new int[] {2, 4, 1, 2}),
                    new Clue(new int[] {2, 5, 2}), new Clue(new int[] {2, 3, 2}), new Clue(new int[] {2, 1, 2}),
                    new Clue(new int[] {2, 1, 2}), new Clue(new int[] {2, 1, 2}), new Clue(new int[] {2, 3, 2}),
                    new Clue(new int[] {1, 6, 1}), new Clue(new int[] {1, 9, 1}), new Clue(new int[] {13}),
                    new Clue(new int[] {15})},
            new Clue[] {new Clue(new int[] {1, 1}), new Clue(new int[] {5, 5}), new Clue(new int[] {1, 2, 2, 2}),
                    new Clue(new int[] {1, 2, 2, 3}), new Clue(new int[] {1, 1, 3, 3}), new Clue(new int[] {1, 2, 1, 4}),
                    new Clue(new int[] {1, 3, 5}), new Clue(new int[] {1, 11}), new Clue(new int[] {1, 2, 5}),
                    new Clue(new int[] {1, 1, 1, 4}), new Clue(new int[] {1, 1, 3, 4}), new Clue(new int[] {1, 2, 2, 2, 3}),
                    new Clue(new int[] {1, 2, 2, 2}), new Clue(new int[] {5, 5}), new Clue(new int[] {1, 1})});
}
