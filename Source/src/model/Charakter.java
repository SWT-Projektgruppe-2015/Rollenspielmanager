package model;

public abstract class Charakter {
    public abstract String getName_();
    abstract Ausruestung getAusruestung_();
    abstract void setAusruestung_(Ausruestung ausruestung);

    
    
    /**
     * Falls keine Ausruestung vorhanden ist, wird eine neue erstellt und mit
     * dem Charakter verbunden.
     * 
     * @return Ausruestung des Charakter, niemals null.
     */
    protected Ausruestung getAusruestungForModification() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null) {
            ausruestung = new Ausruestung();
            setAusruestung_(ausruestung);
        }
        return ausruestung;
    }    
    
    
    
    public int getDefR() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;

        return getAusruestung_().getDefR_();
    }

    
    
    public void setDefR(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefR_(def);
    }
    
    
    public int getDefH() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;
        
        return getAusruestung_().getDefH_();
    }
    
    

    public void setDefH(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefH_(def);
    }

    
    
    public int getDefS() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 0;
        
        return getAusruestung_().getDefS_();
    }
    
    public void setDefS(int def) {
        Ausruestung ausruestung = getAusruestungForModification();
        
        if (def >= 0)
            ausruestung.setDefS_(def);
    }
}
