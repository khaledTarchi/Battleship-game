import java.util.Arrays;
import java.util.Random;



class Grid {
    private static final int GRID_SIZE = 10;
    private final char[][] grid;
    private int score;
    private final int[] fleetSizes;
    private final boolean[] fleetSunk;

    public Grid() {
        grid = new char[GRID_SIZE][GRID_SIZE];
        score = 0;

        // Initialize the grid with empty sea
        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }

        // Initialize the fleet sizes and sunk status
        fleetSizes = new int[]{3, 3, 2, 2, 2, 1, 1, 1, 1};
        fleetSunk = new boolean[fleetSizes.length];
    }





    // Place ships on the grid--
   public void placeShips() {
        Random random = new Random();
        for (int size : fleetSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                int orientation = random.nextInt(2); // 0 for horizontal, 1 for vertical

                if (orientation == 0) { // Place horizontally
                    if (col + size <= GRID_SIZE) {
                        boolean canPlace = true;
                        for (int i = col; i < col + size; i++) {
                            if (grid[row][i] == 'S') {
                                canPlace = false;
                                break;
                            }
                        }
                        if (canPlace) {
                            for (int i = col; i < col + size; i++) {
                                grid[row][i] = 'S';
                            }
                            placed = true;
                        }
                    }
                } else { // Place vertically
                    if (row + size <= GRID_SIZE) {
                        boolean canPlace = true;
                        for (int i = row; i < row + size; i++) {
                            if (grid[i][col] == 'S') {
                                canPlace = false;
                                break;
                            }
                        }
                        if (canPlace) {
                            for (int i = row; i < row + size; i++) {
                                grid[i][col] = 'S';
                            }
                            placed = true;
                        }
                    }
                }
            }
        }
    }





    // Shoot at a target position
    public void shoot(String target) {
        // Convert target (e.g., "A1") to grid coordinates (0, 0)
        int row = target.charAt(0) - 'A';
        int col = Integer.parseInt(target.substring(1)) - 1;

        // Check if the target is a valid position
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE) {
            System.out.println("Invalid shot");
            return;
        }



        // Check if there's a ship at the target position
        if (grid[row][col] == 'S') {
            int shipSize = getShipSizeAt(row, col);
            score += shipSize * 100;
            grid[row][col] = 'X'; // Mark as hit
            System.out.println("Hit! You scored " + (shipSize * 100) + " points.");
            checkAndSinkShip(shipSize);
        } else if (grid[row][col] == 'X' || grid[row][col] == 'O') {
            System.out.println("You already shot there!");
        } else {
            grid[row][col] = 'O'; // Mark as miss
            System.out.println("Miss! No points earned.");
        }
    }




    // Get the size of the ship at a given position
    private int getShipSizeAt(int row, int col) {
        char shipSymbol = grid[row][col];
        int shipSize = 0;

        // Check horizontally
        for (int i = col; i < GRID_SIZE; i++) {
            if (grid[row][i] == shipSymbol) {
                shipSize++;
            } else {
                break;
            }
        }

        // Check vertically
        if (shipSize == 0) {
            for (int i = row; i < GRID_SIZE; i++) {
                if (grid[i][col] == shipSymbol) {
                    shipSize++;
                } else {
                    break;
                }
            }
        }
        return shipSize;
    }







    // Check if a ship is sunk and update the fleet status
    private void checkAndSinkShip(int shipSize) {
        for (int i = 0; i < fleetSizes.length; i++) {
            if (!fleetSunk[i] && fleetSizes[i] == shipSize) {
                // Check if the ship is completely sunk
                boolean isSunk = true;
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        if (grid[row][col] == 'S' && getShipSizeAt(row, col) == shipSize) {
                            isSunk = false;
                            break;
                        }
                    }
                    if (!isSunk) {
                        break;
                    }
                }

                if (isSunk) {
                    fleetSunk[i] = true;
                    System.out.println("You sunk a ship of size " + shipSize + "! You lose " + (shipSize * 30) + " points.");
                    score -= shipSize * 30;
                }
            }
        }
    }








    // Check if the game is over
    public boolean isGameOver() {
        for (boolean sunk : fleetSunk) {
            if (!sunk) {
                return false; // At least one ship is not sunk, the game is not over
            }
        }
        return true; // All ships are sunk, the game is over
    }





    // Print the current fleet arrangement
    public void printFleet() {
        System.out.println("Current Fleet Arrangement:");
        System.out.println("  1 2 3 4 5 6 7 8 9 10"); // Column labels

        for (int row = 0; row < GRID_SIZE; row++) {
            char rowLabel = (char) ('A' + row);
            System.out.print(rowLabel + " "); // Row label
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }




    // Get the current score
    public int getScore() {
        return score;
    }

}

