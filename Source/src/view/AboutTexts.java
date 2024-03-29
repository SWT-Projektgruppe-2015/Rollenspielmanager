package view;

import view.controller.Hauptprogramm;

/*
 * Texte fuer alle Hilfefenster, die mithilfe der MenuBar geoeffnet werden koennen.
 */
public class AboutTexts {    
    public static final String MAIN_MENU_TITLE =  "Das Hauptmen" + Hauptprogramm.UMLAUT_SMALL_UE;
    public static final String ABOUT_MAIN_MENU = 
            "Das Hauptmen" + Hauptprogramm.UMLAUT_SMALL_UE + " gibt einen " + Hauptprogramm.UMLAUT_CAPITAL_UE + "berblick " 
            + Hauptprogramm.UMLAUT_SMALL_UE + "ber die Funktionalit" + Hauptprogramm.UMLAUT_SMALL_AE + "ten des Programms.\n"
            + "Zudem ist an der rechten Seite ein Kurz" + Hauptprogramm.UMLAUT_SMALL_UE + "berblick "
            + Hauptprogramm.UMLAUT_SMALL_UE + "ber die aktuelle Abenteuergruppe, "
            + "bzw. eine Abenteuergruppe kann ausgew" + Hauptprogramm.UMLAUT_SMALL_AE + "hlt werden.\n\n"
            + "Die verschiedenen Funktionalit" + Hauptprogramm.UMLAUT_SMALL_AE + "ten sind:\n\n"
            + "* Zuuma, die Wirtin: Hier k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen Spielercharaktere und Nichtspielercharaktere angelegt "
                    + "sowie Abenteuergruppen verwaltet werden \n\n"
            + "* Anakok, die Kriegerin: Nach der Wahl von Spielercharakteren und Gegnern werden hier Gegnerw" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe simuliert und ausgewertet,"
                    + " Schadenswerte der Spieler berechnet und Erfahrungspunkte vergeben.\n\n"
            + "* Giakiri, der H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler: Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde, ihre Traglast, Kategorie, Kosten und Beschreibung"
                    + " k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen hier verwaltet und insbesondere gesucht werden.\n\n"
            + "* Ceto, der W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelspieler: W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelw" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe"
                    + " k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen simuliert werden.";
    
    public static final String CHARAKTER_MANAGER_TITLE = "Zuumas Charaktermanager";
    public static final String ABOUT_CHARAKTER_MANAGER = 
            "Dieses Fenster ist zum Verwalten von Gruppen, Spielercharakteren und Nichtspielercharakteren da.\n"
            + "Die verschiedenen Tabs und ihre Bedienung:\n\n"
            + "----------- Gruppen: Verwaltung von Gruppen -----------\n"
            + "1) Zur Erstellung einer neuen Gruppe tippe einen Namen in das Textfeld und dr" + Hauptprogramm.UMLAUT_SMALL_UE + "cke Enter. "
                    + "Alternativ kann die neue Gruppe auch mit dem Button 'Neu' best" + Hauptprogramm.UMLAUT_SMALL_AE + "tigt werden.\n"
            + "2) Zur " + Hauptprogramm.UMLAUT_CAPITAL_AE + "nderung des Namens einer bestehenden Gruppe "
                    + "w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle sie in der Dropdownbox aus und modifiziere den Namen in der Textbox. "
                    + "Best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit dem Button 'Update'.\n"
            + "3) Um die Zuweisung von Nichtspielercharakteren zu Gruppen umzu" + Hauptprogramm.UMLAUT_SMALL_AE + "ndern, selektiere die Gruppe. "
                    + "Nun k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen Charaktere durch Klicken der Pfeile in die Gruppe hinein oder hinaus bewegt werden.\n"
            + "4) Um eine Gruppe zu l" + Hauptprogramm.UMLAUT_SMALL_OE + "schen, selektiere sie und "
                    + "w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den Button 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen'."
            + "\n\n"
            + "----------- Spielercharaktere: Verwaltung von Spielercharakteren -----------\n"
            + "Beide Tabs zu Charakteren haben folgende Bedienung:\n"
            + "1) Zur Erstellung eines neuen Charakters w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den Eintrag 'Neuer Spieler' bzw. 'Neuer Gegner'. "
                    + "Modifiziere die Daten und best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit Enter.\n"
            + "2) Zur " + Hauptprogramm.UMLAUT_CAPITAL_AE + "nderung eines bestehenden Charakters w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den "
                    + "entsprechenden Charakter aus, modifiziere die Daten und best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit Enter.\n"
            + "3) Zum L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen eines Charakters w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den "
                    + "entsprechenden Charakter aus und klicke auf den Button 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen'.\n"
            + "4) Durch Eingaben in das 'Suche' Textfeld werden nur alle Charaktere angezeigt, deren Name den eingegebenen Begriff enth" + Hauptprogramm.UMLAUT_SMALL_AE + "lt."
            + "\n\n"
            + "----------- Nichtspielercharaktere: Verwaltung von Nichtspielercharakteren -----------\n"
            + "F" + Hauptprogramm.UMLAUT_SMALL_UE + "r die generelle Bedienung siehe 'Spielercharaktere'. Besonderheit:\n"
            + "Nichtspielercharaktere k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen nach Kreis gefiltert werden. "
                    + "Wenn " + Hauptprogramm.UMLAUT_SMALL_UE + "berhalb der Charakterliste einige Checkboxen nicht markiert sind, "
                    + "werden Nichtspielercharaktere dieses Kreises nicht angezeigt.";
    
