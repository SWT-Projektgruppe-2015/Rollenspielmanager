package view.tabledata;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GegnerTyp;

public class GegnerEinheitImKampf implements Comparable<GegnerEinheitImKampf> {
    private GegnerTyp gegnerTyp_;
    private StringProperty countOf_ = new SimpleStringProperty("1");
    private StringProperty gegnerTypName_ = new SimpleStringProperty("name");
    
    
    public GegnerEinheitImKampf(GegnerTyp gegnerTyp) {
        gegnerTyp_ = gegnerTyp;
        gegnerTypName_.set(gegnerTyp.getName_());
    }
    
    
    public String getGegnerTypName_() {
        return gegnerTypName_.get();
    }
    
    
    
    public String getCountOf_() {
        return countOf_.get();
    }
    
    
    public int getCountAsInteger() {
        return Integer.parseInt(countOf_.get());
    }
    
    
    public void setCountOf_(int count) {
        countOf_.set(Integer.toString(count));
    }
    
    
    public GegnerTyp getGegnerTyp() {
        return gegnerTyp_;
    }


    @Override
    public int compareTo(GegnerEinheitImKampf o) {
        return gegnerTypName_.get().compareTo(o.getGegnerTypName_());
    }
}
