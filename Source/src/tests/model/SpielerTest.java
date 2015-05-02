package tests.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Spieler;
import model.Waffen;

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
	public void setDefH() {
		normalSpieler.setDefH(200);
		assertTrue(normalSpieler.getDefH() == 200);
	}
	
	@Test
	public void setNegativeDefH() {
		int defHBefore = normalSpieler.getDefH();
		
		normalSpieler.setDefH(-200);
		assertTrue(normalSpieler.getDefH() == defHBefore);
	}
	
	@Test
	public void setDefR() {
		normalSpieler.setDefR(200);
		assertTrue(normalSpieler.getDefR() == 200);
	}
	
	@Test
	public void setNegativeDefR() {
		int defRBefore = normalSpieler.getDefR();
		
		normalSpieler.setDefR(-200);
		assertTrue(normalSpieler.getDefR() == defRBefore);
	}
	
	@Test
	public void setDefS() {
		normalSpieler.setDefS(200);
		assertTrue(normalSpieler.getDefS() == 200);
	}
	
	@Test
	public void setNegativeDefS() {
		int defSBefore = normalSpieler.getDefS();
		
		normalSpieler.setDefS(-200);
		assertTrue(normalSpieler.getDefS() == defSBefore);
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
	public void addWaffe() {
		Waffen waffe = new Waffen();
		
		normalSpieler.addWaffe(waffe);
		assertTrue(normalSpieler.getWaffen().contains(waffe));
	}
	
	@Test
	public void deleteWaffe() {
		Waffen waffe = new Waffen();		
		normalSpieler.addWaffe(waffe);
		
		normalSpieler.deleteWaffe(waffe);
		assertTrue(!normalSpieler.getWaffen().contains(waffe));
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