    public static final String KAMPF_TITLE = "Anakoks Kampfsimulator";
    public static final String ABOUT_KAMPF = "Der Kampf gliedert sich in die drei Bereiche 'Gegnerrunde', 'Spielerrunde' und 'Kampfende'.\n\n"
            + "----------Die Gegnerrunde----------\n"
            + "In der Gegnerrunde greifen die Nichtspielercharaktere an. Durch Ausw" + Hauptprogramm.UMLAUT_SMALL_AE + "hlen des Gegners "
                    + "oder durch Klicken des 'Angriff' Buttons werden die W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe des selektierten Gegners simuliert "
                    + "und der Schaden am Spieler (abh" + Hauptprogramm.UMLAUT_SMALL_AE + "ngig von denen im Charaktermanager spezifizierten Werten) "
                    + "berechnet und angezeigt.\n\n"
            + "----------Die Spielerrunde----------\n"
            + "In der Spielerrunde greifen die Spieler selbst an. Es kann gew" + Hauptprogramm.UMLAUT_SMALL_AE + "hlt werden, welche Waffe der Spieler nutzt, "
                    + "indem die entsprechende Zelle in der Tabelle doppelt angeklickt wird.\n"
                    + "Wenn auf einen der Trefferzonen-Buttons geklickt wird, wird der Schaden berechnet, "
                    + "den der selektierte Spieler an jedem einzelnen Gegner bei dieser Zone erzeugen w" + Hauptprogramm.UMLAUT_SMALL_UE + "rde. "
                    + "Zus" + Hauptprogramm.UMLAUT_SMALL_AE + "tzlich kann ein Bonusprozentsatz angegeben werden.\n"
                    + "Durch Klick auf den Button 'Blocken' wird f" + Hauptprogramm.UMLAUT_SMALL_UE + "r jeden Gegner eine Probe ausgef" + Hauptprogramm.UMLAUT_SMALL_UE + "hrt "
                    + "und der Schaden bei Bestehen dieser Probe auf 0 gesetzt.\n"
                    + "Mit dem Button 'Schaden' werden die angegebenen Schadenspunkte dem selektierten Gegner von seinen Lebenspunkten abgezogen. "
                    + "Lebenspunkte k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen aber auch direkt editiert werden, "
                            + "indem die entsprechende Zelle in der Tabelle angeklickt wird.\n"
                    + "Mit den Buttons 'Entfernen' bzw. 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen' kann der selektierte Gegner aus der Tabelle entfernt werden. "
                            + "Gel" + Hauptprogramm.UMLAUT_SMALL_OE + "schte Gegner geben keine Erfahrungspunkte am Ende des Kampfes, entfernte schon."
                    + "\n\n"
            + "Wenn ein Gegner selektiert ist, kann durch einen Klick auf den Button 'St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkewurf' "
                    + "zudem ein St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkewurf des entsprechenden Gegners simuliert werden.\n\n"
                    + "Für eine genauere Beschreibung des Kampfes und insbesondere der Rolle von Effekten im Kampf sei auf das Handbuch verwiesen."
                    + "\n\n"
            + "----------Das Kampfende----------\n"
            + "Das Kampfende ist f" + Hauptprogramm.UMLAUT_SMALL_UE + "r die Berechnung der Erfahrungspunkte und das Generieren der m" 
                + Hauptprogramm.UMLAUT_SMALL_OE + "glichen Beute zust" + Hauptprogramm.UMLAUT_SMALL_AE + "ndig.\n"
            + "In der Gegnerliste sind alle Gegner aufgef" + Hauptprogramm.UMLAUT_SMALL_UE + "hrt, die nicht aus dem Kampf gel" 
                + Hauptprogramm.UMLAUT_SMALL_OE + "scht wurden. Gegner, die get" 
                + Hauptprogramm.UMLAUT_SMALL_OE + "tet oder entfernt wurden, sind gelb markiert. "
            + "Daneben sind die Erfahrungspunkte aufgelistet. Durch bestimmte Effekte kann es Spieler geben, die eine erh" 
                + Hauptprogramm.UMLAUT_SMALL_OE + "hte Anzahl erhalten. Diese sind gesonders aufgef" + Hauptprogramm.UMLAUT_SMALL_UE + "hrt. \n \n"
            + "Die rechte Seite des Fensters zeigt die Beute des gerade selektierten Gegners an. Sie ist zun" 
                + Hauptprogramm.UMLAUT_SMALL_AE + "chst leer, da die Beute erst generiert werden muss. "
            + "Dies erfolgt mithilfe des unteren Bereichs. Jeder Gegner kann Inventar und R" 
                + Hauptprogramm.UMLAUT_SMALL_UE + "stung/Waffen fallen lassen. "
            + "Wie gut diese Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde sind, kann mithilfe der Textboxen festgelegt werden: \n"
            + "Die obere spezifiziert jeweils den Wert. Im Fall des Inventars funktioniert das direkt, "
            + "im Fall der R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung wird spezifiziert, wie viel schlechter sie sein soll als die tats" 
                + Hauptprogramm.UMLAUT_SMALL_AE + "chliche R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung des Gegners. "
            + "Die untere Textbox erlaubt es, dass dieser Wert nicht immer exakt getroffen wird, sondern eine Schwankung vorliegt. "
            + "Sie spezifiziert die erwartete Gr" + Hauptprogramm.UMLAUT_SMALL_OE + "ße dieser Schwankung.\n\n"
            + "Welche Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde als R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung gelten, wird im H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler festgelegt. "
            + "Es sind die Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde der Kategorien 'Waffe', 'Harnisch', 'Helm', 'Handschuh', 'G" + Hauptprogramm.UMLAUT_SMALL_UE + "rtel' und 'Schuh'. "
            + "Wenn einige dieser Kategorien nicht existieren, ist es m" + Hauptprogramm.UMLAUT_SMALL_OE + "glich, dass keine Ausr" + Hauptprogramm.UMLAUT_SMALL_UE + "stungsbeute generiert wird.";

