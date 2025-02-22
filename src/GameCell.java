public abstract class GameCell {

    private boolean isDiscovered;
    protected String content;

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered() {
        isDiscovered = true;
    }



    @Override
    public String toString() {
        return  content;
    }
}
