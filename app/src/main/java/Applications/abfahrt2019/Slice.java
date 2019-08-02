package Applications.abfahrt2019;

public class Slice {

    public enum Level {
        Normal,
        Warm,
        Heiss
    }
    public enum Type{
        Frage,
        Spiel,
        Virus
    }

    private Level kategorie;
    private Type typ;
    private String beschreibung;
    private boolean interaktiv;

    public Slice(String beschreibung, Level Kategorie, boolean interaktiv, Type typ) {
        this.beschreibung = beschreibung;
        this.interaktiv = interaktiv;
        this.kategorie = Kategorie;
        this.typ = typ;
    }

    public String toString() {
        String s = "Text: " + this.beschreibung + "; Kategorie: " + this.kategorie + ", Interaktiv: " + this.interaktiv;
        return s;
    }

    public boolean getInteraktiv() {
        return this.interaktiv;
    }
    public String getBeschreibung() {
        return this.beschreibung;
    }
    public Level getKategorie() {
        return this.kategorie;
    }
    public void setBeschreibung(String s) {
        this.beschreibung = s;
    }
    public Type getTyp() { return this.typ; }
}