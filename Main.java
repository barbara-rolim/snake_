package exercises.snake;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(75, 25, 100);
        try {
            game.start();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
