package model;

public abstract class GameCell {

    private boolean isDiscovered;
    protected String content;

    public GameCell(String content) {
        this.content = content;
        this.isDiscovered = false;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered() {
        isDiscovered = true;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return isDiscovered ? content : "00";
    }

    public abstract int getScore();
}