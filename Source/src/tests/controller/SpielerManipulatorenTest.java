package tests.controller;


//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import model.Ausruestung;
import model.Spieler;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.Before;



import controller.SpielerManipulator;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class SpielerManipulatorenTest {
	
	private static SpielerManipulator testInstance;
	private static Spieler testSpieler;
	//private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    //private static EntityManager theManager = factory.createEntityManager();
	@BeforeClass
	public static void setUpBeforeClass() {
		testInstance = SpielerManipulator.getInstance();
		testSpieler = new Spieler();
	}

	@Test
	public void CanMakeInstance()	{
		assertNotNull(testInstance);
	}
	
	@Test
	public void twoInstancesAreSame()	{
		SpielerManipulator testInstance2 = SpielerManipulator.getInstance();
		assertSame(testInstance, testInstance2);
	}
	
	@Test
	public void canAddSpieler()	{
		assertTrue("Couldn't add new Spieler.", testInstance.add(testSpieler));
	}
	/**
	 * Ignoriert bis man rausbekommt wie man die Exception ausloest.
	 */
	@Ignore
	@Test
	public void causeEntityExistsException()	{
		testInstance.add(testSpieler);
		
		assertFalse("Can add same player twice.", testInstance.add(testSpieler));
		
	}
	
	@Test
	public void canDeletePlayer()	{
		assertTrue("Can't delete player", testInstance.delete(testSpieler));
	}
	
}
