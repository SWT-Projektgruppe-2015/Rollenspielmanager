package view.tabledata;

import javafx.beans.property.StringProperty;

public interface SharedGegnerTableEntry {
    public StringProperty nameProperty();
    public StringProperty dealtSchadenProperty();
    public StringProperty lebenspunkteProperty();
}
