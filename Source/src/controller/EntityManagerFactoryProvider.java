package controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
    private static EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("thePersistenceUnit");

    /**
     * @return the factory
     */
    public static EntityManagerFactory getFactory() {
        if(factory == null) {
            factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        }
        return factory;
    }

    /**
     * @param factory the factory to set
     */
    public static void setFactory(EntityManagerFactory factory) {
        EntityManagerFactoryProvider.factory = factory;
    }
}
