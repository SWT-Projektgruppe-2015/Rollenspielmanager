package tests.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Person;

public class DatabaseTest {
    @Test
    public void test2(){

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        theManager.getTransaction().begin();
        Person person = new Person();
        person.setFirstName("andreas");
        theManager.merge(person);
        theManager.getTransaction().commit();
        System.out.println(person.getId());

        Person p = (Person)theManager.find(Person.class, 1);
        System.out.println(person.getId());

        assertNotNull(p);
    }
}
