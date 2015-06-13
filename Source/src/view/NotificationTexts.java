package view;

import view.controller.Hauptprogramm;
import model.Gruppe;
import model.Spieler;

public class NotificationTexts {

    public static final String DELETE_INFORMATION = "Diese Aktion ist unwiderruflich.";
    public static final String DELETE_TITLE = "Wirklich l" + Hauptprogramm.UMLAUT_SMALL_OE + "schen?";

    public static String textForNewGruppe(Gruppe gruppe) {
        return "Neue Gruppe '" + gruppe.getName() + "' angelegt.";
    }

    public static String textForGruppenRenaming(String oldName, String newName) {
        return "Gruppe '" + oldName + "' in '" + newName + "' umbenannt."; 
    }

    public static String textForGruppenDeletion(Gruppe gruppe) {
        return "Gruppe '" + gruppe.getName() + "' gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht.";
    }

    public static String textForAddingSpielerToGruppe(Spieler spieler, Gruppe gruppe) {
        return "Charakter '" + spieler.getName_() + "' zur Gruppe '" + gruppe.getName() + "' hinzugef" + Hauptprogramm.UMLAUT_SMALL_UE + "gt.";
    }

    public static String textForRemovingSpielerFromGruppe(Spieler spieler, Gruppe gruppe) {
        return "Charakter '" + spieler.getName_() + "' aus Gruppe '" + gruppe.getName() + "' entfernt.";
    }

    public static String confirmationTextGruppenDeletion(Gruppe gruppe) {
        return "Soll Gruppe '" + gruppe.getName_() + "' wirklich gel" + Hauptprogramm.UMLAUT_SMALL_OE +"scht werden?";
    }

    public static String confirmationTextCharakterDeletion(Spieler spieler) {
        return "Soll Charakter '" + spieler.getName_() + "' wirklich gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht werden?";
    }

    public static String textForCharakterDeletion(Spieler spieler) {
        return "Charakter '" + spieler.getName_() + "' wurde gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht.";
    }
    
}
