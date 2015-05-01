package tests.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Spieler;

import org.junit.Before;
import org.junit.Test;

public class SpielerTest {
	private Spieler normalSpieler;
	
	@Before
	public void initObjects() {
		normalSpieler = new Spieler();
		normalSpieler.setLevel_(2);
		normalSpieler.setKreis_(1);
	}
	
	
	@Test
	public void levelIncrease() {
		int kreisBefore = normalSpieler.getKreis_();
		int levelBefore = normalSpieler.getLevel_();
		
		normalSpieler.increaseLevel();
		assertTrue(normalSpieler.getKreis_() == kreisBefore);
		assertTrue(normalSpieler.getLevel_() == levelBefore + 1);
	}
	
	@Test
	public void levelIncreaseOnMaxLevel() {
		Spieler spielerOnMaxLevel = createMaxLevelSpieler();
		
		int kreisBefore = spielerOnMaxLevel.getKreis_();
		
		spielerOnMaxLevel.increaseLevel();
		assertTrue(spielerOnMaxLevel.getKreis_() == kreisBefore + 1);
		assertTrue(spielerOnMaxLevel.getLevel_() == 1);
	}
	
	@Test
	public void levelIncreaseOnMaxLevelAndKreis() {
		Spieler spielerOnMaxLevelAndKreis = createMaxLevelSpieler();
		spielerOnMaxLevelAndKreis.setKreis_(Spieler.MAX_KREIS);
		
		int kreisBefore = spielerOnMaxLevelAndKreis.getKreis_();
		int levelBefore = spielerOnMaxLevelAndKreis.getLevel_();
		
		spielerOnMaxLevelAndKreis.increaseLevel();
		assertTrue(spielerOnMaxLevelAndKreis.getKreis_() == kreisBefore);
		assertTrue(spielerOnMaxLevelAndKreis.getLevel_() == levelBefore);
	}
	
	private Spieler createMaxLevelSpieler() {
		Spieler spielerOnMaxLevel = new Spieler();
		spielerOnMaxLevel.setLevel_(Spieler.MAX_LEVEL);
		spielerOnMaxLevel.setKreis_(1);
		
		return spielerOnMaxLevel;
	}
	
	@Test
	public void levelDecrease() {
		int kreisBefore = normalSpieler.getKreis_();
		int levelBefore = normalSpieler.getLevel_();
		
		normalSpieler.decreaseLevel();		
		assertTrue(normalSpieler.getKreis_() == kreisBefore);
		assertTrue(normalSpieler.getLevel_() == levelBefore - 1);
	}
	
	@Test
	public void levelDecreaseOnMinLevel() {
		Spieler spielerOnMinLevel = createSpielerOnMinLevel();
		
		int kreisBefore = spielerOnMinLevel.getKreis_();
		
		spielerOnMinLevel.decreaseLevel();		
		assertTrue(spielerOnMinLevel.getKreis_() == kreisBefore - 1);
		assertTrue(spielerOnMinLevel.getLevel_() == Spieler.MAX_LEVEL);
	}
	
	@Test
	public void levelDecreaseOnMinLevelAndKreis() {
		Spieler spielerOnMinLevelAndKreis = createSpielerOnMinLevel();
		spielerOnMinLevelAndKreis.setKreis_(1);
				
		spielerOnMinLevelAndKreis.decreaseLevel();		
		assertTrue(spielerOnMinLevelAndKreis.getKreis_() == 1);
		assertTrue(spielerOnMinLevelAndKreis.getLevel_() == 1);
	}
	
	private Spieler createSpielerOnMinLevel() {
		Spieler spielerOnMinLevel = new Spieler();
		spielerOnMinLevel.setKreis_(2);
		spielerOnMinLevel.setLevel_(1);
		
		return spielerOnMinLevel;
	}
	
	
	@Test
	public void allPlayersAreReturned() {
		List<Spieler> allPlayers = Spieler.getAllPlayers();
		for(Spieler player : allPlayers) {
			System.out.println(player.getName_());
		}
	}
	
	
	
	@Test
	public void playersHaveDefR() {
		List<Spieler> allPlayers = Spieler.getAllPlayers();
		for(Spieler player : allPlayers) {
			assertNotNull(player.getDefR());
		}
	}
	
	
	
	@Test
	public void playersHaveDefH() {
		List<Spieler> allPlayers = Spieler.getAllPlayers();
		for(Spieler player : allPlayers) {
			assertNotNull(player.getDefH());
		}
	}
	
	
	
	@Test
	public void playersHaveDefS() {
		List<Spieler> allPlayers = Spieler.getAllPlayers();
		for(Spieler player : allPlayers) {
			assertNotNull(player.getDefS());
		}
	}
}
