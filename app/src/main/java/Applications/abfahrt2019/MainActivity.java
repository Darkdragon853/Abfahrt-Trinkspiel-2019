package Applications.abfahrt2019;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import java.util.HashSet;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    // Attributes
    private HashSet<Slice> fragenNormal;
    private HashSet<Slice> spieleNormal;
    private HashSet<Slice> virusNormal;
    private HashSet<Slice> fragenWarm;
    private HashSet<Slice> spieleWarm;
    private HashSet<Slice> virusWarm;
    private HashSet<Slice> fragenHeiss;
    private HashSet<Slice> spieleHeiss;
    private HashSet<Slice> virusHeiss;

    private HashSet<String> spieler;

    private int sliceCount;
    private int anzahlSlices;

    private String[] fragenNormalArray =  {
            "Alle, die schon einmal an einem öffentlichen Ort Sex hatten, trinken 3 Schlucke.",
            "Die letzte Person, die ihre Füße vom Bodem hebt trinkt 2 Schlucke.",
            "§ leckt die Nase von & oder trinkt 2 Schlucke!"
    };
    private String[] spieleNormalArray =  {
            "§ und & spielen Piss-Pott(gegenüberstehend abwechselnd ein Fuß vor dem anderen stellen, bis ihr euch in der Mitte trefft). Der/Die Verlierer/in trinkt 4 Schlucke.",
            "Lieber Ausschweifungen mit einer Sexnummer haben oder FKK mit Freunden/Freundinnen machen? Stimmt alle gleichzeitig ab, die Verlierer trinken 2 Schlucke."
    };
    private String[] virusNormalArray =  {
            "§ und & sind das neue Dreamteam, wenn § trinkt, trinkt & mit. > § und &, ihr seid doch kein Dreamteam und dürft wieder alleine trinken."
    };
    private String[] fragenWarmArray = {
            "Alle trinken und ziehen ein Kleidungsstück aus!",
            "§ zieht & ein Kleidungsstück aus oder trinkt 4 Schlucke."
    };
    private String[] spieleWarmArray =  {
            "Boxershorts-Contest! Die Jungs zeigen alle ihre Boxershorts. Die Mädels bilden die Jury und wählen die schönste. Der Sieger verteilt 5 Schlücke.",
            "String-Contest! Die Mädels zeigen alle ihren String. Die Jungs bilden die Jury und wählen den schönsten. Die Siegerin verteilt 5 Schlücke."
    };
    private String[] virusWarmArray =  {
            "Loch-Spiel! Wann immer ihr wollt, formt einen Kreis mit Daumen und Zeigefinger zwischen Hüfte und Schulter. Jeder der reinguckt, zieht ein Kleidungsstück aus oder trink 4 Schlucke. > Das Loch-Spiel ist Vorbei!"
    };
    private String[] fragenHeissArray = {
            "§, wähle eine Person, die die Hand an deine Unterwäsche legen soll. 4 Schlucke, wenn er/sie sich weigert.",
            "§ gibt & einen Kuss auf den nackten Hintern oder trinkt 4 Schlucke."
    };
    private String[] spieleHeissArray =  {
            "Nacheinander wählt jeder eine Person, die ein Kleidungsstück ausziehen muss. Weigert sie sich, trinkt sie 2 Schlucke. § beginnt.",
            "Wettbewerb des schönsten Hinterns: Die Kerle machen die Show, die Mädels bilden die Jury. Der Gewinner verteilt 5 Schlucke."
    };
    private String[] virusHeissArray =  {

    };


    private void Abfahren() {
        //
        // Bereinige zuerst Oberfläcche


        // Initialisiere Sets usw
        Initialize();
        // DummyFillers
        testFill();
        // Theoretisch müssten jetzt alle Fragen im entsprechenden Set sein. Jetzt setzen wir die Namen in die Fragen ein.
        Interact();
        // Jetzt noch für SpezialFälle
        InteractLarge();




        // Debug:
        /*
        if(fragenNormal.isEmpty()) Log.d("debug", "Empty");
        // DebugTime, grab a Slice and represent it
        for(Slice s1 : spieleHeiss) {
            Log.d("debug", s1.toString());
        }
        */

        // Dann hol alle Slices zusammen in For-Schleifen und fülle HashSets, beim UPsetten müssen die Fragenb mit dem X bearbeitet werden. (Interaktiv -> Boolean + Random Wahl)
    }

    // UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linke den Button zu unserer Start-Methode
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view ->  {
            // Hier kommt der Aufruf unserer Anfangsmethode rein
            Abfahren();
        });
    }
    private void testFill() {
        spieler.add("Lukas");
        spieler.add("Patrick");
        spieler.add("Luisa");
        spieler.add("Robin");
    }

    private void InteractLarge() {


    }

    private void Interact() {
        // Eliminiere alle X, Y, Z usw; Wähle zufällige Zahl zw. 0 und Anzahl Spieler, dieser Spieler kommt an Stelle X. Achtung, Keine DOPPLUNG!
        Random randomGenerator = new Random();

        // Packe alle Slices in ein Set um nur ein mal zu Iterieren
        HashSet<Slice> allSlices = new HashSet<Slice>();
        allSlices.addAll(fragenNormal);
        allSlices.addAll(fragenWarm);
        allSlices.addAll(fragenHeiss);
        allSlices.addAll(spieleNormal);
        allSlices.addAll(spieleWarm);
        allSlices.addAll(spieleHeiss);
        allSlices.addAll(virusNormal);
        allSlices.addAll(virusWarm);
        allSlices.addAll(virusHeiss);


        // Methode mit HashSet als Übergabe?
        for(Slice s : allSlices) {
           // Log.d("debug/Interact Start", "Starting");
            if(s.getInteraktiv()) {

                        // Hol dir zuerst die Beschreibung, wir rechnen mit max. 3 Spielern
                String current = s.getBeschreibung();

                // Erzeuge 3 einzigartige Zahlen
                int item1, item2, item3;
                do {
                    item1 = randomGenerator.nextInt(spieler.size()); item2 = randomGenerator.nextInt(spieler.size()); item3 = randomGenerator.nextInt(spieler.size());
                }
                while(((item1 == item2) || (item1  == item3) || (item2 == item3)));


                // Jetzt suche die zu den Zahlen gehörenden Spieler
                int i = 0;
                String Spieler1 = "";  String Spieler2 = "";  String Spieler3 = "";
                for(String string: spieler) {
                    if( item1 == i)  {
                        Spieler1 = string;
                    }
                    else if( item2 == i)  {
                        Spieler2 = string;
                    }
                    else if( item3 == i)  {
                        Spieler3 = string;
                    }
                    i++;
                }

               // Log.d("debug/players", Spieler1 + " " + Spieler2 + " " + Spieler3 + " zahlen: " + item1 + " " + item2 + " " + item3);
               // Log.d("debug/current", current);


                // Replace die Zeichen mit den jeweiligen Spielern
                String replaced = current.replaceAll("§", Spieler1).replaceAll("&", Spieler2).replaceAll("%", Spieler3);
                //Log.d("debug/replaced", replaced);
                s.setBeschreibung(replaced);

            }
            else {
                continue;
            }
        }
       // Log.d("debug/Interact End", "Ending");
    }



    private void Initialize() {
        fragenNormal = new HashSet<Slice>();
        spieleNormal = new HashSet<Slice>();
        virusNormal = new HashSet<Slice>();
        fragenWarm  = new HashSet<Slice>();
        spieleWarm = new HashSet<Slice>();
        virusWarm = new HashSet<Slice>();
        fragenHeiss = new HashSet<Slice>();
        spieleHeiss = new HashSet<Slice>();
        virusHeiss = new HashSet<Slice>();
        spieler = new HashSet<String>();

        sliceCount = 0;


        // Fragen
        // Normale Fragen
        for(int i = 0; i < fragenNormalArray.length ; i++) {
            String current = fragenNormalArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Normal, temp);
            // Rein in den FragenPool
            fragenNormal.add(slice);
        }

        // Warme Fragen
        for(int i = 0; i < fragenWarmArray.length ; i++) {
            String current = fragenWarmArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Warm, temp);
            // Rein in den FragenPool
            fragenWarm.add(slice);
        }

        // Heisse Fragen
        for(int i = 0; i < fragenHeissArray.length ; i++) {
            String current = fragenHeissArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Heiss, temp);
            // Rein in den FragenPool
            fragenHeiss.add(slice);
        }


        // Spiele
        // Normale Spiele
        for(int i = 0; i < spieleNormalArray.length ; i++) {
            String current = spieleNormalArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Normal, temp);
            // Rein in den FragenPool
            spieleNormal.add(slice);
        }
        // Warme Spiele
        for(int i = 0; i < spieleWarmArray.length ; i++) {
            String current = spieleWarmArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Warm, temp);
            // Rein in den FragenPool
            spieleWarm.add(slice);
        }

        // Heisse Spiele
        for(int i = 0; i < spieleHeissArray.length ; i++) {
            String current = spieleHeissArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Heiss, temp);
            // Rein in den FragenPool
            spieleHeiss.add(slice);
        }

        // Viren
        // Normale Viren
        for(int i = 0; i < virusNormalArray.length ; i++) {
            String current = virusNormalArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Normal, temp);
            // Rein in den FragenPool
            virusNormal.add(slice);
        }
        // Warme Viren
        for(int i = 0; i < virusWarmArray.length ; i++) {
            String current = virusWarmArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Warm, temp);
            // Rein in den FragenPool
            virusWarm.add(slice);
        }

        // Heisse Viren
        for(int i = 0; i < virusHeissArray.length ; i++) {
            String current = virusHeissArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Heiss, temp);
            // Rein in den FragenPool
            virusHeiss.add(slice);
        }
    }
}
// Spieler Auswahl. Momentan werden Dummys gefillt