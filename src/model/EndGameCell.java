package model;

public class EndGameCell extends GameCell {

    private EndGamecellType type;

    public EndGameCell(EndGamecellType type) {
        super(type.getSymbol()); // Contenido de la celda (CC o CH)
        this.type = type;

        // Hacer que solo el queso esté visible desde el inicio
        if (type == EndGamecellType.Cheese) {
            setDiscovered();
        }
    }


    public EndGamecellType getType() {
        return type;
    }

    @Override
    public int getScore() {
        return 0;
    }
}