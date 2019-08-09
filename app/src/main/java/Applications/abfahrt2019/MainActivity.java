// Spieler: Keine Namen-Dopplung, wir gehen davon aus, das der Stammtisch spielt, also lade HäkchenBilder zuerst ! <- Fehlt noch, Anfangsbilder noch setzen
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

    // // Attributes
    // Mengen für die Fragen / Spiele / Viren
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

    // Speziell für andauernde Viren
    private List<Virus> momentaneViren;

    // Extra-Tools
    private ConstraintLayout parentLayout;
    private Random randomGenerator;
    private Slice endSlice;
    private TextView textView;

    // Parameter zur Spielbalance
    private double sliceCount;
    private double anzahlSlices;
    private int virusDauerIntervall;
    private int virusDauerOffset;

    // Conditionals
    private boolean finished;
    private boolean started;
    private boolean restart;
    private boolean player1checked = true;
    private boolean player2checked = true;
    private boolean player3checked = true;
    private boolean player4checked = true;
    private boolean player5checked = true;
    private boolean player6checked = true;
    private boolean player7checked = true;
    private boolean player8checked = true;


    // Text-Halter
    private String[] fragenNormalArray =  {
            "Alle, die schon einmal an einem öffentlichen Ort Sex hatten, trinken 3 Schlucke.",
            "Die letzte Person, die ihre Füße vom Boden hebt trinkt 2 Schlucke.",
            "§ leckt die Nase von & oder trinkt 2 Schlucke!",
            "Alle, die schon einmal an einem öffentlichen Ort Sex hatten, trinken 3 Schlucke.",
            "§ leckt die Nase von & oder trinkt 2 Schlucke!",
            "Alle geben nacheinander neuen Alkohol ins Glas. § beginnt.",
            "§, du musst entscheiden: Wer von & und % glaubt deiner Meinung nach am ehesten heute Abend jemanden abzuschleppen? Diese Person trinkt 2 Schlucke.",
            "§, Trink so viele Schlucke, wie du verlassen wurdest und auf depressiver Basis noch einen.",
            "§, Verteil 4 Schlucke, wenn du schon einmal mit & Unterricht hattest.",
            "Die letzte Person, die ihre Füße vom Bodem hebt trinkt 2 Schlucke.",
            "§, Wenn du beweisen kannst, dass Rothaarige (ja Robin) eine Seele haben, verteile 3 Schlucke. Ansonsten trink sie selbst.",
            "§, Mach so viele Liegestütze, wie du Schlucke verteilen möchtest! Bei den Jungs entspricht jeder 10te einem Schluck, bei den Mädchen jeder 3te. Es sind maximal 8 Schlucke möglich.",
            "§, Erfülle die Aufgabe, die dir & gibt. Wenn du dich weigerst, trink 5 Schlucke.",
            "Alle Hände in die Höhe! Die letze Person trinkt 2 Schlucke.",
            "§ verteilt so viele Schlucke wie viele schwarze Kleidungsstücke er/sie an hat.",
            "§ tut uns den Gefallen und trinkt sein/ihr Glas aus!",
            "§, Wann hattest du das letzte mal Sex? War es noch in den letzen 7 Tagen/2 Wochen trinke 2 bzw. 3 Schlucke. War es doch länger, ist das schon genug Bestrafung.",
            "§, Trink für jeden Sexpartner den du hattest einen Schluck.",
            "§, Trink einen Schluck, wenn du noch nie in einem anderen Haus, als deinem, Sex hattest.",
            "§, Trink einen Schluck, wenn du schon einmal Sex im Zimmer deiner Eltern hattest.",
            "§, Trink einen Schluck, wenn du dich noch nie geprügelt hast.",
            "§, Trink einen Schluck, wenn du noch nie in eine Person in deiner Schule verliebt warst.",
            "§, Trink einen Schluck, wenn du schon einmal beim Sex an eine andere Person gedacht hast.",
            "§, Trink einen Schluck, wenn du noch nie mit einer Person des anderen Geschlechts in der Dusche warst.",
            "§, Erzähl uns eine Wahre und eine gelogene Geschichte. Jeder der auf dich reinfällt, trinkt 2 Schlucke!",
            "§, Erzähl uns von deinem Schlechtesten Date! Wer es toppen kann, darf 3 Schlucke verteilen, sonst verteilst du zwei Stück!",
            "§, Imitiere ein selbstgewähltes Tier! Kommt es authentisch rüber, verteile 2 Schlucke, ansonsten trink sie selbst; deine Mitspieler entscheiden!",
            "§, Finde heraus, wessen Haare aus der Runde am Besten Duften! Lass diese Person 2 Schlucke verteilen.",
            "§, Wer wäre beim Eintreten plötzlicher Blindheit der Blindenhund deines Vertrauens aus dieser Runde? Sowohl § als auch der Blindenhund verteilen jeweils !auf Ehre! 2 Schlucke!",
            "§, Spiele uns eine Geburt vor! Wenn & überzeugt ist, darfst du 3 Schlucke verteilen!",
            "§, Trage die Person links von dir einmal durch den Raum!",
            "§, Tu so als würdest du dich 1 min lang mit einem Gegenstand streiten! Sind die anderen überzeugt verteile 3 Schlucke, ansonsten trink 2!",
            "Robin, sing uns ein Lied! Die Gruppe entscheidet, wie viel Soul drin war und lässt dich 3 Schlucke trinken / verteilen!"

    };
    private String[] spieleNormalArray =  {
            "§ und & spielen Piss-Pott: Gegenüberstehend abwechselnd ein Fuß vor dem anderen stellen, bis ihr euch in der Mitte trefft! Die Person, dessen Fuß nicht mehr reinpasst, trinkt 4 Schlucke.",
            "Lieber Ausschweifungen mit einer Sexnummer haben oder FKK mit Freunden/Freundinnen machen? Stimmt alle gleichzeitig ab, die Verlierer trinken 2 Schlucke.",
            "Jeder nennt nacheinander den Film, den er zuletzt gesehen hat. Wer ihn auch schon gesehen hat, trinkt. § beginnt.",
            "Jeder nennt nacheinander etwas, das er schon immer mal ausprobieren wollte. Wer es schon mal gemacht hat, trinkt. § beginnt.",
            "§ muss sich Lippenstift auftragen und jedem einen Kuss auf die Wange geben.",
            "§ und & geben in WhatsApp den Suchbegriff Blasen/ Sex(y)/ nackt / Orgie / ... ein und lesen die erste Nachricht vor. Wer sich weigert, trinkt 4 Schlucke.",
            "An alle: Mit wem würdet ihr gerne mal schlafen (nur Freundes und Bekanntenkreis)? Schreibt den Namen auf! Die den gleichen Namen aufschreiben trinken brüderlich 3 Schlucke.",
            "An alle: Mit welchem Star würdet ihr gerne einmal schlafen? Die den gleichen Namen aufschreiben trinken 3 Schlucke zusammen.",
            "§ vs &:\nFür einen Tag das andere Geschlecht sein. Was würdet ihr jeweils machen? % entscheidet welche Idee fir beste wäre. Der Gewinner darf 4 Schlucke verteilen",
            "§ vs &:\nWas war dein deprimierendstes Erlebnis im Bett? % entscheidet welches schlimmer war. Der arme Gewinner darf 4 Schlucke verteilen.",
            "§ vs &:\nErzählt von eurem ersten Mal. % entscheidet welches erotischer klingt, der Verlierer darf zum Trost 4 Schlucke verteilen.",
            "Themenspiel:\nDinge, die beim Grillen nicht fehlen dürfen.",
            "Themenspiel:\nMake-up-Marken",
            "Jeder nennt seine Lieblingsfarbe. Alle, die dieselbe haben, trinken!",
            "§, lasse dich von & schminken!",
            "Karaoke!\n§ und & singen zusammen ein Lied ihrer Wahl bis zum Refrain. Wenn nicht, trinkt 5 Schlucke.",
            "§ und & stellen sich nah gegenüber und füllen ihren Mund komplett mit Wasser. % wird nun Flachwitze vorlesen, bis einer Lacht, wodurch dieser Verliert. Der Gewinner erhält 3 Schlucke zum Verteilen und wird vom Verlierer getrocknet.",
            "§, bestimme den Slot-König unter euch und lege / stelle ihm 4 Schlucke zu Füßen, die er dann Verteilen darf!",
            "Lieber eine Nacht mit dem Promi deiner Wahl verbringen oder eine Woche Urlaub auf Mallorca? Stimmt alle gleichzeitig ab, die Verlierer trinken 2 Schlucke.",
            "Lieber nur noch 5 Jahre leben, aber steinreich sein oder lange leben, aber kaum einen Cent besitzen? Stimmt alle gleichzeitig ab, die Verlierer trinken 3 Schlucke.",
            "Letzte Worte vor dem Tod. § fängt an. Wird nichts neues genannt oder etwas wiederholt, trinkt die Person 2 Schlucke.",
            "Dinge, die man unter deinem/einem Bett finden kann. § fängt an. Wird nichts neues genannt oder etwas wiederholt, trinkt die Person 2 Schlucke.",
            "Welche Sexspielzeuge kennt ihr? § fängt an. Wird nichts neues genannt oder etwas wiederholt, trinkt die Person 2 Schlucke."

    };
    private String[] virusNormalArray =  {
            "§ und & sind das neue Dreamteam, wenn § trinkt, trinkt & mit. > § und &, ihr seid doch kein Dreamteam und dürft wieder alleine trinken.",
            "§ muss nun mit einem französischen Akzent sprechen. > § darf nun wieder wie ein Mensch sprechen. (Scheiß Franzosen XD)",
            "§ und & müssen Händchen halten. > § und &, ihr dürft euch wieder loslassen.",
			"§ ist Amor. Du kannst zu jeder Zeit 2 Spieler auswählen, die sich wie ein Paar benehmen müssen. > § ist nicht mehr Armor und alle dürfen sich wieder normal verhalten.",
            "§ ist nun ein Papagei und plappert Alles nach was & sagt. > §, höre auf alles nachzuplappern, das nervt! ",
            "§ darf eine neue Regel einführen. > Die Regel von § gilt nun nicht mehr.",
            "§ ist Questionmaster. Wer ihm/ihr eine Frage beantwortet, muss trinken. > § ist kein Questionmaster mehr.",
			"§ muss jetzt auf jede Frage verneinen und sich jedes Mal einen Grund dafür ausdenken. 1 Schluck für jeden Fehler. > § braucht nicht mehr alle Fragen zu verneinen.",
            "§, &, und %, ihr seid die Babos. Jedes Mal, wenn einer von euch sein Glas leert, trinken alle Spieler. > Die Babos sind nun wieder Lappen und es muss nicht mehr getrunken werden, wenn ihr euer Glas geleert habt.",
            "§ ist jetzt Fingerlos und darf nicht mehr seine/ihre Finger nutzen! > § darf wieder seine/ihre Finger benutzen!",
            "§ ist ein Zoo. Nach jedem Schluck macht er/sie ein Tiergeräusch! > § darf wieder normal trinken.",
			"§ sucht für Jeden einen Kosenamen aus.> Alle Spieler erhalten ihre Original-Namen zurück!",
            "Sämtliche Vornamen rutschen um 2 Plätze nach rechts. > Alle Vornamen rücken wieder an ihre ursprünglichen Plätze!",
            "Ab sofort macht jeder Spieler seinem Gegenüber den Nächsten Drink! > Ihr macht wieder eure eigenen Drinks, nachdem ihr min. Einen Drink, der nicht von eurer Hand geschaffen wurde, erhalten habt.",
			"§ muss nun in ihren Satz \"Ja Schatz\" einbauen, wenn sie/er von einem Mädchen angesprochen wird. Bei einem Jungen soll es \"Ja Hase\" sein. Jeder Fail erzeugt einen Schluck. > § darf nun die Alibi-Ehe auflösen und auch wieder \"Nein\" benutzen ^^",
            "§ darf ab sofort nicht mehr die Hände zum Trinken benutzen! Sei kreativ :P > § darf wieder normal trinken.",
            "An die Gruppe: Schminkt § mit Lebensmitteln! > §, du darfst die Lebensmittel wieder zurück schaffen!"
};
    private String[] fragenWarmArray = {
            "§ zieht & ein Kleidungsstück aus oder trinkt 4 Schlucke." ,
            "§, Entscheide wer ein Kleidungsstück ablegt und welches.",
            "Alle Mitspieler, die keine Brille tragen, ziehen ihre Oberteile aus.",
            "Alle Mitspieler, die eine Brille tragen, ziehen ihre Oberteile aus.",
            "Alle trinken und ziehen ein Kleidungsstück aus!",
            "Alle Kerle ziehen ein Kleidungsstück aus!",
            "Alle Mädels ziehen ein Kleidunsstück aus!",
            "§ zieht & ein Kleidungsstück aus oder trinkt 4 Schlucke.",
            "§, Küsse & oder trinke 2 Schlucke",
            "§ wählt 2 Personen und eine Stellung aus, die die beiden auf Trocken vormachen oder 4 Schlucke trinken müssen.",
            "Wenn sich § und & küssen (mündlich), verteilen beide jeweils 3 Schlucke, ansonsten trinken beide jeweils 3.",
            "§ gibt allen Spielern einen Kuss oder trinkt das Glas seiner/ihrer Wahl aus.",
            "§, Nenne die Person mit der du am liebsten Sex hättest und trink einen Schluck mit dieser.",
            "§, Deute eine sexuelle Stellung mit einem anderen Spieler an oder trink 3 Schlucke.",
            "§, Zeig deinen Oberkörper oder trink 2 Schlucke.",
            "§, Zeig deinen Hintern oder trink 3 Schlucke.",
            "Alle trinken 3 Schlucke. Außer §, du gibst allen einen Kuss.",
            "§ und & kuscheln miteinander.",
            "§ erspart uns den Anblick und muss sich wieder anziehen!",
            "Alle Kerle machen sich obenrum frei und trinken 2 Schlucke.",
            "§, Wähle eine Person, die eine Stelle deines Körpers abschlabbern muss. 3 Schlucke, wenn er/sie sich weigert.",
            "§ muss & ein Arschgeweih verpassen! Weigert sich jemand, 4 Schlucke für den Betroffenen.",
            "§ muss bei & einen Lapdance machen. Zieht § den Schwanz ein (oder die Möse), dann werden 4 Schlucke getrunken.",
            "§ lässt seiner Fantasie freien Lauf und zählt 5 Pornonamen auf. Jeder Lacher/Schmunzler sorgt für einen Schluck!",
            "§ verteilt nun eine innige Umarmung an einen Spieler deiner Wahl. Solltest du Scheitern, trinkst du 4 Schlucke.",
            "Arsch, Titten, Gesicht, jetzt kommts drauf an: Worauf achtet ihr am Meisten bei einer Frau? Jeder wählt und die Verlierer trinken 3 Schlucke!",
            "§, Wenn du auf den Strich gehen würdest – was wäre deine Spezialität? Jede Person, von der du denkst, dass sie dich dort aufsuchen würde, trinkt 3 Schlucke!",
            "Alle Redheads dürfen einem Spieler ihrer Wahl ein Arschgeweih verpassen. ~ Cause Karma always catches up to you.",
            "§, Lasse dich von & und % Würzen!",
            "§, Preise dein Geschlechtsteil wie auf einem ShoppingKanal an!",
            "§, Tausche mit & dein T-Shirt! Nein, es gibt kein Zurück!",
            "§, Spiele das Letzte Lied auf deinem Handy, dass du gehört hast und gehe dazu Ab!",
            "§, Erzähle uns vom Schlimmsten Korb, den du je bekommen hast!",
            "§, Hast du schon mal Doktorspiele gespielt? Wenn ja, verteile 3 Schlucke, sonst trinke 2!",
            "§, Erzähl uns die letze Lüge, die du der Person rechts von dir angedreht hast! Weigerst du dich, 5 Schlucke!",
            "§, Gib einem Mitspieler deiner Wahl einen Klaps auf den Po!",
            "§, Liefere & einen richtig Dummen Anmachspruch!",
            "Robin, Wenn du schon einmal mit einer Person im Raum Sex hattest, trink 5 Schlucke!"

};
    private String[] spieleWarmArray =  {
            "Boxershorts-Contest! Die Jungs zeigen alle ihre Boxershorts. Die Mädels bilden die Jury und wählen die Schönste. Der Sieger verteilt 5 Schlucke.",
            "String-Contest! Die Mädels zeigen alle ihren String. Die Jungs bilden die Jury und wählen den Schönsten. Die Siegerin verteilt 5 Schlucke.",
            "§, lass dir die Augen zubinden. Ein/e Spieler/in küsst dich irgendwo. Wenn du die Person errätst, verteilst du 5 Schlucke.",
            "Eine Runde Kiss-Party! Jeder küsst eine Person der Wahl. Wer sich weigert trinkt 3 Schlucke. § beginnt!",
            "§, Welche ist deine Lieblingsstellung beim Sex? Zeige diese mit & oder trinkt beide 3 Schlucke.",
			"§ und &: Macht zusammen ein anzügliches Foto und zeigt es den anderen.",
            "Alle Jungs tanzen eine Runde zu \"I will walk 500 Miles\", klatschen sich danach ab und trinken zusammen einen Shot!\nEhre Jungs!",
            "Jeder sucht sich die passende Zahl aus, wie oft er in der Woche Sex haben möchte. Wessen Zahl alleine steht, der trinkt einen Schluck; Wer einen / mehrere Partner findet, verteilt 2 Schlucke für den guten Geschmack!",
            "Spielt die FBI-OPEN-UP Szene nach! Dazu begeben sich 2-3 Leute in einen Raum und schließen die Tür, der Rest weiß was zu tun ist.",
            "§, Denke dir eine Sexstellung mit Allen Mitspielern aus und stellt sie dar!",
            "Fitness Style: § muss 20 Liegestütze/Kniebeuge machen! Für jeden nicht-geschafften trinkst du einen Schluck!",
            "Alle nennen Körperteile, die sie Anturnen! Der Verlierer erhält 3 Schlucke!",
            "Alle nennen Animes! Der Verlierer trinkt 3 Schlucke!",
            "Alle zeigen ihr Können bei einem Catwalk! Die Mädels leiten das Ganze an!"
};
    private String[] virusWarmArray =  {
            "Loch-Spiel! Wann immer ihr wollt, formt einen Kreis mit Daumen und Zeigefinger zwischen Hüfte und Schulter. Jeder der reinguckt, zieht ein Kleidungsstück aus oder trinkt 4 Schlucke. > Das Loch-Spiel ist Vorbei!",
            "§ braucht Liebe. Er/Sie muss jedes mal vorm Trinken jemanden Küssen, egal wo! > § braucht keine Küsse mehr!",
            "Rülps-Spiel! Einer rülpst, alle sagen Cedric. Der Letzte ist der Verlierer und muss ein Kleidungsstück ausziehen oder trinkt 3 Schlucke. > Das Rülps-Spiel ist vorbei!",
            "Ihr könnt jetzt mit Flaschendrehen eure Schlucke neu spielen! Dreht die Flasche und küsst die Person, auf die die Flasche zeigt (und trinkt nicht). Weigert sich die Person, wird die Anzahl um einen Schluck erhöht. > Flaschendrehen ist vorbei. ihr müsst wieder eure nromalen Schlucke trinken.",
            "Wer noch keinen Sex hatte, spielt ab sofort Kellner , bedient und mischt für die anderen Mitspieler! > Die Kellner sind jetzt wieder von ihrer Aufgabe entlassen.",
            "§, Bestimme eine Aktion, die & jedes mal, wenn % eine bestimme Handlung ausführt, machen muss! > &, Du musst nicht mehr auf die Handlungen von % reagieren.",
            "§, Baue nun in jeden deiner Sätze \"Im Bett\" ein! > §, Du darfst ab sofort auch wieder Sachen außerhalb des Betts tun ^^",
            "Ab sofort sprecht ihr Alle nur noch Englisch! > Ihr dürft wieder in die Deutsche Sprache übergehen.",
            "§, Preise dich & mit deinen Diensten als Slave an, die du aber auch Leisten können musst! Ist & unzufrieden, trinke 3 Schlucke! > § ist nun kein Sklave mehr."
    };
    private String[] fragenHeissArray = {
            "§, Wähle eine Person, die die Hand an deine Unterwäsche legen soll. 4 Schlucke, wenn er/sie sich weigert.",
            "§ gibt & einen Kuss auf den nackten Hintern oder trinkt 4 Schlucke.",
            "§, Hol dir eine Banane und mach uns den perfekten Blowjob vor! Die Mitspieler stimmen über die Qualität ab und lassen dich 3 Schlucke trinken / verteilen. Beißt du die Banane währenddessen ab folgen 7 StrafSchlucke!",
            "§, Lass dir von & den Intimbereich streicheln. Wenn nicht, trinkt ihr beide jeweils 4 Schlucke.",
            "§, Lass dich von dem Spieler deiner Wahl auf der Brust küssen. 4 Schlucke, wenn er/sie sich weigert.",
            "§, Trink dein Glas aus und küss die Person, die dich am Meisten anturnt.",
            "§, Du erhälst nun 20 Sekunden Zeit, ungestört einem Spieler deiner Wahl etwas ins Ohr zu Flüstern. Turnt es ihn/sie an, trinkt dein Ziel 4 Schlucke auf ehrlicher Basis, ansonsten übernimmst du sie!",
            "§, Setze dich einer Person deiner Wahl auf den Schoß solange du willst – oder setze sie auf deinen Schoß und spiele einen Orgasmus vor. Fürs bessere Aushalten erhält die gewählte Person einen Schluck.",
			"§, Schnapp dir einen Spieler deiner Wahl und preise deine Lieblingssexstellung an, indem du sie kommentierst und darstellst!",
            "Teamwork: § zieht nun ein Kleidungsstück aus. & hat jetzt die Wahl: Wird das Gleiche Ausgezogen, ziehen auch alle anderen das aus! Ansonsten ziehen § und & noch das jeweils Andere aus.",
            "§, Welche der hier anwesenden Personen würdest du dir für einen Dreier aussuchen? Der Dreier trinkt 3 Schlucke!",
            "§, Bringe einem Mitspieler deiner Wahl die Rote Hand Gottes auf den Arsch oder trink 3 Schlucke!",
            "§, Zieh deine Badesachen an und lasse dir von einem Mitspieler des anderen Geschlechts den Rücken eincremen!",
            "§, Ziehe einem Mitspieler deiner Wahl möglichst Erotisch das T-Shirt aus!"
    };
    private String[] spieleHeissArray =  {
            "Nacheinander wählt jeder eine Person, die ein Kleidungsstück ausziehen muss. Weigert sie sich, trinkt sie 4 Schlucke. § beginnt.",
            "Wettbewerb des schönsten Hinterns: Die Kerle machen die Show, die Mädels bilden die Jury. Der Gewinner verteilt 5 Schlucke.",
            "Pimmel/Muschi-Alarm! Einmal im Kreis berührt jeder die Genitalien seines rechten/linken (random zugewiesen) Nachbarn, wer sich weigert trinkt 3 Schlucke, § beginnt.",
            "Pimmel/Muschi-Alarm! Einmal im Kreis streichelt jeder die Brust seines rechten/linken (random zugewiesen) Nachbarn, wer sich weigert trinkt 3 Schlucke, § beginnt.",
			"Wettbewerb des schönsten Hinterns: Die Mädels machen die Show, die Kerle bilden die Jury. Die Gewinnerin verteilt 5 Schlucke.",
            "§ und & Schnappen sich etwas Essbares und stellen die Susi-und-Strolch Szene nach! Brecht ihr ab, 3 Schlucke für jeden.",
            "§, Lasse dir die Augen verbinden. Deine Mitspieler lassen dich etwas vom Körper von & essen und du musst es erraten! Schaffst du es, 5 Schlucke zum Verteilen, sonst trinke 3!",
            "§, Lasse dir die Augen verbinden und dich im Kreis drehen! Den Ersten Spieler, den du berührst, musst du an eben diese Stelle küssen! Versagst du, 3 Schlucke!",
            "§, Verteile so viele Knutschflecke wie du möchtest, mindestens aber 2 Stück!",
            "§ zieht jetzt über jede Hand ein Kondom und behält diese bis zum Spielende dran oder trinkt 5 Schlucke! Wenn es reißt, 10 Strafschlucke!"
    };
    private String[] virusHeissArray =  {
            "§ füttert % nun mit Snacks! Für jeden einzelnen muss sich % mit \"Danke Daddy!\" (bei Mädchen \"Merci Mommy\" bedanken! -> § hat jetzt genug von den Snacks!"
    };
    private String[] manyInteractsArray = {
            "§, % und & treten gegen `, # und - an. Der Rest bildet die Jury. Jeder erfindet eine Sexposition und gibt ihr einen Namen. Das kreativste Team gewinnt. Die Verlierer trinken 3 Schlucke."
    };


    // Steuert den Verlauf des Spiels, wird durch den StartButton ausgelöst
    private void Abfahren() {

        // Bereinige zuerst Oberfläche
        hideSystemUI();
        clearButton();

        // Initialisiere Sets usw
        Initialize();
        loadPlayerBase();

        // Ersatz bis Spielerwahl hinhaut
        //testFill();

        for(String s : spieler) {
            Log.d("debug/Spieler", "Spieler drin: " + s);
        }
        // Passe alle Fragen auf die momentanen Spieler an
        Interact();

        // Benutze SpezialMethode, falls genug Spieler da sind.
        if(spieler.size() >= 6) {
            InteractLarge();
        }

        // Starte das Spiel mit der ersten Folie. Dadurch umgehen wir ein zwingendes Touch-Event auf dem Weißen Untergrund.
        nextSlice();
        started = true;
    }

    // UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Müssen wir hier initialiseren, da wir sonst Null-Object Probleme bekommen
        parentLayout = findViewById(R.id.parentLayout);

        // Der StartButton beginnt unser Spiel mit den momentanen Spielern.
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view ->  {
            // Hier kommt der Aufruf unserer Anfangsmethode rein
            Abfahren();
        });

        // Erst noch Ausblenden
        TextView textViewMode = findViewById(R.id.textViewMode);
        textViewMode.setVisibility(View.GONE);


        // Wenn das Spiel gestartet wurde, löst ein TouchEvent die nächste Folie aus
        parentLayout.setOnTouchListener( (view, motionEvent) -> {
            if(started) {
                nextSlice();
            }
            // Log.d("debug/touch", "Touched");
            return false;
        });


        // ImageButtons
        // Noch Anfangsbilder setzen







        // MusterBeispiel für spieler1. Durch das Anklicken des Bildes ersetzen wir es mit einer Häkchen-Version usw. -> Lukas
        ImageButton spieler1 = findViewById(R.id.spieler1Button);
        spieler1.setImageResource(R.drawable.lukas_checked);
        spieler1.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player1checked = !player1checked;
            if(player1checked) {
                // Lade Bild mit Häkchen
                spieler1.setImageResource(R.drawable.lukas_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler1.setImageResource(R.drawable.lukas_unchecked);
            }

            Log.d("Debug/players", "Player1 check: " + player1checked);
        });

        // Spieler2 -> Patrick
        ImageButton spieler2 = findViewById(R.id.spieler2Button);
        spieler2.setImageResource(R.drawable.patrick_checked);
        spieler2.setOnClickListener(v -> {


            // Vertausche Check-Zustand
            player2checked = !player2checked;
            if(player2checked) {
                // Lade Bild mit Häkchen
                spieler2.setImageResource(R.drawable.patrick_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler2.setImageResource(R.drawable.patrick_unchecked);
            }

            Log.d("Debug/players", "Player2 check: " + player2checked);
        });

        // Spieler3 -> Robin
        ImageButton spieler3 = findViewById(R.id.spieler3Button);
        spieler3.setImageResource(R.drawable.robin_checked);
        spieler3.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player3checked = !player3checked;
            if(player3checked) {
                // Lade Bild mit Häkchen
                spieler3.setImageResource(R.drawable.robin_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler3.setImageResource(R.drawable.robin_unchecked);
            }

            Log.d("Debug/players", "Player3 check: " + player3checked);
        });

        // Spieler4 -> Alfred
        ImageButton spieler4 = findViewById(R.id.spieler4Button);
        spieler4.setImageResource(R.drawable.alfred_checked);
        spieler4.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player4checked = !player4checked;
            if(player4checked) {
                // Lade Bild mit Häkchen
                spieler4.setImageResource(R.drawable.alfred_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler4.setImageResource(R.drawable.alfred_unchecked);
            }


            Log.d("Debug/players", "Player4 check: " + player4checked);
        });

        // Spieler5 -> Daddl
        ImageButton spieler5 = findViewById(R.id.spieler5Button);
        spieler5.setImageResource(R.drawable.daddl_checked);
        spieler5.setOnClickListener(v -> {


            // Vertausche Check-Zustand
            player5checked = !player5checked;
            if(player5checked) {
                // Lade Bild mit Häkchen
                spieler5.setImageResource(R.drawable.daddl_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler5.setImageResource(R.drawable.daddl_unchecked);
            }

            Log.d("Debug/players", "Player5 check: " + player5checked);
        });

        // Spieler6 -> Leonie
        ImageButton spieler6 = findViewById(R.id.spieler6Button);
        spieler6.setImageResource(R.drawable.leonie_checked);
        spieler6.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player6checked = !player6checked;
            if(player6checked) {
                // Lade Bild ohne Häkchen
                spieler6.setImageResource(R.drawable.leonie_checked);
            }
            else {
                // Lade Bild mit Häkchen
                spieler6.setImageResource(R.drawable.leonie_unchecked);
            }


            Log.d("Debug/players", "Player6 check: " + player6checked);
        });

        // Spieler7 -> Luisa
        ImageButton spieler7 = findViewById(R.id.spieler7Button);
        spieler7.setImageResource(R.drawable.luisa_checked);
        spieler7.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player7checked = !player7checked;
            if(player7checked) {
                // Lade Bild mit Häkchen
                spieler7.setImageResource(R.drawable.luisa_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler7.setImageResource(R.drawable.luisa_unchecked);
            }


            Log.d("Debug/players", "Player7 check: " + player7checked);
        });

        // Spieler8 -> Felix
        ImageButton spieler8 = findViewById(R.id.spieler8Button);
        spieler8.setImageResource(R.drawable.felix_checked);
        spieler8.setOnClickListener(v -> {

            // Vertausche Check-Zustand
            player8checked = !player8checked;
            if(player8checked) {
                // Lade Bild mit Häkchen
                spieler8.setImageResource(R.drawable.felix_checked);
            }
            else {
                // Lade Bild ohne Häkchen
                spieler8.setImageResource(R.drawable.felix_unchecked);
            }

            Log.d("Debug/players", "Player8 check: " + player8checked);
        });
    }

    // Versteckt Systemleisten
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

    // Entfernt alle Buttons vom Anfang für einen sauberen Spielverlauf
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
        // Fallunterscheidung anhand Normal/Warm/Heiss und Frage/Spiel/Virus. Je nachdem wir ein anderer Farbübergang verwendet.
        // Beim Virus ist es etwas spezieller: Wir brauchen eine zufällige Laufzeit des Virus, benennen ihn Autistischer Kick und legen den Hinteren Teil in die momentaneViren - Liste

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

            // Zufällige Rundenzahl
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String(strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
            // Log.d("debug/SecondVirs", "Virus added");
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

            // Zufällige Rundenzahl
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String(strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
            // Log.d("debug/SecondVirs", "Virus added");
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

            // Zufällige Rundenzahl
            int randomInt = randomGenerator.nextInt(virusDauerIntervall) + virusDauerOffset;

            // Virus per Trennsymbol splitten
            String[] strings = slice.getBeschreibung().split(">");

            // Ersten String bauen
            String autistischerKick = new String(strings[0]+ "\nGilt " + randomInt + " Runden!");
            // Veränderten Text setzen
            slice.setBeschreibung(autistischerKick);

            // Übernehme verhalten der ursprünglichen Slice
            Slice newSlice = new Slice(strings[1], slice.getKategorie(), slice.getInteraktiv(), slice.getTyp());
            // Add to momentaneViren
            momentaneViren.add(new Virus(newSlice, randomInt));
            // Log.d("debug/SecondVirs", "Virus added");
        }

        // Lul ModusAnsage
        TextView textViewMode = findViewById(R.id.textViewMode);

        // Fonts
        // Je nach Typ verwenden wir eine andere Schriftart
        if(slice.getTyp().equals(Slice.Type.Frage)) {
            // Frage
            Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/frage.ttf");

            textView.setTypeface(typeface);
            textViewMode.setTypeface(typeface);

            textViewMode.setText(R.string.FragenModus);
        }
        else if(slice.getTyp().equals(Slice.Type.Spiel)) {
            // Spiel
            Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/spiel.ttf");

            textView.setTypeface(typeface);
            textViewMode.setTypeface(typeface);

            textViewMode.setText(R.string.SpielModus);
        }
        else {
            // virus
            Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/virus.ttf");

            textView.setTypeface(typeface);
            textViewMode.setTypeface(typeface);

            textViewMode.setText(R.string.VirusModus);
        }

        // TextView auf momentane Slice setzen
        String text = slice.getBeschreibung();
        textView.setText(text);
    }
  
    // HelperMethods
    // Soll nun eine neue Slice wählen, schaut zuerst ob neu gestartet werden sollte.
    private void nextSlice() {
        Log.d("debug/Finsih", "SliceCount: " + sliceCount + ", finished: " + finished);

        // Restart?
        if(restart) {
            restartApplication(this);
        }

        // Behandle zuerst Viren
        boolean virusFirst = false;
        if(!momentaneViren.isEmpty() && !finished) {
            Virus destroyVirus = null;
            for(Virus virus : momentaneViren) {
                if(virus.getTimer() <= 0) {
                    virusFirst = true;
                     Log.d("debug/Virus", virus.toString());
                    handleSecondVirusPart(virus.getSlice());
                    destroyVirus = virus;
                }
                else {
                    // Log.d("debug/Viren", "Momentaner Virus wird jetzt um eins verringert: " + virus);
                    virus.setTimer(virus.getTimer() - 1);
                }
            }

            // Bereinigt nach und nach die momentaneViren-Liste, immer nur einen um die ConcurrentModifikationException zu umgehen
            if(destroyVirus != null) {
                momentaneViren.remove(destroyVirus);
                destroyVirus = null;
            }
        }


        // Normaler Spiel-Flow, kein Virus wurde aufgehoben
        if(!finished && !virusFirst) {
            ++sliceCount;
            // Momentaner SpielFortschritt
            double factor = sliceCount / anzahlSlices;
            // Versuche currentSlice zufällig zu wählen. Wähle erneut, falls diesmal getroffene Menge schon leer ist.
            Slice currentSlice = null;
            while(currentSlice == null) {
                int temp = randomGenerator.nextInt(99) + 1; // Werte zwischen 1 und 100
                // Log.d("Debug/Slice", "RandomZahl: "+ temp + " Faktor: " + factor + " SliceCount: " + sliceCount + " anzahlMaxSlices: " + anzahlSlices);
                // 0%-30% des Games
                if (factor <= 0.3) {

                    if (temp <= 35) {

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 45) {

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } /*else if (temp <= 45) {

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } */else if (temp <= 70) {

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 80) {

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } /*else if (temp <= 77) {

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } */else if (temp <= 90) {

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 100) {

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {

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

                    if (temp <= 10) {

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 30) {

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } else if (temp <= 40) {

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } else if (temp <= 50) {

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 70) {

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } else if (temp <= 80) {

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } else if (temp <= 85) {

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 95) {

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {

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
                    if (temp <= 2) {

                        if (fragenNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenNormal);
                        }
                    } else if (temp <= 16) {

                        if (fragenWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenWarm);
                        }
                    } else if (temp <= 46) {

                        if (fragenHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(fragenHeiss);
                        }
                    } else if (temp <= 48) {

                        if (spieleNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleNormal);
                        }
                    } else if (temp <= 58) {

                        if (spieleWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleWarm);
                        }
                    } else if (temp <= 78) {

                        if (spieleHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(spieleHeiss);
                        }
                    } else if (temp <= 80) {

                        if (virusNormal.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusNormal);
                        }
                    } else if (temp <= 90) {

                        if (virusWarm.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusWarm);
                        }
                    } else {

                        if (virusHeiss.isEmpty()) {
                            continue;
                        } else {
                            currentSlice = pickRandomSlice(virusHeiss);
                        }
                    }
                }
            }

            // Gewählte Slice wartet nun in currentSlice
            handleSlice(currentSlice);

            // Sind wir jetzt fertig?
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

        // Setze zuerst dem Level entsprechenden Hintergrund
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
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/virus.ttf");
        textView.setTypeface(typeface);
        textView.setText(slice.getBeschreibung());

        TextView textView2 = findViewById(R.id.textViewMode);
        textView2.setText(R.string.VirusModus);
        textView2.setTypeface(typeface);
    }

    private void loadPlayerBase() {

        Log.d("Debug/Players", "Player1Check: " + player1checked +"Player2Check: " + player2checked +"Player3Check: " + player3checked +"Player4Check: " + player4checked +"Player5Check: " + player5checked +"Player6Check: " + player6checked +"Player7Check: " + player7checked +"Player8Check: " + player8checked);
        // Füge StammSpieler anhand des momentanen Bildes hinzu.
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
            spieler.add("Daddl");
        }
        if(player6checked) {
            spieler.add("Leonie");
        }
        if(player7checked) {
            spieler.add("Luisa");
        }
        if(player8checked) {
            spieler.add("Felix");
        }

        // Lade zusätzliche Spieler aus dem Edit-Text, trenne per Komma
        EditText editText = findViewById(R.id.editText);
        String[] additionalPlayers = editText.getText().toString().trim().split(",");
        Pattern pattern = Pattern.compile("[A-Z][a-z]*");

        for(int i = 0; i<additionalPlayers.length; i++) {
            String currentPlayer = additionalPlayers[i].trim();

            // Wenn das Ergebnis der Regex matched, dann füge zu den Spielern hinzu. Sonst gehe weiter im Array
            if (pattern.matcher(currentPlayer).matches()) {

                // Log.d("debug/Player", "Spieler hinzugefügt: " + currentPlayer);
                spieler.add(currentPlayer);
            }
            else {
                    // Log.d("debug/Player", "Spieler nicht hinzugefügt: " + currentPlayer);
                    continue;
            }
        }
    }



    // Startet die App neu, nur copypasted
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

    // Füllt erstmal Namen rein, solange Spielerauswahl noch nicht vollständig ist
    private void testFill() {
        spieler.add("Lukas");
    }

    // Sucht eine zufällige Slice aus dem übergebenen Set aus
    private Slice pickRandomSlice(HashSet<Slice> set) {
        int i = 0;
        Slice finalSlice = new Slice("Something failed in PickRandomSlice", Slice.Level.Heiss, false, Slice.Type.Frage);
        int item = randomGenerator.nextInt(set.size());
        for(Slice s : set) {
            if(item == i) {
                // Wirf Slice aus der Menge und gib sie zurück
                set.remove(s);
                return s;
            }
            else {
                i++;
            }
        }
        return finalSlice;
    }

    // Spezielle Methode für Fragen, die eine Anzahl von <= 6 Spielern fordern
    private void InteractLarge() {
        for(Slice s : manyInteracts) {
            HashSet<Integer> temp = new HashSet<Integer>();
            // Hol dir zuerst die Beschreibung
            String current = s.getBeschreibung();

            // Erzeuge 6 einzigartige Zahlen, indem du so lange Zahlen wählst, bis 6 Stück in temp stehen
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

            // Ersetze nun Sonderzeichen in den Fragen mit den ermittelten SpielerNamen
            String replaced = current.replaceAll("§", Spieler1).replaceAll("&", Spieler2).replaceAll("%", Spieler3).replaceAll("`", Spieler4).replaceAll("#", Spieler5).replaceAll("-", Spieler6);
            s.setBeschreibung(replaced);
        }
    }

    //  Ersetzt Sonderzeichen in den Texten der Slices mit zufälligen Spieler-Namen, Ausgelegt für 3 Spieler pro Frage
    private void Interact() {

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

        Log.d("debug/anzahlSlices", "Anzahl:= " + allSlices.size());
        for(Slice s : allSlices) {

            // Wähle nur die Slices, bei denen auch was ersetzt werden muss
            if(s.getInteraktiv()) {

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

                // Replace die Zeichen mit den ermittelten Spielern
                String replaced = current.replaceAll("§", Spieler1).replaceAll("&", Spieler2).replaceAll("%", Spieler3);
                s.setBeschreibung(replaced);
            }
            else {
                continue;
            }
        }
    }


    private void Initialize() {

        // SpielParameter festlegen
        sliceCount = 1;
        anzahlSlices = 40; // TotalSlices
        virusDauerIntervall = 7;
        virusDauerOffset = 3;

        // Sets und Listen initialiseren
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

        // Conditionals
        finished = false;
        started = false;
        restart = false;


        // Extra-Tools
        randomGenerator = new Random();
        textView = findViewById(R.id.textView);
        TextView textViewMode = findViewById(R.id.textViewMode);
        textViewMode.setVisibility(View.VISIBLE);
        endSlice = new Slice(getResources().getString(R.string.endText), Slice.Level.Normal, false, Slice.Type.Frage);


        // Jetzt füllen wir die Sets mit den jeweiligen Elementen. Enthält das momentane ein Sonderzeichen §, dann wird es als Interaktiv gekennzeichnet
        // Normale Fragen
        for(int i = 0; i < fragenNormalArray.length ; i++) {
            String current = fragenNormalArray[i];

            // InteraktivitätsCheck
            boolean temp = false;
            if(current.contains("§")) {
                temp = true;
            }

            Slice slice = new Slice(current, Slice.Level.Normal, temp, Slice.Type.Frage);
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
            virusHeiss.add(slice);
        }

        // SpezialSet manyInteracts für Fragen mit besonders hoher SpielerBeteiligung
        for(int i = 0; i < manyInteractsArray.length ; i++) {
            String current = manyInteractsArray[i];

            Slice slice = new Slice(current, Slice.Level.Warm, true, Slice.Type.Frage);
            manyInteracts.add(slice);
        }
    }
}