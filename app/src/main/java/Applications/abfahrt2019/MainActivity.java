// Textview Global machen und Gravity ändern


package Applications.abfahrt2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.fonts.Font;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.List;


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
    private HashSet<Slice> manyInteracts;
    private HashSet<String> spieler;



    private List<Slice> momentaneViren;

    private List<Integer> counters;

    private ConstraintLayout parentLayout;

    private Random randomGenerator;

    private Slice endSlice;

    private int sliceCount;
    private int anzahlSlices;
    private int virusDauerIntervall = 1;
    private int virusDauerOffset = 1;

    private boolean finished = false;

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
    private String[] manyInteractsArray = {
            "§, % und & treten gegen `, # und - an. Der Rest bildet die Jury. Jeder erfindet eine Sexposition und gibt ihr einen Namen. Das kreativste Team gewinnt. Die Verlierer trinken 3 Schlücke. (Vlt ne Version mit 2vs2 bei weniger Spielern. 3vs3 erst ab 8 Spielern)"
    };


    private void Abfahren() {

        // Bereinige zuerst Oberfläcche
        hideSystemUI();
        clearButton();
        Log.d("debug", "Starting Abfahren");

        // Initialisiere Sets usw
        Initialize();
        // DummyFillers der Spieler
        testFill();
        // Theoretisch müssten jetzt alle Fragen im entsprechenden Set sein. Jetzt setzen wir die Namen in die Fragen ein.
        //Log.d("debug", "Starting Interact");
        Interact();
        // Jetzt noch für SpezialFälle
        //Log.d("debug", "Endging Interact");


        // Log.d("debug", "Starting InteractLArge");
        InteractLarge();
        // Log.d("debug", "Endging InteractLarge");
        // Hier geht das Spiel jetzt los. Wir beginnen mit einem manuellen Aufruf, der Rest kommt über die Touches
        nextSlice();

        //  Log.d("debug/slice", s.toString());
        //Debug:
        /*
        if(fragenNormal.isEmpty()) Log.d("debug", "Empty");
        // DebugTime, grab a Slice and represent it
        for(Slice s1 : spieleHeiss) {
            Log.d("debug", s1.toString());
        }
        */
    }

    // UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = findViewById(R.id.parentLayout);


        // Linke den Button zu unserer Start-Methode
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view ->  {
            // Hier kommt der Aufruf unserer Anfangsmethode rein
            Abfahren();
        });

        parentLayout.setOnTouchListener( (view, motionEvent) -> {
            nextSlice();
            // Log.d("debug/touch", "Touched");
            return false;
        });

    }

    // Dreht Screen in den Fullscreen und versteckt Systemleisten
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            // Immersiv-Mode
            View.SYSTEM_UI_FLAG_IMMERSIVE
            // Verhindere Resize wenn Systemleisten angezeigt werden sollen
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Verstecke Status- und NavigationsLeiste
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN );
    }    
    
    private void clearButton() {
      Button startButton = findViewById(R.id.startButton);
      ViewGroup layout = (ViewGroup) startButton.getParent();  
      if(layout != null) {
        layout.removeView(startButton);     
      }
    }   

    private void handleSlice(Slice slice) {
        // Zuerst setzen wir den Hintergrund. Dafür brauchen wir noch eine LayoutID mit Namen parentLayout!
        // Fallunterscheidung

        if(slice.getKategorie().equals(Slice.Level.Normal) && slice.getTyp().equals(Slice.Type.Frage)) {
            // Normale Frage
            parentLayout.setBackgroundResource(R.drawable.gradientnormalfrage);
        }
        else if(slice.getKategorie().equals(Slice.Level.Normal) && slice.getTyp().equals(Slice.Type.Spiel)) {
            // Normales Spiel
            parentLayout.setBackgroundResource(R.drawable.gradientnormalspiel);
        }
        else if(slice.getKategorie().equals(Slice.Level.Normal) && slice.getTyp().equals(Slice.Type.Virus)) {
            // Normaler Virus .. mach autistischer Kick draus
            parentLayout.setBackgroundResource(R.drawable.gradientnormalvirus);

            // Zufällige Rundenzahl, in Counters gestored
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;
            counters.add(randomInt);

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(newSlice);
            Log.d("debug/SecondVirs", "Virus added");
        }
        else if(slice.getKategorie().equals(Slice.Level.Warm) && slice.getTyp().equals(Slice.Type.Frage)) {
            // Warme Frage
            parentLayout.setBackgroundResource(R.drawable.gradientwarmfrage);
        }
        else if(slice.getKategorie().equals(Slice.Level.Warm) && slice.getTyp().equals(Slice.Type.Spiel)) {
            // Warmes Spiel
            parentLayout.setBackgroundResource(R.drawable.gradientwarmspiel);
        }
        else if(slice.getKategorie().equals(Slice.Level.Warm) && slice.getTyp().equals(Slice.Type.Virus)) {
            // Warmer Virus
            parentLayout.setBackgroundResource(R.drawable.gradientwarmvirus);

            // Zufällige Rundenzahl, in Counters gestored
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;
            counters.add(randomInt);

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(newSlice);
            Log.d("debug/SecondVirs", "Virus added");
        }
        else if(slice.getKategorie().equals(Slice.Level.Heiss) && slice.getTyp().equals(Slice.Type.Frage)) {
            // Heisse Frage
            parentLayout.setBackgroundResource(R.drawable.gradientheissfrage);
        }
        else if(slice.getKategorie().equals(Slice.Level.Heiss) && slice.getTyp().equals(Slice.Type.Spiel)) {
            // Heisses Spiel
            parentLayout.setBackgroundResource(R.drawable.gradientheissspiel);
        }
        else {
            // Heisser Virus
            parentLayout.setBackgroundResource(R.drawable.gradientheissvirus);

            // Zufällige Rundenzahl, in Counters gestored
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;
            counters.add(randomInt);

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(newSlice);
            Log.d("debug/SecondVirs", "Virus added");
        }

        // Jetzt brauchen wir noch den entsprechenden Text, Style
        Font style = slice.getStyle();



        // Text setzen
        String text = slice.getBeschreibung();


        TextView textView = findViewById(R.id.textView);

        //TextView nun bearbeiten falls nötig
        textView.setText(text);
    }
  
    // HelperMethods
    // Soll nun eine neue Frage auswählen
    private void nextSlice() {
        boolean virusFirst = false;
        // Behandle zuerst Viren
        if(!momentaneViren.isEmpty()) {
            Log.d("debug/Viren", "Inhalt momentane Viren bei 0: " + momentaneViren.get(0));
            for(Integer i : counters) {
                Log.d("debug/Viren", "inhalt von Counters: " + i);
                if(i == 0) {
                    Log.d("debug/Viren", "Momentanes i war 0 ");
                    virusFirst = true;
                    handleSecondVirusPart();
                    counters.remove(i);
                }
                else {
                    Log.d("debug/Viren", "Momentanes i wird jetzt um eins verringert ");
                    int j = i-1;
                    counters.remove(i);
                    counters.add(j);
                }
            }
        }
        // Normaler Spiel-Flow
        if(!finished && !virusFirst) {
            ++sliceCount;
            // getRandomSet
            float factor = sliceCount / anzahlSlices; float fragenNormalWs; float fragenWarmWs; float fragenHeissWs; float spieleNormalWs; float spieleWarmWs; float spieleHeissWs; float virusNormalWs; float virusWarmWs; float virusHeissWs;
            int temp = randomGenerator.nextInt(99) + 1; // Werte zwischen 0 und 100
            //0% - 30% des Games
            Slice currentSlice = new Slice("Something failed in NextSlice", Slice.Level.Normal, false, Slice.Type.Frage);

            // 0%-30% des Games
            if(factor <= 0.3) {

                // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                if(temp <= 15) {
                   //pick set X, [1,15]

                    if(fragenNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenNormal);
                    }
               }
               else if (temp <= 30) {
                   //pick set X, [16,30]

                   if(fragenWarm.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(fragenWarm);
                   }
               }
               else if (temp <= 45) {
                   //pick set X, [31,45]

                   if(fragenHeiss.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(fragenHeiss);
                   }
               }
               else if (temp <= 60) {
                   //pick set X, [46,60]

                   if(spieleNormal.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(spieleNormal);
                   }
               }
               else if (temp <= 75) {
                   //pick set X, [61,75]

                   if(spieleWarm.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(spieleWarm);
                   }
               }
               else if (temp <= 85) {
                   //pick set X, [61,80]

                   if(spieleHeiss.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(spieleHeiss);
                   }
               }
               else if (temp <= 90) {
                   //pick set X, [61,80]

                   if(virusNormal.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(virusNormal);
                   }
               }
               else if (temp <=95) {
                   //pick set X, [61,80]

                   if(virusWarm.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(virusWarm);
                   }
               }
               else {
               //pick set X, [81,100]

                   if(virusHeiss.isEmpty()) {
                       --sliceCount; nextSlice(); return;
                   }
                   else {
                       currentSlice = pickRandomSlice(virusHeiss);
                   }
               }

            }
            //
            // 31% - 60% des Games
            //
            else if(factor <= 0.6) {

                // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                if(temp <= 15) {
                    //pick set X, [1,15]

                    if(fragenNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenNormal);
                    }
                }
                else if (temp <= 30) {
                    //pick set X, [16,30]

                    if(fragenWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenWarm);
                    }
                }
                else if (temp <= 45) {
                    //pick set X, [31,45]

                    if(fragenHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenHeiss);
                    }
                }
                else if (temp <= 60) {
                    //pick set X, [46,60]

                    if(spieleNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleNormal);
                    }
                }
                else if (temp <= 75) {
                    //pick set X, [61,75]

                    if(spieleWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleWarm);
                    }
                }
                else if (temp <= 85) {
                    //pick set X, [61,80]

                    if(spieleHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleHeiss);
                    }
                }
                else if (temp <= 90) {
                    //pick set X, [61,80]

                    if(virusNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusNormal);
                    }
                }
                else if (temp <=95) {
                    //pick set X, [61,80]

                    if(virusWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusWarm);
                    }
                }
                else {
                    //pick set X, [81,100]

                    if(virusHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusHeiss);
                    }
                }

            }
            //
            // 61% - 100% des Games
            //
            else {

                // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                if(temp <= 15) {
                    //pick set X, [1,15]

                    if(fragenNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenNormal);
                    }
                }
                else if (temp <= 30) {
                    //pick set X, [16,30]

                    if(fragenWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenWarm);
                    }
                }
                else if (temp <= 45) {
                    //pick set X, [31,45]

                    if(fragenHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(fragenHeiss);
                    }
                }
                else if (temp <= 60) {
                    //pick set X, [46,60]

                    if(spieleNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleNormal);
                    }
                }
                else if (temp <= 75) {
                    //pick set X, [61,75]

                    if(spieleWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleWarm);
                    }
                }
                else if (temp <= 85) {
                    //pick set X, [61,80]

                    if(spieleHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(spieleHeiss);
                    }
                }
                else if (temp <= 90) {
                    //pick set X, [61,80]

                    if(virusNormal.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusNormal);
                    }
                }
                else if (temp <=95) {
                    //pick set X, [61,80]

                    if(virusWarm.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusWarm);
                    }
                }
                else {
                    //pick set X, [81,100]

                    if(virusHeiss.isEmpty()) {
                        --sliceCount; nextSlice(); return;
                    }
                    else {
                        currentSlice = pickRandomSlice(virusHeiss);
                    }
                }

            }

           // Log.d("debug/touch", "nextSlice aufgerufen");
            
            // draw the Slice
            handleSlice(currentSlice); // Vielleicht sogar krasser verschachteln, dass die Farbe besser zuzuordnen ist

            // finished?
            if (sliceCount == anzahlSlices) finished = true;
        }
        else if(!virusFirst) {
            //Finish -> Restart App?
            handleSlice(endSlice);
            restartApplication();
        }
    }


    // Zeigt die Aufhebung eines Virus an
    private void handleSecondVirusPart() {
        if(momentaneViren.isEmpty()) Log.d("Debug/viren", "IRgendwas ist mit HandleSecondVIren kaputt, wurde ohne Counterpart gecallt");
        Slice currentSlice = momentaneViren.get(0);
            if ( currentSlice.getKategorie().equals(Slice.Level.Normal)) {
                // Normal
                parentLayout.setBackgroundResource(R.drawable.gradientnormalvirus);
            } else if( currentSlice.getKategorie().equals(Slice.Level.Warm)) {
                // Warm
                parentLayout.setBackgroundResource(R.drawable.gradientwarmvirus);
            } else {
                // Heiss
                parentLayout.setBackgroundResource(R.drawable.gradientheissvirus);
            }
        TextView tv = findViewById(R.id.textView);
        tv.setText(currentSlice.getBeschreibung());
        Log.d("debug/SecondVirs", "aufgerufen:  " + currentSlice.getBeschreibung());
    }




    // Startet die App neu
    private void restartApplication() {

    }

    // Füllt erstmal Namen rein
    private void testFill() {
        spieler.add("Lukas");
        spieler.add("Patrick");
        spieler.add("Luisa");
        spieler.add("Robin");
        spieler.add("Daddl");
        spieler.add("Leonie");
        spieler.add("Felix");
        spieler.add("Alfred");
    }

    // Sucht eine zufällige Slice aus dem Set aus
    private Slice pickRandomSlice(HashSet<Slice> set) {
        int i = 0;
        Slice finalSlice = new Slice("Something failed in PickRandomSlice", Slice.Level.Heiss, false, Slice.Type.Frage);
        int item = randomGenerator.nextInt(set.size());
        for(Slice s : set) {
            if(item == i) {
                // Wirf Slice aus der Menge und gib es zurück
                set.remove(s);
                return s;
            }
            else {
                i++;
            }
        }
        return finalSlice;
    }

    //ManyInteracts noch
    private void InteractLarge() {
        for(Slice s : manyInteracts) {
            HashSet<Integer> temp = new HashSet<Integer>();
            // Hol dir zuerst die Beschreibung
            String current = s.getBeschreibung();

            // Erzeuge 3 einzigartige Zahlen
            int item1, item2, item3, item4, item5, item6;
            do {
                temp.clear();
                item1 = randomGenerator.nextInt(spieler.size());
                item2 = randomGenerator.nextInt(spieler.size());
                item3 = randomGenerator.nextInt(spieler.size());
                item4 = randomGenerator.nextInt(spieler.size());
                item5 = randomGenerator.nextInt(spieler.size());
                item6 = randomGenerator.nextInt(spieler.size());
                temp.add(item1);
                temp.add(item2);
                temp.add(item3);
                temp.add(item4);
                temp.add(item5);
                temp.add(item6);
            }
            while (temp.size() < 6);

            // Weise den Zahlen Spieler zu

            int i = 0;
            String Spieler1 = "";
            String Spieler2 = "";
            String Spieler3 = "";
            String Spieler4 = "";
            String Spieler5 = "";
            String Spieler6 = "";
            for (String string : spieler) {
                if (item1 == i) {
                    Spieler1 = string;
                } else if (item2 == i) {
                    Spieler2 = string;
                } else if (item3 == i) {
                    Spieler3 = string;
                } else if (item4 == i) {
                    Spieler4 = string;
                } else if (item5 == i) {
                    Spieler5 = string;
                } else if (item6 == i) {
                    Spieler6 = string;
                }
                i++;
            }

            // Jetzt replace#
          //  Log.d("debug/spieler", Spieler1 + " " +Spieler2 + " " +Spieler3 + " " +Spieler4 + " " +Spieler5 + " " +Spieler6);
         //   Log.d("debug/current", current);
            String replaced = current.replaceAll("§", Spieler1).replaceAll("&", Spieler2).replaceAll("%", Spieler3).replaceAll("`", Spieler4).replaceAll("#", Spieler5).replaceAll("-", Spieler6);
           // Log.d("debug/replaced", replaced);
            s.setBeschreibung(replaced);

        }
    }

    private void Interact() {
        // Eliminiere alle X, Y, Z usw; Wähle zufällige Zahl zw. 0 und Anzahl Spieler, dieser Spieler kommt an Stelle X. Achtung, Keine DOPPLUNG!

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
        manyInteracts = new HashSet<Slice>();

        momentaneViren = new ArrayList<Slice>();
        counters = new ArrayList<Integer>();

        sliceCount = 0;
        anzahlSlices = 50;

        randomGenerator = new Random();

        endSlice = new Slice("Spiel Vorbei ihr Autisten!", Slice.Level.Normal, false, Slice.Type.Frage);

        // Fragen
        // Normale Fragen
        for(int i = 0; i < fragenNormalArray.length ; i++) {
            String current = fragenNormalArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Normal, temp, Slice.Type.Frage);
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

            Slice slice = new Slice(current, Slice.Level.Warm, temp, Slice.Type.Frage);
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

            Slice slice = new Slice(current, Slice.Level.Heiss, temp, Slice.Type.Frage);
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

            Slice slice = new Slice(current, Slice.Level.Normal, temp, Slice.Type.Spiel);
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

            Slice slice = new Slice(current, Slice.Level.Warm, temp, Slice.Type.Spiel);
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

            Slice slice = new Slice(current, Slice.Level.Heiss, temp, Slice.Type.Spiel);
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

            Slice slice = new Slice(current, Slice.Level.Normal, temp, Slice.Type.Virus);
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

            Slice slice = new Slice(current, Slice.Level.Warm, temp, Slice.Type.Virus);
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

            Slice slice = new Slice(current, Slice.Level.Heiss, temp, Slice.Type.Virus);
            // Rein in den FragenPool
            virusHeiss.add(slice);
        }

        // ManyInteracts
        for(int i = 0; i < manyInteractsArray.length ; i++) {
            String current = manyInteractsArray[i];

            Slice slice = new Slice(current, Slice.Level.Warm, true, Slice.Type.Frage);
            // Rein in den FragenPool
            manyInteracts.add(slice);
        }
    }
}
// Spieler Auswahl. Momentan werden Dummys gefillt
