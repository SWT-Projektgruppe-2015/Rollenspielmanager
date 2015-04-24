package tests.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Person;
import model.Ausruestung;
import model.Spieler;
import model.Waffen;
import model.Faehigkeiten;

public class DatabaseTest {
    @Test
    public void test2(){

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        theManager.getTransaction().begin();
        Person person = new Person();
        person.setFirstName("andreas");
        Ausruestung test = new Ausruestung();
        theManager.merge(test);
        Person person2 = new Person();
        person2.setFirstName("Boris");
        
        
        
        theManager.merge(person);
        theManager.merge(person2);
        theManager.getTransaction().commit();
        
        /*theManager.getTransaction().begin();
        Spieler testPlayer = new Spieler();
        testPlayer.name_ = "Krssk";
        testPlayer.ausruestung_ = test;
        theManager.merge(testPlayer);
        theManager.getTransaction().commit();*/
        
        assertNull(person.getId());
        
        Person p = (Person)theManager.find(Person.class, 1);

        assertNotNull(p);
    }
}
