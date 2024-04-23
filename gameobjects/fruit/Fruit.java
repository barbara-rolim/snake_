package exercises.snake.gameobjects.fruit;


import exercises.snake.field.Field;
import exercises.snake.field.Position;
import java.util.Random;

public class Fruit {

    private final Position position;

    public Fruit() {
        Random random = new Random();
        position = new Position(random.nextInt(Field.getWidth() - 2) + 1, random.nextInt(Field.getHeight() - 2) + 1);
    }

    public Position getPosition() {
        return position;
    }
}
