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
            + "* Charaktermanager: Hier k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen Spielercharaktere und Nichtspiler-Charakter-Typen angelegt "
                    + "sowie Abenteuergruppen verwaltet werden \n\n"
            + "* Kampf: Nach der Wahl von Spielercharakteren und Gegner werden hier Gegnerw" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe simuliert und ausgewertet,"
                    + " Schadenswerte der Spieler berechnet und Erfahrungspunkte vergeben.\n\n"
            + "* H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler: Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde, ihre Traglast, Kategorie, Kosten und Beschreibung"
                    + " k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen hier verwaltet und insbesondere gesucht werden.\n\n"
            + "* W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfeln: W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelw" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe"
                    + " k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen simuliert werden.";
    
    public static final String CHARAKTER_MANAGER_TITLE = "Der Charaktermanager";
    public static final String ABOUT_CHARAKTER_MANAGER = 
            "Fenster zum Verwalten von Gruppen, Spielercharakteren und Nichtspieler-Charakter-Typen.\n"
            + "Die verschiedenen Tabs und ihre Bedienung:\n\n"
            + "Gruppen: Verwaltung von Gruppen.\n"
            + "1) Zur Erstellung einer neuen Gruppe tippe einen Namen in das Textfeld und dr" + Hauptprogramm.UMLAUT_SMALL_UE + "cke Enter. "
                    + "Alternativ kann die neue Gruppe auch mit dem Button 'Neu' best" + Hauptprogramm.UMLAUT_SMALL_AE + "tigt werden.\n"
            + "2) Zur " + Hauptprogramm.UMLAUT_CAPITAL_AE + "nderung des Namens einer bestehenden Gruppe "
                    + "w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle sie in der Dropdownbox aus und modifiziere den Namen in der Textbox. "
                    + "Best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit dem Button 'Update'.\n"
            + "3) Um die Zuweisung von Nichtspielercharakteren zu Gruppen umzu" + Hauptprogramm.UMLAUT_SMALL_AE + "ndern, selektiere die Gruppe. "
                    + "Nun k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen Charaktere durch klicken der Pfeile in die Gruppe hinein oder hinaus bewegt werden.\n"
            + "4) Um eine Gruppe zu l" + Hauptprogramm.UMLAUT_SMALL_OE + "schen, selektiere sie und "
                    + "w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den Button 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen'."
            + "\n\n"
            + "Spielercharaktere: Verwaltung von Spielercharakteren\n"
            + "Beide Tabs zu Charaktern haben folgende Bedienung:\n"
            + "1) Zur Erstellung eines neuen Charakters w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den Eintrag 'Neuer Spieler' bzw. 'Neuer Gegner'."
                    + "Modifiziere die Daten und best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit Enter.\n"
            + "2) Zur " + Hauptprogramm.UMLAUT_CAPITAL_AE + "nderung eines bestehenden Charakters w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle den "
                    + "entsprechenden Charakter aus, modifiziere die Daten und best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige mit Enter.\n"
            + "3) Zum L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen eines Charakters w" + Hauptprogramm.UMLAUT_CAPITAL_AE + "hle den "
                    + "entsprechenden Charakter aus und klicke auf den Button 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen'.\n"
            + "4) Durch Eingaben in das 'Suche' Textfeld werden nur alle Charaktere angezeigt, deren Name den eingegebenen Begriff enth" + Hauptprogramm.UMLAUT_SMALL_AE + "lt."
            + "\n\n"
            + "Nichtspielercharaktere: Verwaltung von Nichtspieler-Charakter-Typen\n\n"
            + "F" + Hauptprogramm.UMLAUT_SMALL_UE + "r die generelle Bedienung siehe 'Spielercharaktere'. Besonderheit:\n"
            + "Nichtspielercharakter k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen nach Kreis gefiltert werden. "
                    + "Wenn " + Hauptprogramm.UMLAUT_SMALL_UE + "berhalb der Charakterliste einige Checkboxen nicht markiert sind, "
                    + "werden Nichtspieler-Charakter-Typen dieses Kreises nicht angezeigt.";
    
    public static final String KAMPF_TITLE = "Der Kampf";
    public static final String ABOUT_KAMPF = "Der Kampf gliedert sich in die beiden Bereiche 'Gegnerrunde' und 'Spielerrunde'.\n\n"
            + "In der Gegnerrunde greifen die Nichtspielercharaktere an. Durch neues Ausw" + Hauptprogramm.UMLAUT_SMALL_AE + "hlen des Gegners "
                    + "oder durch Klicken des 'Angriff' Buttons werden die W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfe des selektierten Gegners simuliert "
                    + "und der Schaden am Spieler abh" + Hauptprogramm.UMLAUT_SMALL_AE + "ngig von denen im Charaktermanager spezifizierten Werten "
                    + "berechnet und angezeigt.\n\n"
            + "In der Spielerrunde greifen die Spieler selbst an. Es kann gew" + Hauptprogramm.UMLAUT_SMALL_AE + "hlt werden, welche Waffe der Spieler nutzt, "
                    + "indem die entsprechende Zelle in der Tabelle doppelt angeklickt wird.\n"
                    + "Wenn auf einen der Trefferzonen-Buttons geklickt wird, wird der Schaden berechnet, "
                    + "den der selektierte Spieler an jedem einzelnen Gegner bei dieser Zone erzeugen w" + Hauptprogramm.UMLAUT_SMALL_UE + "rde. "
                    + "Bei direkten Treffern kann zus" + Hauptprogramm.UMLAUT_SMALL_AE + "tzlich ein Bonusprozentsatz angegeben werden.\n"
                    + "Durch Klick auf den Button 'Blocken' wird f" + Hauptprogramm.UMLAUT_SMALL_UE + "r jeden Gegner eine Probe ausgef" + Hauptprogramm.UMLAUT_SMALL_UE + "hrt "
                    + "und der Schaden bei Bestehen dieser Probe auf 0 gesetzt.\n"
                    + "Mit dem Button 'Schaden' werden die angegebenen Schadenspunkte dem selektierten Gegner von seinen Lebenspunkten abgezogen. "
                    + "Lebenspunkte k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen aber auch direkt editiert werden, "
                            + "indem die entsprechende Zelle in der Tabelle angeklickt wird.\n"
                    + "Mit den Buttons 'Entfernen' bzw. 'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen' kann der selektierte Gegner aus der Tabelle entfernt werden. "
                            + "Gel" + Hauptprogramm.UMLAUT_SMALL_OE + "schte Gegner geben keine Erfahrungspunkte am Ende des Kampfes, entfernte schon."
                    + "\n\n"
            + "Wenn ein Gegner selektiert ist, kann durch einen Klick auf den Button 'St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkewurf' "
                    + "zudem ein St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkewurf des entsprechenden Gegners simuliert werden.";

    public static final String HAENDLER_TITLE = "Der H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler";
    public static final String ABOUT_HAENDLER = "Mit dem H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen "
            + "Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde verwaltet und gesucht werden. "
            + "Es wird grob unterschieden zwischen 'Einfachen Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nden' "
                    + "und 'R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung / Waffen'. "
                    + "Letztere haben zus" + Hauptprogramm.UMLAUT_SMALL_AE + "tzlich zu Name, Kategorie, Traglast, Preis und Beschreibung "
                    + "auch eine St" + Hauptprogramm.UMLAUT_SMALL_AE + "rke und einen Wert. \n\n"
            + "Kategorien k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen beliebig angelegt werden. "
            + "Sie sind dazu da, die Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde grob zu ordnen: "
                    + "Es werden immer nur Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nde einer Kategorie angezeigt. "
                    + "Zus" + Hauptprogramm.UMLAUT_SMALL_AE + "tzlich kann nach Gegenst" + Hauptprogramm.UMLAUT_SMALL_AE + "nden gesucht werden."
            + "\n\n"
            + "Um einen neuen Gegenstand anzulegen, w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle 'Neuer Gegenstand' aus der Liste, "
                    + "modifiziere die Angaben und best" + Hauptprogramm.UMLAUT_SMALL_AE + "tige durch den Button 'Speichern'.\n"
            + "Um einen bestehenden Gegenstand zu ver" + Hauptprogramm.UMLAUT_SMALL_AE + "ndern, verfahre " + Hauptprogramm.UMLAUT_SMALL_AE + "hnlich, "
                    + "aber selektiere den zu ver" + Hauptprogramm.UMLAUT_SMALL_AE + "ndernden Gegenstand.\n"
            + "Um einen Gegenstand zu l" + Hauptprogramm.UMLAUT_SMALL_OE + "schen, selektiere ihn und dr" + Hauptprogramm.UMLAUT_SMALL_UE + "cke "
                    + "'L" + Hauptprogramm.UMLAUT_SMALL_OE + "schen'.";
   
    public static final String WUERFELSIMULATOR_TITLE = "Der W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelsimulator";
    public static final String ABOUT_WUERFELSIMULATOR = "Mit dem W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelsimulator "
            + "k" + Hauptprogramm.UMLAUT_SMALL_OE + "nnen beliebige W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfel geworfen werden. "
            + "Ein Klick auf den entsprechenden Button erzeugt das Ergebnis.";
    
}
