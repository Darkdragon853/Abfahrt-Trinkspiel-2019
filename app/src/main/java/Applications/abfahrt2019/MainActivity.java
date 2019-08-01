package Applications.abfahrt2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.app.PendingIntent;
import android.app.AlarmManager;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.regex.Pattern;


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

    private List<Virus> momentaneViren;

    private ConstraintLayout parentLayout;

    private Random randomGenerator;

    private Slice endSlice;

    private TextView textView;

    private int sliceCount;
    private int anzahlSlices;
    private int virusDauerIntervall = 1;
    private int virusDauerOffset = 2;

    private int debugCounter = 0;

    private boolean finished = false;
    private boolean started = false;
    private boolean restart = false;

    private boolean player1checked = true;
    private boolean player2checked = true;
    private boolean player3checked = true;
    private boolean player4checked = true;
    private boolean player5checked = true;
    private boolean player6checked = true;
    private boolean player7checked = true;
    private boolean player8checked = true;


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
        //Log.d("debug", "Starting Abfahren");


        // Initialisiere Sets usw
        Initialize();
        // Loading Players
        loadPlayerBase();
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
        started = true;
    }

    // UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = findViewById(R.id.parentLayout);

        ImageButton spieler1 = findViewById(R.id.spieler1Button);
        spieler1.setOnClickListener(v -> {

            if(player1checked) {
                // Lade Bild mit Häkchen
                spieler1.setImageResource(R.drawable.littleavatar);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler1.setImageResource(R.drawable.littleavatar);
            }

            //Swap states
            player1checked = !player1checked;
            Log.d("Debug/players", "Player1 check: " + player1checked);
        }); // ÜBernehme noch für die andere Buttons


        ImageButton spieler2 = findViewById(R.id.spieler2Button);
        spieler2.setOnClickListener(v -> {
            spieler2.setImageResource(R.drawable.littleavatar);
        });

        ImageButton spieler3 = findViewById(R.id.spieler3Button);
        spieler3.setOnClickListener(v -> {
            spieler3.setImageResource(R.drawable.littleavatar);
        });

        ImageButton spieler4 = findViewById(R.id.spieler4Button);
        spieler4.setOnClickListener(v -> {
            spieler4.setImageResource(R.drawable.littleavatar);
        });

        ImageButton spieler5 = findViewById(R.id.spieler5Button);
        spieler5.setOnClickListener(v -> {
            spieler5.setImageResource(R.drawable.littleavatar);
        });


        ImageButton spieler6 = findViewById(R.id.spieler6Button);
        spieler6.setOnClickListener(v -> {
            spieler6.setImageResource(R.drawable.littleavatar);
        });

        ImageButton spieler7 = findViewById(R.id.spieler7Button);
        spieler7.setOnClickListener(v -> {
            spieler7.setImageResource(R.drawable.littleavatar);
        });

        ImageButton spieler8 = findViewById(R.id.spieler8Button);
        spieler8.setOnClickListener(v -> {
            spieler8.setImageResource(R.drawable.littleavatar);
        });



        // Linke den startButton zu unserer Start-Methode
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view ->  {
            // Hier kommt der Aufruf unserer Anfangsmethode rein
            Abfahren();
        });

        parentLayout.setOnTouchListener( (view, motionEvent) -> {
            if(started) {
                nextSlice();
            }
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
        // StartButton
        Button startButton = findViewById(R.id.startButton);
        startButton.setVisibility(View.GONE);

        ImageButton currentButton;
        // Spieler1
        currentButton = findViewById(R.id.spieler1Button);
        currentButton.setVisibility(View.GONE);
        // Spieler2
        currentButton = findViewById(R.id.spieler2Button);
        currentButton.setVisibility(View.GONE);
        // Spieler3
        currentButton = findViewById(R.id.spieler3Button);
        currentButton.setVisibility(View.GONE);
        // Spieler4
        currentButton = findViewById(R.id.spieler4Button);
        currentButton.setVisibility(View.GONE);
        // Spieler5
        currentButton = findViewById(R.id.spieler5Button);
        currentButton.setVisibility(View.GONE);
        // Spieler6
        currentButton = findViewById(R.id.spieler6Button);
        currentButton.setVisibility(View.GONE);
        // Spieler7
        currentButton = findViewById(R.id.spieler7Button);
        currentButton.setVisibility(View.GONE);
        // Spieler8
        currentButton = findViewById(R.id.spieler8Button);
        currentButton.setVisibility(View.GONE);

        //Edit Text
        EditText editText = findViewById(R.id.editText);
        editText.setVisibility(View.GONE);

        // Zweite TextView
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setVisibility(View.GONE);
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

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
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

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
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

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String("Autistischer Kick! \n" + strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
            Log.d("debug/SecondVirs", "Virus added");
        }

        // Jetzt brauchen wir noch den entsprechenden Text, Style
        if(slice.getTyp().equals(Slice.Type.Frage)) {
            // Frage
            Typeface typeface = getResources().getFont(R.font.frage);
            textView.setTypeface(typeface);

        }
        else if(slice.getTyp().equals(Slice.Type.Spiel)) {
            // Spiel
            Typeface typeface = getResources().getFont(R.font.spiel);
            textView.setTypeface(typeface);
        }
        else {
            // virus
            Typeface typeface = getResources().getFont(R.font.virus);
            textView.setTypeface(typeface);
        }

        // Text setzen
        String text = slice.getBeschreibung();


        //TextView nun bearbeiten falls nötig
        textView.setText(text);
    }
  
    // HelperMethods
    // Soll nun eine neue Frage auswählen
    private void nextSlice() {
        // Restart?
        if(restart) {
            restartApplication(this);
        }

        // Normal


        ++debugCounter; Log.d("debug/counterNextSlice", "FebugCOunter:= " + debugCounter);
        boolean virusFirst = false;
        // Behandle zuerst Viren
        if(!momentaneViren.isEmpty() && !finished) {
            Virus destroyVirus = null;
            for(Virus virus : momentaneViren) {
                if(virus.getTimer() == 0) {
                    virusFirst = true;
                    Log.d("debug/Virus", virus.toString());
                    handleSecondVirusPart(virus.getSlice());
                    destroyVirus = virus;
                }
                else {
                    Log.d("debug/Viren", "Momentaner Virus wird jetzt um eins verringert: " + virus);
                    virus.setTimer(virus.getTimer() - 1);
                }
            }
            if(destroyVirus != null) {
                momentaneViren.remove(destroyVirus);
                destroyVirus = null;
            }
        }
        // Normaler Spiel-Flow
        if(!finished && !virusFirst) {
            ++sliceCount;
            // getRandomSet
            float factor = sliceCount / anzahlSlices; float fragenNormalWs; float fragenWarmWs; float fragenHeissWs; float spieleNormalWs; float spieleWarmWs; float spieleHeissWs; float virusNormalWs; float virusWarmWs; float virusHeissWs;

            //0% - 30% des Games
            Slice currentSlice = null;


            while(currentSlice == null) {
                int temp = randomGenerator.nextInt(99) + 1; // Werte zwischen 0 und 100
                // 0%-30% des Games
                if (factor <= 0.3) {

                    // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                    if (temp <= 15) {
                        //pick set X, [1,15]

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 30) {
                        //pick set X, [16,30]

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } else if (temp <= 45) {
                        //pick set X, [31,45]

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } else if (temp <= 60) {
                        //pick set X, [46,60]

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 75) {
                        //pick set X, [61,75]

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } else if (temp <= 85) {
                        //pick set X, [61,80]

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } else if (temp <= 90) {
                        //pick set X, [61,80]

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 95) {
                        //pick set X, [61,80]

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {
                        //pick set X, [81,100]

                        if (virusHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusHeiss);
                        }
                    }

                }
                //
                // 31% - 60% des Games
                //
                else if (factor <= 0.6) {

                    // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                    if (temp <= 15) {
                        //pick set X, [1,15]

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 30) {
                        //pick set X, [16,30]

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } else if (temp <= 45) {
                        //pick set X, [31,45]

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } else if (temp <= 60) {
                        //pick set X, [46,60]

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 75) {
                        //pick set X, [61,75]

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } else if (temp <= 85) {
                        //pick set X, [61,80]

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } else if (temp <= 90) {
                        //pick set X, [61,80]

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 95) {
                        //pick set X, [61,80]

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {
                        //pick set X, [81,100]

                        if (virusHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusHeiss);
                        }
                    }

                }
                //
                // 61% - 100% des Games
                //
                else {

                    // Schaut zuerst ob das jeweilige Set leer ist. Wenn ja, starte neue Iteration von NextSlice. Sonst wähle zufällig Slice aus Set.
                    if (temp <= 15) {
                        //pick set X, [1,15]

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 30) {
                        //pick set X, [16,30]

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } else if (temp <= 45) {
                        //pick set X, [31,45]

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } else if (temp <= 60) {
                        //pick set X, [46,60]

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 75) {
                        //pick set X, [61,75]

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } else if (temp <= 85) {
                        //pick set X, [61,80]

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } else if (temp <= 90) {
                        //pick set X, [61,80]

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 95) {
                        //pick set X, [61,80]

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {
                        //pick set X, [81,100]

                        if (virusHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusHeiss);
                        }
                    }

                }
            }
           // Log.d("debug/touch", "nextSlice aufgerufen");

            // draw the Slice
            handleSlice(currentSlice);
            // finished?
            if (sliceCount == anzahlSlices) finished = true;
        }
        else if(!virusFirst) {
            //Finish -> Restart App?
            handleSlice(endSlice);
            Log.d("debug/all", "game ended");
            // Short Stop
            restart = true;
        }
    }


    // Zeigt die Aufhebung eines Virus an
    private void handleSecondVirusPart(Slice slice) {
       // if(momentaneViren.isEmpty()) Log.d("Debug/viren", "IRgendwas ist mit HandleSecondVIren kaputt, wurde ohne Counterpart gecallt");
        if ( slice.getKategorie().equals(Slice.Level.Normal)) {
            // Normal
            parentLayout.setBackgroundResource(R.drawable.gradientnormalvirus);
        } else if( slice.getKategorie().equals(Slice.Level.Warm)) {
            // Warm
            parentLayout.setBackgroundResource(R.drawable.gradientwarmvirus);
        } else {
            // Heiss
            parentLayout.setBackgroundResource(R.drawable.gradientheissvirus);
        }

        // Virus-Font und Text
        Typeface typeface = getResources().getFont(R.font.virus);
        textView.setTypeface(typeface);
        textView.setText(slice.getBeschreibung());
        Log.d("debug/SecondVirs", "aufgerufen:  " + slice.getBeschreibung());
        // Sollten mehrere Viren auf einmal gestoppt werden
    }

    private void loadPlayerBase() {
        if(player1checked) {
            spieler.add("Lukas");
        }
        if(player2checked) {
            spieler.add("Patrick");
        }
        if(player3checked) {
            spieler.add("Robin");
        }
        if(player4checked) {
            spieler.add("Alfred");
        }
        if(player5checked) {
            spieler.add("Felix");
        }
        if(player6checked) {
            spieler.add("Daddl");
        }
        if(player7checked) {
            spieler.add("Leonie");
        }
        if(player8checked) {
            spieler.add("Luisa");
        }

        // Fehlen noch die zusätzlichen Spieler!
        EditText editText = findViewById(R.id.editText);

        if(editText.getText().toString().equals(R.string.editTexttext)) {
            // Nichts wurde hinzugefügt
             Log.d("debug/players", "Wurde kein weiter hinzugefügt");
            return;

        }
        else {
            String[] additionalPlayers = editText.getText().toString().trim().split(",");
            Pattern pattern = Pattern.compile("[A-Z][a-z]*");

            for(int i = 0; i<additionalPlayers.length; i++) {
                String currentPlayer = additionalPlayers[i].trim();

                // Wenn das Ergebnis der Regex mached, dann füge zu den Spielern hinzu. Sonst gehe weiter im Array
                if(pattern.matcher(currentPlayer).matches()) {

                    Log.d("debug/Player", "Spieler hinzugefügt: " + currentPlayer);
                    spieler.add(currentPlayer);
                }
                else {
                    Log.d("debug/Player", "Spieler nicht hinzugefügt: " + currentPlayer);
                    continue;
                }
            }
        }
    }



    // Startet die App neu
    private void restartApplication(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.d("debug/restart", "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.d("debug/restart", "Was not able to restart application, PM null");
                }
            } else {
                Log.d("debug/restart", "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.d("debug/restart", "Was not able to restart application");
        }
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

        momentaneViren = new ArrayList<Virus>(5);

        sliceCount = 0;
        anzahlSlices = 15;

        randomGenerator = new Random();

        textView = findViewById(R.id.textView);

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
