package tests.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Ausruestung;
import model.Beute;
import model.Faehigkeiten;
import model.Spieler;
import model.Waffen;

public class DatabaseTest {
    @Test
    public void test2(){

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        theManager.getTransaction().begin();

        
        
//        Ausruestung defaultAusruestung = new Ausruestung();
//        defaultAusruestung.defH_=2;
//        defaultAusruestung.defR_=2;
//        defaultAusruestung.defS_=2;
//        theManager.persist(defaultAusruestung);
//        Beute defaultBeute = new Beute();
//        defaultBeute.besonderesWkt_ = 0.0;
//        defaultBeute.ruestungWkt_ = 0.0;
//        defaultBeute.waffenWkt_ = 0;
//        defaultBeute.geldBetrag_ = 0;
//        defaultBeute.profil_ = "Default";
//        defaultBeute.schwaechungsFaktor_ = 0.0;
//        theManager.merge(defaultBeute);
//        
//        
//        
//        theManager.getTransaction().commit();
//        
//        theManager.getTransaction().begin();
//        Spieler testPlayer = new Spieler();
//        testPlayer.name_ = "Hovalit";
//        testPlayer.ausruestung_ = (Ausruestung)theManager.find(Ausruestung.class, 2);
//       
//        Waffen testWaffe = new Waffen();
//        testWaffe.waffenName_="Stab";
//        testWaffe.ausruestung_ = (Ausruestung)theManager.find(Ausruestung.class, 2);
//        
//        theManager.persist(testWaffe);
//        theManager.persist(testPlayer);
//        theManager.getTransaction().commit();
//        
//        Ausruestung a = (Ausruestung)theManager.find(Ausruestung.class, 2);

//        assertNotNull(a);
    }
}
