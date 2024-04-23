package exercises.snake.gameobjects.snake;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int colChange;
    private final int rowChange;

    Direction(int colChange, int rowChange) {
        this.colChange = colChange;
        this.rowChange = rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    public int getRowChange() {
        return rowChange;
    }
}
