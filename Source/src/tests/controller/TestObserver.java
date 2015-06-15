package tests.controller;

import controller.interfaces.GruppenObserver;

public class TestObserver implements GruppenObserver {
    private int updateCounter_ = 0;
    private int updateSelectedCounter_ = 0;
    
    
    
    /**
     * @return the updateCounter_
     */
    public int getUpdateCounter_() {
        return updateCounter_;
    }



    /**
     * @param updateCounter_ the updateCounter_ to set
     */
    public void setUpdateCounter_(int updateCounter_) {
        this.updateCounter_ = updateCounter_;
    }



    /**
     * @return the updateSelectedCounter_
     */
    public int getUpdateSelectedCounter_() {
        return updateSelectedCounter_;
    }



    /**
     * @param updateSelectedCounter_ the updateSelectedCounter_ to set
     */
    public void setUpdateSelectedCounter_(int updateSelectedCounter_) {
        this.updateSelectedCounter_ = updateSelectedCounter_;
    }



    @Override
    public void updateGruppenList() {
        System.out.println("I have updated.\n");
        setUpdateCounter_(getUpdateCounter_() + 1);
    }
    
    
    
    @Override
    public void updateSelectedGruppe() {
        System.out.println("I have updated the selected group.\n");
        setUpdateSelectedCounter_(getUpdateSelectedCounter_() + 1);
    }
    
}
