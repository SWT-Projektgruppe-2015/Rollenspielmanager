package controller;

import model.Waffen;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class WaffenManipulator extends DBManipulator {
    private static WaffenManipulator singleton;   

    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Waffen) entity);
    }

    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((Waffen) entity);
    }

    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Waffen) entity);
    }

    public static WaffenManipulator getInstance() {
        if(singleton == null)
            singleton = new WaffenManipulator();
        return singleton;
    }
    
}
