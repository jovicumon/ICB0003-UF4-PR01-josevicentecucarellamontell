package model;

import java.util.List;
import java.util.ArrayList;

public class PlusCell extends GameCell implements Questionable {

    private String question;
    private List<String> correctAnswers; // Lista de respuestas válidas
    private int reward;

    // Constructor
    public PlusCell(int reward, String question) {
        super("++"); // Contenido de una celda de pregunta (++)
        this.reward = reward;
        this.question = question;
        this.correctAnswers = initializeCorrectAnswers(); // Inicializar la lista de respuestas válidas
    }

    // Método para inicializar la lista de respuestas válidas
    private List<String> initializeCorrectAnswers() {
        List<String> answers = new ArrayList<>();
        answers.add("IntelliJ");
        answers.add("Eclipse");
        answers.add("NetBeans");
        answers.add("Android Studio");
        answers.add("JDeveloper");
        return answers;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean submitAnswer(String answer) {
        // Normalizar la respuesta del jugador (eliminar espacios y convertir a minúsculas)
        String normalizedAnswer = answer.replaceAll("\\s", "").toLowerCase();

        // Verificar si la respuesta normalizada coincide con alguna respuesta válida
        for (String correctAnswer : correctAnswers) {
            String normalizedCorrectAnswer = correctAnswer.replaceAll("\\s", "").toLowerCase();
            if (normalizedAnswer.equals(normalizedCorrectAnswer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getScore() {
        return 0; // Las celdas PlusCell no dan puntos directamente, solo recompensan al acertar
    }

    public int getReward() {
        return reward;
    }
}