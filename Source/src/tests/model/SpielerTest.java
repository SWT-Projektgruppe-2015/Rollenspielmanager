package tests.model;

import java.util.List;

import model.Spieler;
import model.charakterdaten.Charakter;

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
}
