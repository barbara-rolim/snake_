package exercises.snake.gameobjects.snake;

import exercises.snake.field.Field;
import exercises.snake.field.Position;

import java.util.LinkedList;

public class Snake {

    private final static int SNAKE_INITIAL_SIZE = 3;
    private Direction direction;
    private boolean alive;
    private final LinkedList<Position> snakeBody;

    public Snake() {
        snakeBody = new LinkedList<>();
        buildInitialSnake();
        alive = true;
        direction = Direction.RIGHT;
    }

    private void buildInitialSnake() {
        int startingRowPosition = Field.getHeight() / 2;
        int startingColPosition = Field.getWidth() / 2;

        for (int i = 0; i < SNAKE_INITIAL_SIZE; i++) {
            snakeBody.add(new Position(startingColPosition, startingRowPosition));
            startingColPosition -= 1;
        }

    }

    public void increaseSize() {
        snakeBody.add(new Position(getTail().getCol(), getTail().getRow()));

    }

    public void move(Direction direction) {
        if (checkOpositeDirection(direction)) {
            move(this.direction);
            return;
        }
        Position head = getHead();
        snakeBody.addFirst(new Position(head.getCol() + direction.getColChange(), head.getRow() + direction.getRowChange()));
        snakeBody.remove(getTail());
        this.direction = direction;
    }

    private boolean checkOpositeDirection(Direction direction) {
        return direction.getRowChange() + this.direction.getRowChange() == 0 && direction.getColChange() + this.direction.getColChange() == 0;
    }

    public void move() {
        move(direction);
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getHead() {

        return snakeBody.getFirst();
    }

    public Position getTail() {

        return snakeBody.getLast();
    }

    public LinkedList<Position> getFullSnake() {

        return snakeBody;
    }

    public int getSnakeSize() {
        //size
        return snakeBody.size() - SNAKE_INITIAL_SIZE;
    }

}

