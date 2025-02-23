package model;

import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private GameCell[][] grid;
    private int mouseX, mouseY;
    private int score;
    private boolean testMode;
    private Random random;
    private Scanner scanner;

    public Board(boolean testMode) {
        this.testMode = testMode;
        this.mouseX = 0;
        this.mouseY = 0;
        this.score = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        initializeGrid();

        if (testMode) {
            printTestBoard(); // Mostrar tablero solución si está en modo test
        }
    }

    private void initializeGrid() {
        grid = new GameCell[4][4];

        // Inicializar todas las celdas regulares con puntuación aleatoria
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    grid[i][j] = new RegularCell(0);
                    grid[i][j].setDiscovered(); // Hacer que la celda del ratón sea visible
                } else if (i == 3 && j == 3) {
                    grid[i][j] = new EndGameCell(EndGamecellType.Cheese); // Queso (CH)
                } else {
                    int randomScore = (random.nextInt(3) + 1) * 10; // 10, 20 o 30 puntos
                    grid[i][j] = new RegularCell(randomScore);
                }
            }
        }

        // Colocar el gato en una celda aleatoria
        int[] catPos = getValidPosition();
        grid[catPos[0]][catPos[1]] = new EndGameCell(EndGamecellType.Cat); // Gato (CC)

        // Colocar una celda PlusCell (++) en una posición aleatoria
        int[] plusPos = getValidPosition();
        grid[plusPos[0]][plusPos[1]] = new PlusCell(50, "Di el nombre de un IDE para programar en JAVA");

        // Colocar una celda MinusCell (--) en una posición aleatoria
        int[] minusPos = getValidPosition();
        grid[minusPos[0]][minusPos[1]] = new MinusCell(50); // Penalización de 50 puntos
    }

    private int[] getValidPosition() {
        int x, y;
        do {
            x = random.nextInt(4);
            y = random.nextInt(4);
        } while ((x == 0 && y == 0) || (x == 3 && y == 3) || grid[x][y] instanceof EndGameCell || grid[x][y] instanceof PlusCell || grid[x][y] instanceof MinusCell);
        return new int[]{x, y};
    }

    private void printTestBoard() {
        System.out.println("Tablero Solución (Modo Test):");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] instanceof RegularCell) {
                    System.out.print("· "); // Mostrar celdas regulares como ·
                } else if (grid[i][j] instanceof EndGameCell) {
                    System.out.print(((EndGameCell) grid[i][j]).getType().getSymbol() + " ");
                } else if (grid[i][j] instanceof PlusCell) {
                    System.out.print("++ ");
                } else if (grid[i][j] instanceof MinusCell) {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }

    public boolean moveMouse(char direction) {
        int newX = mouseX;
        int newY = mouseY;

        // Calcular nueva posición
        switch (direction) {
            case 'W': newX--; break; // Arriba
            case 'A': newY--; break; // Izquierda
            case 'S': newX++; break; // Abajo
            case 'D': newY++; break; // Derecha
        }

        // Verificar límites del tablero
        if (newX < 0 || newX >= 4 || newY < 0 || newY >= 4) {
            System.out.println("Movimiento no válido: Fuera del tablero.");
            return false;
        }

        // Verificar si la celda ya fue descubierta (excepto si es el queso)
        if (grid[newX][newY].isDiscovered() && !(grid[newX][newY] instanceof EndGameCell)) {
            System.out.println("Movimiento no válido: Celda ya descubierta.");
            return false;
        }

        // Marcar la celda actual como descubierta antes de mover el ratón
        grid[mouseX][mouseY].setDiscovered();

        // Mover el ratón
        mouseX = newX;
        mouseY = newY;

        // Verificar si el ratón se movió a una celda especial
        if (grid[mouseX][mouseY] instanceof RegularCell) {
            score += ((RegularCell) grid[mouseX][mouseY]).getScore();
        }

        // Marcar la nueva celda como descubierta (excepto si es el queso)
        if (!(grid[mouseX][mouseY] instanceof EndGameCell)) {
            grid[mouseX][mouseY].setDiscovered();
        }

        // Mostrar la puntuación acumulada después de cada movimiento
        System.out.println("Puntuación acumulada: " + score);

        // Verificar si el juego ha terminado
        return isGameOver();
    }

    /**
     * Verifica si la celda actual es una celda de pregunta (++ o --).
     */
    public boolean isCurrentCellQuestionable() {
        return grid[mouseX][mouseY] instanceof Questionable;
    }

    /**
     * Obtiene la celda de pregunta actual.
     */
    public Questionable getCurrentQuestionableCell() {
        return (Questionable) grid[mouseX][mouseY];
    }

    /**
     * Añade puntos a la puntuación actual.
     */
    public void addScore(int points) {
        this.score += points;
    }

    private boolean hasValidMoves() {
        // Verificar movimientos posibles
        boolean canMoveUp = (mouseX > 0 && !grid[mouseX - 1][mouseY].isDiscovered());
        boolean canMoveDown = (mouseX < 3 && !grid[mouseX + 1][mouseY].isDiscovered());
        boolean canMoveLeft = (mouseY > 0 && !grid[mouseX][mouseY - 1].isDiscovered());
        boolean canMoveRight = (mouseY < 3 && !grid[mouseX][mouseY + 1].isDiscovered());

        // Si hay al menos un movimiento válido, el ratón no está atrapado
        return canMoveUp || canMoveDown || canMoveLeft || canMoveRight;
    }

    public boolean isGameOver() {
        // Verificar si el ratón alcanzó el queso
        if (grid[mouseX][mouseY] instanceof EndGameCell) {
            EndGameCell cell = (EndGameCell) grid[mouseX][mouseY];
            if (cell.getType() == EndGamecellType.Cheese) {
                return true; // El ratón encontró el queso
            } else if (cell.getType() == EndGamecellType.Cat) {
                return true; // El gato atrapó al ratón
            }
        }

        // Verificar si el ratón está atrapado (no tiene movimientos válidos)
        if (!hasValidMoves()) {
            return true; // El ratón está atrapado
        }

        return false; // El juego continúa
    }

    public void printGameOverMessage() {
        if (grid[mouseX][mouseY] instanceof EndGameCell) {
            EndGameCell cell = (EndGameCell) grid[mouseX][mouseY];
            if (cell.getType() == EndGamecellType.Cheese) {
                System.out.println("¡Has encontrado el queso! ¡Ganaste!");
            } else if (cell.getType() == EndGamecellType.Cat) {
                System.out.println("¡Oh no! Te ha atrapado el gato. ¡Juego terminado!");
            }
        } else if (!hasValidMoves()) {
            System.out.println("¡El ratón está atrapado! ¡Juego terminado!");
        }

        printFinalBoard(); // Mostrar el tablero final
    }

    private void printFinalBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == mouseX && j == mouseY) {
                    sb.append("MM "); // Mostrar el ratón
                } else if (grid[i][j] instanceof EndGameCell) {
                    EndGameCell cell = (EndGameCell) grid[i][j];
                    if (cell.isDiscovered()) {
                        // Mostrar el gato o el queso solo si están descubiertos
                        sb.append(cell.getContent()).append(" ");
                    } else {
                        // Mostrar celdas no descubiertas como 00
                        sb.append("00 ");
                    }
                } else {
                    // Mostrar celdas regulares
                    sb.append(grid[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.println("Tablero final:");
        System.out.println(sb.toString());
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == mouseX && j == mouseY) {
                    sb.append("MM "); // Mostrar el ratón
                } else if (grid[i][j] instanceof EndGameCell) {
                    EndGameCell cell = (EndGameCell) grid[i][j];
                    if (cell.isDiscovered()) {
                        // Mostrar el gato o el queso solo si están descubiertos
                        sb.append(cell.getContent()).append(" ");
                    } else {
                        // Mostrar celdas no descubiertas como 00
                        sb.append("00 ");
                    }
                } else if (grid[i][j] instanceof PlusCell && grid[i][j].isDiscovered()) {
                    sb.append("++ "); // Mostrar celdas PlusCell descubiertas
                } else if (grid[i][j] instanceof MinusCell && grid[i][j].isDiscovered()) {
                    sb.append("-- "); // Mostrar celdas MinusCell descubiertas
                } else if (grid[i][j] instanceof RegularCell) {
                    // Mostrar celdas regulares
                    sb.append(grid[i][j]).append(" ");
                } else {
                    // Mostrar celdas no descubiertas como 00
                    sb.append("00 ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}