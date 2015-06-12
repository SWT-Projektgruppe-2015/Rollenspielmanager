package view.controller;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import view.tabledata.SchadenAmSpieler;

public class TrefferzoneCell extends TableCell<SchadenAmSpieler, String> {
    protected void updateItem(String zone, boolean empty)   {
        super.updateItem(zone, empty);
        setText(zone);
        if(zone == null || zone.isEmpty())  {
            return;
        }
        switch(zone)    {
            case SchadenAmSpieler.DANEBEN:  {
                setTextFill(Color.BLACK);
                setStyle("-fx-background-color: white; "
                        + "-fx-font-weight: bold");
                break;
            }
            case SchadenAmSpieler.RUESTUNG:  {
                setTextFill(Color.WHITE);
                setStyle("-fx-background-color: #339966; "
                        + "-fx-font-weight: bold");
                break;
            }
            case SchadenAmSpieler.HELM:  {
                setTextFill(Color.BLACK);
                setStyle("-fx-background-color: gold; "
                        + "-fx-font-weight: bold");
                break;
            }
            case SchadenAmSpieler.DIREKT:  {
                setTextFill(Color.BLACK);
                setStyle("-fx-background-color: darkorange; "
                        + "-fx-font-weight: bold");
                break;
            }
            case SchadenAmSpieler.KRITISCH:  {
                setTextFill(Color.WHITE);
                setStyle("-fx-background-color: darkred; "
                        + "-fx-font-weight: bold");
                break;
            }
        }
    }
}
