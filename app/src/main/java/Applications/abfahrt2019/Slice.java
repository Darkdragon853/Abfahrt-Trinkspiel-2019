package Applications.abfahrt2019;

import android.graphics.Color;
import android.graphics.fonts.Font;

public class Slice {
    public enum Level {
        Normal,
        Warm,
        Heiss
    }

    private Level kategorie;
    private String beschreibung;
    private Color farbe;
    private Font style;
    private boolean interaktiv;


    public Slice(String beschreibung, Level Kategorie, boolean interaktiv) {
        this.beschreibung = beschreibung;
        this.interaktiv = interaktiv;
        this.kategorie = Kategorie;
    }

    public String toString() {
        String s = "Text: " + this.beschreibung + "; Kategorie: " + this.kategorie + ", Interaktiv: " + this.interaktiv + ", Farbe: " + this.farbe + ", Style: " + this.style;
        return s;
    }

    public boolean getInteraktiv() {
        return this.interaktiv;
    }
    public String getBeschreibung() {
        return this.beschreibung;
    }
    public Color getFarbe() {
     return this.farbe;   
    }
    public Font getStyle() {
        return this.style;
    }
    public void setBeschreibung(String s) {
        this.beschreibung = s;
    }
}
