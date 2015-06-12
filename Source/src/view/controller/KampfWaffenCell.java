package view.controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import view.tabledata.SpielerMitWaffe;

public class KampfWaffenCell extends TableCell<SpielerMitWaffe, String> {
    public KampfWaffenCell(Hauptprogramm main, SpielerrundeController controller) {
        addWaffenwechselOnDoubleClick(main, controller);
    }



    private void addWaffenwechselOnDoubleClick(Hauptprogramm main, 
            SpielerrundeController controller) {
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    TableCell<SpielerMitWaffe,String> c = 
                            (TableCell<SpielerMitWaffe, String>) event.getSource();
                    int currentIndex = c.getTableRow().getIndex();
                    SpielerMitWaffe currentSpieler = 
                            (SpielerMitWaffe) c.getTableView().getItems().get(currentIndex);
                    if(currentSpieler.isArmed())
                        main.openWaffenwechsel(controller, currentSpieler);
                }
            }
        });
    }
    
    
    
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(item);
        
        if (!empty) {
            SpielerMitWaffe currentSpieler = getTableView().getItems().get(
                    getTableRow().getIndex());
            
            String waffenInformation;
            if (currentSpieler.isArmed()) {
                String schadenInformation = "Schaden: "
                        + Integer.toString(currentSpieler.getWaffe()
                                .getWaffenSchaden_());
                String doubleClickInformation = "Waffenwechsel durch Doppelklick";
                waffenInformation = schadenInformation + "\n" + doubleClickInformation;
            } else {
                waffenInformation = "Spieler hat keine Waffe";
            }
            
            setTooltip(new Tooltip(waffenInformation));
        }
    }
}
