package view;

import view.controller.Hauptprogramm;
import model.Gruppe;
import model.Spieler;

public class NotificationTexts {

    public static String textForNewGruppe(Gruppe gruppe) {
        return "Neue Gruppe '" + gruppe.getName() + "' angelegt.";
    }

    public static String textForGruppenRenaming(String oldName, String newName) {
        return "Gruppe '" + oldName + "' in '" + newName + "' umbenannt."; 
    }

    public static String textForGruppenDeletion(Gruppe gruppe) {
        return "Gruppe '" + gruppe.getName() + "' gelöscht.";
    }

    public static String textForAddingSpielerToGruppe(Spieler spieler, Gruppe gruppe) {
        return "Spieler '" + spieler.getName_() + "' zur Gruppe '" + gruppe.getName() + "' hinzugef" + Hauptprogramm.UMLAUT_SMALL_UE + "gt.";
    }

    public static String textForRemovingSpielerFromGruppe(Spieler spieler, Gruppe gruppe) {
        return "Spieler '" + spieler.getName_() + "' aus Gruppe '" + gruppe.getName() + "' entfernt.";
    }
    
}
