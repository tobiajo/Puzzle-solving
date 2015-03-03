package aod.nb25;

import java.util.Scanner;

/**
 * Puzzle solving.
 *
 * @author Tobias Johansson <tobias@johansson.xyz>
 */
public class Puzzle {

    private static final int EMPTY = 0;
    private static final int ROT_A = 'A';
    private static final int ROT_B = 'B';
    private static final int ROT_C = 'C';
    private static final int ROT_D = 'D';
    private static final int NONE  = 'E';
    private static final int FIXED = 'F';
    
    private static final int N = 5; // board size = N*N
    private static final int MAX_PIECES   = (N*N-1)/3;
    private static final int MAX_POSITION = (N-1)*(N-1)-1;

    private static final boolean ADD    = true;
    private static final boolean REMOVE = false;

    private int board[][];
    private boolean printOn;

    public void solve() {
        System.out.println("[NB25 - Puzzle]\n"
                + "Enter desired coordinates for the fixed square:");
        Scanner scanIn = new Scanner(System.in);
        System.out.print("row = ");
        int x = scanIn.nextInt();
        System.out.print("col = ");
        int y = scanIn.nextInt();
        board = new int[N][N];
        board[x - 1][y - 1] = FIXED;
        printOn = true;
        System.out.println("\nNumber of solutions: " + solutions());
    }

    public void solveAll() {
        System.out.println("[NB25 - Puzzle]\nAll solutions:");
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                board = new int[N][N];
                board[row - 1][col - 1] = FIXED;
                printOn = false;
                System.out.print(solutions() + "\t");
            }
            System.out.println();
        }
    }

    private int solutions() {
        return solutions(0, 0, 0);
    }

    private int solutions(int pos, int add, int sol) {
        if (pos > MAX_POSITION) {
            if (add == MAX_PIECES) {
                if (printOn) printSolution();
                return sol + 1;
            }
            return sol + 0;
        }
        for (int rot = ROT_A; rot <= NONE; rot++) {
            if (possible(pos, rot)) {
                add += reserve(pos, rot, ADD);
                sol  = solutions(pos + 1, add, sol);
                add += reserve(pos, rot, REMOVE);
            }
        }
        return sol;
    }

    private boolean possible(int pos, int rot) {
        if (rot == NONE) {
            return true;
        }
        int row = row(pos);
        int col = col(pos);
        if (rot == ROT_A) {
            return (   board[row]    [col + 1] == EMPTY
                    && board[row + 1][col]     == EMPTY
                    && board[row + 1][col + 1] == EMPTY);

        } else if (rot == ROT_B) {
            return (   board[row]    [col]     == EMPTY
                    && board[row + 1][col]     == EMPTY
                    && board[row + 1][col + 1] == EMPTY);

        } else if (rot == ROT_C) {
            return (   board[row]    [col]     == EMPTY
                    && board[row]    [col + 1] == EMPTY
                    && board[row + 1][col + 1] == EMPTY);

        } else {
            return (   board[row]    [col]     == EMPTY
                    && board[row]    [col + 1] == EMPTY
                    && board[row + 1][col]     == EMPTY);
        }
    }

    private int reserve(int pos, int rot, boolean status) {
        if (rot == NONE) {
            return 0;
        }
        int row = row(pos);
        int col = col(pos);
        if (rot == ROT_A) {
            board[row]    [col + 1] = status ? ROT_A : EMPTY;
            board[row + 1][col]     = status ? ROT_A : EMPTY;
            board[row + 1][col + 1] = status ? ROT_A : EMPTY;
            return status ? 1 : - 1;
        } else if (rot == ROT_B) {
            board[row]    [col]     = status ? ROT_B : EMPTY;
            board[row + 1][col]     = status ? ROT_B : EMPTY;
            board[row + 1][col + 1] = status ? ROT_B : EMPTY;
            return status ? 1 : - 1;
        } else if (rot == ROT_C) {
            board[row]    [col]     = status ? ROT_C : EMPTY;
            board[row]    [col + 1] = status ? ROT_C : EMPTY;
            board[row + 1][col + 1] = status ? ROT_C : EMPTY;
            return status ? 1 : - 1;

        } else {
            board[row]    [col]     = status ? ROT_D : EMPTY;
            board[row]    [col + 1] = status ? ROT_D : EMPTY;
            board[row + 1][col]     = status ? ROT_D : EMPTY;
            return status ? 1 : - 1;
        }
    }
    
    private int row(int pos) {
        return pos % (N-1);
    }
    
    private int col(int pos) {
        return pos / (N-1);
    }

    private void printSolution() {
        System.out.println("\nSolution:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((char) board[i][j] + " ");
            }
            System.out.println();
        }
    }
}