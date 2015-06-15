package controller;

import java.util.ArrayList;
import java.util.List;

import controller.interfaces.GruppenObserver;
import model.Gruppe;

/**
 * @author Andreas
 *
 */
public class GruppenSubject {
    
    private List<Gruppe> gruppen_;
    private Gruppe selectedGruppe_;
    private List<GruppenObserver> gruppenObserver_;
    
    
    
    public GruppenSubject () {
        gruppen_ = Gruppe.getAll();
        selectedGruppe_ = gruppen_.isEmpty() ? new Gruppe() : gruppen_.get(0);
        gruppenObserver_ = new ArrayList<GruppenObserver>();
        
    }
    
    
    
    public void addGruppenObserver (GruppenObserver observer){
        gruppenObserver_.add(observer);
        
    }
    
    
    
    /**
     * Testmethode um zu ueberpruefen ob ein Observer in der Observerliste enthalten ist.
     * @param observer
     * @return
     */
    public boolean containsGruppenObserver(GruppenObserver observer)   {
        return gruppenObserver_.contains(observer);
    }
    
    
    
    public void removeGruppenObserver (GruppenObserver observer) {
        gruppenObserver_.remove(observer);
    }
    
    
    private void notifyObserversGruppenList() {
        for(GruppenObserver observer : gruppenObserver_){
            observer.updateGruppenList();
        }
    }
    
    
    
    private void notifyObserversSelectedGruppe() {
        for(GruppenObserver observer : gruppenObserver_){
            observer.updateSelectedGruppe();
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
        notifyObserversGruppenList();
    }



    public void setSelectedGruppe(Gruppe selectedGruppe) {
        this.selectedGruppe_ = selectedGruppe;
        notifyObserversSelectedGruppe();
    }
}
