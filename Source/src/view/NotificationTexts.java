package view;

import view.controller.Hauptprogramm;
import model.Charakter;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Gruppe;
import model.Spieler;
import model.Waffen;

public class NotificationTexts {

    public static final String DELETE_INFORMATION = "Diese Aktion ist unwiderruflich.";
    public static final String DELETE_TITLE = "Wirklich l" + Hauptprogramm.UMLAUT_SMALL_OE + "schen?";
    public static final String CONFIRMATION_DELETE_GEGNER_FROM_KAMPF = "Soll der Gegner wirklich aus dem Kampf gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht werden?";
    public static final String DELETE_FROM_KAMPF_INFORMATION = "Der Gegner wird aus der Erfahrungspunkteberechnung entfernt.\n"
            + "Um ihn nur als kampfunf" + Hauptprogramm.UMLAUT_SMALL_AE + "hig zu kennzeichenen, w" + Hauptprogramm.UMLAUT_SMALL_AE + "hle 'Entfernen'.";
    public static final String GEGNER_TYP_CANNOT_BE_REMOVED = "Ein Gegnertyp mit k" + Hauptprogramm.UMLAUT_SMALL_AE + "mpfenden Einheiten muss in der Tabelle bleiben.";
    public static final String WRONG_LEBENSPUNKTE_FORMAT = "Lebenspunkte m" + Hauptprogramm.UMLAUT_SMALL_UE + "ssen im Format '{momentan} / {max}' angegeben werden.";
    
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

    public static String textForLevelChange(Spieler spieler) {
        return "Level für Charakter '" + spieler.getName_() + "' gespeichert.";
    }

    public static String textForNewCharakter(Charakter charakter) {
        return "Neuer Charakter '" + charakter.getName_() + "' angelegt.";
    }

    public static String textForSpielerUpdate(Spieler spieler) {
        return "Daten f" + Hauptprogramm.UMLAUT_SMALL_UE + "r Spielercharakter '" + spieler.getName_() + "' gespeichert.";
    }

    public static String textForWaffenDeletion(Waffen selectedWaffe) {
        return "Waffe '" + selectedWaffe.getWaffenName_() + "' wurde gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht.";
    }

    public static String confirmationTextWaffenDeletion(Waffen selectedWaffe) {
        return "Soll Waffe '" + selectedWaffe.getWaffenName_() + "' wirklich gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht werden?";
    }

    
    
    
    public static String confirmationTextGegnerDeletion(GegnerTyp selectedGegner) {
        return "Soll Gegner '" + selectedGegner.getName_() + "' wirklich gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht werden?";
    }

    public static String textForGegnerDeletion(GegnerTyp selectedGegner) {
        return "Gegner '" + selectedGegner.getName_() + "' wurde gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht.";
    }

    public static String textForGegnerUpdate(GegnerTyp selectedGegner) {
        return "Daten f" + Hauptprogramm.UMLAUT_SMALL_UE + "r Nichtspielercharakter '" + selectedGegner.getName_() + "' gespeichert.";
    }

    public static String textForRemovingGegnerFromKampf(String removedName) {
        return "Gegner '" + removedName + "' ist kampfunf" + Hauptprogramm.UMLAUT_SMALL_AE + "hig und wurde aus der Tabelle entfernt.\n"
                + "Er z" + Hauptprogramm.UMLAUT_SMALL_AE + "hlt noch f" + Hauptprogramm.UMLAUT_SMALL_UE + "r Erfahrung.";
    }

    public static String textForDeletingGegnerFromKampf(String deletedName) {
        return "Gegner '" + deletedName + "' aus Kampf gel" + Hauptprogramm.UMLAUT_SMALL_OE + "scht.";
    }

    public static String textForGegnerRemovedDueToLebenspunkte(
            GegnerEinheit changedGegner) {
        return "Gegner '" + changedGegner.getName_() + "' hat 0 Lebenspunkte und wurde aus der Tabelle entfernt.\n"
                + "Er z" + Hauptprogramm.UMLAUT_SMALL_AE + "hlt noch f" + Hauptprogramm.UMLAUT_SMALL_UE + "r Erfahrung.";
    }

    public static String textForSchadenDealt(GegnerEinheit gegner) {
        return "Lebenspunkte von Gegner '" + gegner.getName_() + "' aktualisiert.";
    }

    public static String textForGegnerUpdateFailed(GegnerTyp selectedGegner) {
        return "Nichtspielercharakter '" + selectedGegner.getName_() + "' konnte nicht geupdated werden: \n" 
                + "Einige Werte sind ung" + Hauptprogramm.UMLAUT_SMALL_UE + "ltig.";
    }

    public static String textForAusruestungUpdateFailed(Charakter charakter) {
        return "Ausr" + Hauptprogramm.UMLAUT_SMALL_UE + "stung von '" + charakter.getName_() + "' konnte nicht geupdated werden: \n" 
                + "Einige Werte sind ung" + Hauptprogramm.UMLAUT_SMALL_UE + "ltig.";
    }

    public static String textForWaffenUpdateFailed(Waffen selectedWaffe) {
        return "Waffe '" + selectedWaffe.getWaffenName_() + "' konnte nicht geupdated werden: \n" 
                + "Einige Werte sind ung" + Hauptprogramm.UMLAUT_SMALL_UE + "ltig.";
    }    
}
