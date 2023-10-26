import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grid player1Grid = new Grid();
        Grid player2Grid = new Grid();
        Player currentPlayer = Player.PLAYER1;
        boolean gameOver = false;

        System.out.println("Welcome to Battleship!");
        player1Grid.placeShips();
        player2Grid.placeShips();

        while (!gameOver) {
            System.out.println(currentPlayer + ", it's your turn.");
            System.out.println("Enter a command (shoot, score, fleet, quit):");
            String command = scanner.nextLine();

            switch (command) {
                case "shoot" -> {
                    System.out.println("Please select a target position using the format 'A1' or 'B4' to take your shot:");
                    String target = scanner.nextLine();
                    if (currentPlayer == Player.PLAYER1) {
                        player2Grid.shoot(target);
                    } else {
                        player1Grid.shoot(target);
                    }
                }
                case "score" -> {
                    if (currentPlayer == Player.PLAYER1) {
                        System.out.println("Player 1 score: " + player1Grid.getScore());
                    } else {
                        System.out.println("Player 2 score: " + player2Grid.getScore());
                    }
                }
                case "fleet" -> {
                    if (currentPlayer == Player.PLAYER1) {
                        player1Grid.printFleet();
                    } else {
                        player2Grid.printFleet();
                    }
                }
                case "quit" -> gameOver = true;
                default -> System.out.println("Invalid command");
            }

            if (player1Grid.isGameOver()) {
                System.out.println("Player 2 wins the game!");
                gameOver = true;
            } else if (player2Grid.isGameOver()) {
                System.out.println("Player 1 wins the game!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
            }
        }
    }
}



enum Player {
    PLAYER1, PLAYER2
}
