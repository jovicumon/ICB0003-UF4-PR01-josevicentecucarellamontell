package model;

import java.util.Random;

public class MinusCell extends GameCell implements Questionable {

    private int correctNumber;
    private int penalty; // Campo para almacenar la penalización

    public MinusCell(int penalty) {
        super("--"); // Contenido de una celda de pregunta (--)
        this.penalty = penalty; // Inicializar la penalización
        generateNewNumber();
    }

    private void generateNewNumber() {
        correctNumber = new Random().nextInt(3) + 1;
    }

    @Override
    public String getQuestion() {
        return "Adivina un número del 1 al 3:";
    }

    @Override
    public boolean submitAnswer(String answer) {
        try {
            return Integer.parseInt(answer) == correctNumber;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int getScore() {
        return 0; // Las celdas MinusCell no dan puntos, solo penalizan
    }

    public int getPenalty() {
        return penalty; // Método para obtener la penalización
    }
}