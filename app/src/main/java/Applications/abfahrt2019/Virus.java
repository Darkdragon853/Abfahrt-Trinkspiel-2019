package Applications.abfahrt2019;

public class Virus {
    private Slice slice;
    private int timer;

    public Virus(Slice slice, int timer) {
        this.slice = slice;
        this.timer = timer;
    }

    public Slice getSlice() {
        return this.slice;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer){
        this.timer = timer;
    }

    public String toString() {
        return new String(this.slice.getBeschreibung() + " Timer: " + this.timer);
    }
}