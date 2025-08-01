package exercises.snake.field;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import exercises.snake.gameobjects.fruit.Fruit;
import exercises.snake.gameobjects.snake.Snake;

public final class Field {

    private static final String BORDER_STRING = "▒";
    private static final String SNAKE_BODY_STRING = "#";
    private static final String SNAKE_HEAD_STRING = "0";
    private static final String SNAKE_TAIL_STRING = "€";
    private static final String FRUIT_STRING = "@";


    private static int width;
    private static int height;
    private static Screen screen;
    private static ScreenWriter screenWriter;

    private Field() {
    }

    public static void init(int width, int height) {

        screen = TerminalFacade.createScreen();

        Field.width = width;
        Field.height = height;
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        screenWriter = new ScreenWriter(screen);
        screen.setCursorPosition(null);
        screen.startScreen();

        drawWalls();
        screen.refresh();
    }

    public static void newScreen() {
        screen.clear();
        drawWalls();
        screen.refresh();
    }

    public static void draw(Snake snake) {

        Terminal.Color snakeColor = Terminal.Color.GREEN;

        if (!snake.isAlive()) {
            snakeColor = Terminal.Color.RED;
        }

        Position head = snake.getHead();

        for (Position p : snake.getFullSnake()) {
            if (p.equals(snake.getTail())) {
                screen.putString(p.getCol(), p.getRow(), SNAKE_TAIL_STRING, snakeColor, null);
            } else if (!p.equals(head)) {
                screen.putString(p.getCol(), p.getRow(), SNAKE_BODY_STRING, snakeColor, null);
            } else {
                screen.putString(p.getCol(), p.getRow(), SNAKE_HEAD_STRING, snakeColor, null);
            }
        }

        screen.refresh();
    }

    public static void updateScore(int score, int maxScore) {
        screen.putString(width / 3, 0, "Score: " + score, Terminal.Color.WHITE, null);
        screen.putString(width / 2, 0, "Max Score: " + maxScore, Terminal.Color.WHITE, null);
        screen.refresh();
    }

    public static void clearTail(Snake snake) {
        Position tail = snake.getTail();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }

    private static void drawWalls() {
        for (int i = 0; i < width; i++) {
            screenWriter.drawString(i, 0, BORDER_STRING);
            screenWriter.drawString(i, height - 1, BORDER_STRING);
        }

        for (int j = 0; j < height; j++) {
            screenWriter.drawString(0, j, BORDER_STRING);
            screenWriter.drawString(width - 1, j, BORDER_STRING);
        }
    }

    public static Key readInput() {
        return screen.readInput();
    }

    public static void drawFruit(Fruit fruit) {
        screen.putString(fruit.getPosition().getCol(), fruit.getPosition().getRow(), FRUIT_STRING, Terminal.Color.RED, null);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void drawGameOverScreen() {
        screen.putString(width / 2 - 5, height / 2, "GAME OVER", Terminal.Color.RED, Terminal.Color.BLACK);
        screen.putString(width / 2 - 17, height / 2 + 1, "Press enter to retry and esc to quit", Terminal.Color.RED, Terminal.Color.BLACK);
        screen.refresh();


    }

    public static void closeScreen() {
        screen.stopScreen();
    }
}
