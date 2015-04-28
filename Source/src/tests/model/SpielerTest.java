package tests.model;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import model.Spieler;

import org.junit.Test;

public class SpielerTest {
	@Test
	public void spielerCanBeCreated() {
		Spieler spieler = new Spieler();
	}
	
	
	@Test
	public void allPlayersAreReturned() {
		List<Spieler> allPlayers = Spieler.getAllPlayers();
		for(Spieler player : allPlayers) {
			System.out.println(player.name_);
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
