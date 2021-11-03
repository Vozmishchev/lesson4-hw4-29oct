package lesso4.hw4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static char[][] field ;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static int scoreHuman;
    private static int scoreAi;
    private static int roundCounter;
    private static char dotHuman;
    private static char dotAi;
    //private static int lineWin =4;
    private static int size =8;

    public static void main(String[] args) {
        play();
    }

    private static void play() {
        while (true) {
            playRound();
            System.out.printf("SCORE: HUMAN     AI\n" +
                    "         %d       %d\n", scoreHuman, scoreAi);
            System.out.printf("Wanna play again? Y or N >>> ");
            if (!scanner.next().toLowerCase().equals("y")) {
                System.out.println("Good bye!");
                break;
            }
        }
    }

    private static void playRound() {
        System.out.printf("ROUND № %d\n", roundCounter+=1);
        initField(size,size);

        System.out.println("Please enter 'x' if you want to play with X, and something else for O >>> ");
        String x = scanner.next();
        if (x.toLowerCase().equals("x")) {
            dotHuman = DOT_X;
            dotAi = DOT_O;
        } else {
            dotHuman = DOT_O;
            dotAi = DOT_X;
        }
        printField();

        while (true) {
            if (dotHuman == DOT_X) {
                humanTurn();
                printField();
                if (checkAll(dotHuman)) break;
                aiTurn();
                printField();
                if (checkAll(dotAi)) break;
            } else {
                aiTurn();
                printField();
                if (checkAll(dotAi)) break;
                humanTurn();
                printField();
                if (checkAll(dotHuman)) break;
            }
        }
    }

    private static boolean checkAll(char dot) {
        if (checkWin(dot)) {
            if (dot == dotHuman) {
                System.out.println("Human WIN!!!");
                scoreHuman++;
            } else {
                System.out.println("AI WIN!!!");
                scoreAi++;
            }
            return true;
        }
        if (checkDraw()) return true;
        return false;
    }

    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) return false;
            }
        }
        System.out.println("DRAW");
        return true;
    }

    /*
     * Главное ДЗ:
     * Сделать проверку победы в циклах
     * В идеале, сделать такую проверку, которой неважен размер поля и длина выигрышной последовательности
     */

    /*Можете посмотреть и прокомментировать. Что тут не так */

    private static boolean checkLine(int start_x, int start_y, int dx, int dy, char dot)
    {
        for (int i = 0; i < size-1; i++)
        {
            if (field[start_x + i * dx][start_y + i * dy] != dot)
                return false;
        }
        return true;
    }

    private static boolean checkWin(char dot)
    {
        for (int i = 0; i < size-1; i++)
        {
            // проверяем строки
            if (checkLine(i, 0, 0, 1, dot)) return true;
            // проверяем столбцы
            if (checkLine(0, i, 1, 0, dot)) return true;
        }
        // проверяем диагонали
        if (checkLine(0, 0, 1, 1, dot)) return true;
        if (checkLine(0, size - 1, 1, -1, dot)) return true;
        return false;
    }



    private static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(y, x));
        field[y][x] = dotAi;
    }

    private static void humanTurn() {
        int x;
        int y;
        boolean isFirstTry = true;
        do {
            if (!isFirstTry) {
                System.out.println("Wrong coordinates, try again!");
            }
            isFirstTry = false;
            System.out.print("Please enter coordinates of your turn x & y split by whitespace >>>> ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x) || !isCellEmpty(y, x));
        field[y][x] = dotHuman;
    }

    private static boolean isCellValid(int y, int x) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private static boolean isCellEmpty(int y, int x) {
        return field[y][x] == DOT_EMPTY;
    }

    private static void initField(int sizeX, int sizeY) {
        fieldSizeX = sizeX;
        fieldSizeY = sizeY;
        field = new char[sizeY][sizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

    }
}

