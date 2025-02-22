package model;

public class RegularCell extends GameCell {
    private int score; // Almacena la puntuación de la celda

    public RegularCell(int score) {
        super(String.valueOf(score)); // Guarda el puntaje como contenido
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return isDiscovered() ? "·." : "00"; //Muestra ·. cuando pasa el ratón
    }
}
