package exercises.snake;

import com.googlecode.lanterna.input.Key;
import exercises.snake.field.Field;
import exercises.snake.field.Position;
import exercises.snake.gameobjects.fruit.Fruit;
import exercises.snake.gameobjects.snake.Direction;
import exercises.snake.gameobjects.snake.Snake;

import java.util.Iterator;


public class Game {

    private Snake snake;
    private Fruit fruit;
    private final int startDelay;
    private int delay;
    private int maxScore;

    public Game(int cols, int rows, int startDelay) {
        Field.init(cols, rows);
        this.startDelay = startDelay;
        maxScore = 0;
    }

    public void start() throws InterruptedException {
        snake = new Snake();
        generateFruit();
        delay = startDelay;
        Field.updateScore(snake.getSnakeSize(), maxScore);

        while (snake.isAlive()) {
            Field.draw(snake);
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
        }

        maxScore = Math.max(maxScore, snake.getSnakeSize());
        Field.updateScore(snake.getSnakeSize(), maxScore);
        Field.draw(snake);
        Field.drawGameOverScreen();

        restartGame();
    }


    private void restartGame() throws InterruptedException {
        while (true) {
            Key k = Field.readInput();
            if (k != null) {
                if (k.getKind().equals(Key.Kind.Enter)) {
                    Field.newScreen();
                    start();
                    return;
                }
                if (k.getKind().equals(Key.Kind.Escape)) {
                    Field.closeScreen();
                    return;
                }
            }
        }
    }

    private void generateFruit() {
        fruit = new Fruit();
        Field.drawFruit(fruit);
    }

    private void moveSnake() {

        Key k = Field.readInput();

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    snake.move(Direction.UP);
                    return;

                case ArrowDown:
                    snake.move(Direction.DOWN);
                    return;

                case ArrowLeft:
                    snake.move(Direction.LEFT);
                    return;

                case ArrowRight:
                    snake.move(Direction.RIGHT);
                    return;
            }
        }
        snake.move();
    }

    private void checkCollisions() {
        if (checkFruitCollision()) {
            delay--;
            snake.increaseSize();
            generateFruit();
            Field.updateScore(snake.getSnakeSize(), maxScore);
            return;
        }
        if (checkWallCollision()) {
            snake.die();
            return;
        }
        if (checkBodyColision()) {
            snake.die();
        }
    }

    private boolean checkBodyColision() {
        Iterator<Position> iterator = snake.getFullSnake().iterator();
        iterator.next();
        while (iterator.hasNext()) {
            if (snake.getHead().equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkWallCollision() {
        return snake.getHead().getRow() == 0 || snake.getHead().getRow() == Field.getHeight() - 1 || snake.getHead().getCol() == 0 || snake.getHead().getCol() == Field.getWidth() - 1;
    }

    private boolean checkFruitCollision() {
        return fruit.getPosition().equals(snake.getHead());
    }
}
