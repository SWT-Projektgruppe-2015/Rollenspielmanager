package tests.controller;


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
	@BeforeClass
	public static void setUp() {
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
	@Test
	public void causeEntityExistsException()	{
		testInstance.add(testSpieler);
		assertFalse("Can add same player twice.", testInstance.add(testSpieler));
	}
	
	
}
