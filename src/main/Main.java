package main;

import model.Board;
import model.Questionable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean testMode = args.length > 0 && args[0].equals("test");
        Board board = new Board(testMode);
        Scanner scanner = new Scanner(System.in);

        while (!board.isGameOver()) {
            System.out.println(board); // Mostrar el tablero
            System.out.print("Movimiento (W->Arriba/A->Izquierda/S->Abajo/D->Derecha): ");
            char move = getValidMove(scanner); // Obtener un movimiento válido
            board.moveMouse(move);

            // Verificar si el ratón está en una celda de pregunta
            if (board.isCurrentCellQuestionable()) {
                handleQuestionableCell(board, scanner);
            }
        }

        // Mostrar el mensaje de fin de juego y el tablero final
        board.printGameOverMessage();

        System.out.println("¡Juego terminado!");
        System.out.println("Puntuación final: " + board.getScore());
    }

    /**
     * Solicita al usuario un movimiento válido (W/A/S/D).
     */
    private static char getValidMove(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas
            if (input.length() == 1 && "WASD".contains(input)) {
                return input.charAt(0); // Retornar el carácter válido
            }
            System.out.print("Entrada no válida. Introduce W (arriba), A (izquierda), S (abajo) o D (derecha): ");
        }
    }

    /**
     * Maneja la interacción con una celda de pregunta (++ o --).
     */
    private static void handleQuestionableCell(Board board, Scanner scanner) {
        // Obtener la celda de pregunta actual
        Questionable cell = board.getCurrentQuestionableCell();
        // Mostrar la pregunta al usuario
        System.out.println("Pregunta: " + cell.getQuestion());
        // Leer la respuesta del usuario
        System.out.print("Tu respuesta: ");
        String answer = scanner.nextLine();

        // Verificar la respuesta del usuario
        if (cell.submitAnswer(answer)) {  //uso del submitAnswer
            System.out.println("¡Correcto!");
            board.addScore(cell instanceof model.PlusCell ? 50 : -50); // Sumar o restar puntos
        } else {
            System.out.println("Incorrecto.");
            board.addScore(cell instanceof model.MinusCell ? -50 : 0); // Restar puntos solo en MinusCell
        }

        System.out.println("Puntuación acumulada: " + board.getScore());
    }
}