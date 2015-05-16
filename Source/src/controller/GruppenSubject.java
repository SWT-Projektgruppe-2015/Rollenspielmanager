package controller;

import java.util.ArrayList;
import java.util.List;

import controller.interfaces.GruppenObserver;
import model.Gruppe;

public class GruppenSubject {
    
    private List<Gruppe> gruppen_;
    private Gruppe selectedGruppe_;
    private List<GruppenObserver> gruppenObserver_;
    
    
    
    public GruppenSubject () {
        gruppen_ = new ArrayList<Gruppe>();
        selectedGruppe_ = new Gruppe();
        gruppenObserver_ = new ArrayList<GruppenObserver>();
        
    }
    
    
    
    public void addGruppenObserver (GruppenObserver observer){
        gruppenObserver_.add(observer);
        
    }
    
    
    
    public void removeGruppenObserver (GruppenObserver observer) {
        gruppenObserver_.remove(observer);
    }
    
    
    private void notifyObservers() {
        for(GruppenObserver observer : gruppenObserver_){
            observer.update();
        }
    }
    
    
    public List<Gruppe> getGruppen(){
        return gruppen_;
        
    }
    
    
    public Gruppe getSelectedGruppe() {
        return selectedGruppe_;
        
    }
    
    
    
    public void setGruppen(List<Gruppe> gruppen_) {
        this.gruppen_ = gruppen_;
        notifyObservers();
    }



    public void setSelectedGruppe(Gruppe selectedGruppe) {
        this.selectedGruppe_ = selectedGruppe;
        notifyObservers();
    }
}