    public static final String HAENDLER_TITLE = "Giakiri, der H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler";
    public static final String ABOUT_HAENDLER = ""
            + "----------- Kategorien -----------\n"
            + "\nNEU ERSTELLEN:   Dazu muss ein neuer Gegenstand mit dieser Kategorie erstellt werden. Subkategorien werden mit '/' getrennt, z.B. Kategorie: Lebensmittel/Getr" + Hauptprogramm.UMLAUT_SMALL_AE + "nke/Bier"
            + "\nAUSWAHL:   Das Ausw" + Hauptprogramm.UMLAUT_SMALL_AE + "hlen einer (Sub-)Kategorie l" + Hauptprogramm.UMLAUT_SMALL_AE + "sst alle Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde dieser (Sub-)Kategorie in der Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "ndeliste erscheinen."
            + "\n\n----------- Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde -----------\n"
            + "\nNEU ERSTELLEN:   W" + Hauptprogramm.UMLAUT_SMALL_AE + "hle dazu den Eintrag 'Neuer Gegenstand' in der Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde Liste aus und bearbeite dann die rechten Textfelder. Speichern durch Taste 'ENTER' oder Button 'Speichern'."
            + "\nZU SUBKATEGORIE HINZUF" + Hauptprogramm.UMLAUT_CAPITAL_UE + "GEN:   Wenn die Subkategorie schon vorhanden ist, dann reicht es, als Kategorie nur die Subkategorie zu spezifizieren. Wenn eine neue Subkategorie erstellt werden soll, dann siehe 'Kategorien - NEU ERSTELLEN'"
            + "\nAUSWAHL:   Das Ausw" + Hauptprogramm.UMLAUT_SMALL_AE + "hlen eines Gegenstandes f" + Hauptprogramm.UMLAUT_SMALL_UE + "llt die rechten Textfelder mit seinen Eigenschaften."
            + "\n\n----------- ACHTUNG -----------\n"
            + "Der Beutegenerator (im Kampfende-Tab) teilt seine Beute auf in die Kategorien 'Waffe, Helm, Harnisch, Handschuh, G" + Hauptprogramm.UMLAUT_SMALL_UE + "rtel, Schuh'. Stellen Sie bitte sicher, dass diese Kategorien existieren, da sonst zu wenig Beute verteilt wird."            
            + "";
   
    public static final String WUERFELSIMULATOR_TITLE = "Cetos W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelsimulator";
    public static final String ABOUT_WUERFELSIMULATOR = "Mit dem W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelsimulator "
            + "k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen beliebige W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfel geworfen werden. "
            + "Ein Klick auf den entsprechenden Button erzeugt das Ergebnis.";
    
}
