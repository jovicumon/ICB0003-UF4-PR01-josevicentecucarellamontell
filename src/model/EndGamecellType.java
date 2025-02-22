package model;

public enum EndGamecellType {
    Cat("CC"),Cheese("CH");
    private final String symbol;

    EndGamecellType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
