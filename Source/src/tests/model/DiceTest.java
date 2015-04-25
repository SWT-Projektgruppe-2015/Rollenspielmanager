package tests.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.function.IntSupplier;
import controller.DiceRoller;

public class DiceTest {
	private void assertCorrectRange(int result, int max) {
		assertTrue(1<=result);
		assertTrue(result<=max);
	}
	
	
	
	private void assertCoversRange(int[] results) {
		for(int i = 0; i<results.length; i++) {
			assertTrue(results[i] != 0);
		}
	}
	
	
	
	private void rollCoversRange(IntSupplier roller, int max) {
		int result;
		int[] rolled = new int[max];
		
		for(int i = 0; i<1000; i++) {
			result = roller.getAsInt();
			rolled[result-1]++;
		}
		
		assertCoversRange(rolled);
	}
	
	
	
	private void rollIsInCorrectRange(IntSupplier roller, int max) {
		int result;
		for(int i = 0; i<100; i++) {
			result = roller.getAsInt();
			assertCorrectRange(result, max);
		}
	}
	
	
	
	@Test
	public void W4IsInCorrectRange() {
		IntSupplier w4Roller = () -> DiceRoller.RollW4();
		rollIsInCorrectRange(w4Roller, 4);
	}
	
	
	
	@Test
	public void W4CoversRange() {
		IntSupplier w4Roller = () -> DiceRoller.RollW4();
		rollCoversRange(w4Roller, 4);
	}
	
	
	
	
	@Test
	public void W6IsInCorrectRange() {
		IntSupplier w6Roller = () -> DiceRoller.RollW6();
		rollIsInCorrectRange(w6Roller, 6);
	}
	
	
	
	@Test
	public void W6CoversRange() {
		IntSupplier w6Roller = () -> DiceRoller.RollW6();
		rollCoversRange(w6Roller, 6);
	}
	
	
	
	@Test
	public void W8IsInCorrectRange() {
		IntSupplier w8Roller = () -> DiceRoller.RollW8();
		rollIsInCorrectRange(w8Roller, 8);
	}
	
	
	
	@Test
	public void W8CoversRange() {
		IntSupplier w8Roller = () -> DiceRoller.RollW8();
		rollCoversRange(w8Roller, 8);
	}
	
	
	
	@Test
	public void W12IsInCorrectRange() {
		IntSupplier w12Roller = () -> DiceRoller.RollW12();
		rollIsInCorrectRange(w12Roller, 12);
	}
	
	
	
	@Test
	public void W12CoversRange() {
		IntSupplier w12Roller = () -> DiceRoller.RollW12();
		rollCoversRange(w12Roller, 12);
	}
	
	
	
	@Test
	public void W10IsInCorrectRange() {
		IntSupplier w10Roller = () -> DiceRoller.RollW10();
		rollIsInCorrectRange(w10Roller, 10);
	}
	
	
	
	@Test
	public void W10CoversRange() {
		IntSupplier w10Roller = () -> DiceRoller.RollW10();
		rollCoversRange(w10Roller, 10);
	}
	
	
	
	@Test
	public void W20IsInCorrectRange() {
		IntSupplier w20Roller = () -> DiceRoller.RollW20();
		rollIsInCorrectRange(w20Roller, 20);
	}
	
	
	
	@Test
	public void W20CoversRange() {
		IntSupplier w20Roller = () -> DiceRoller.RollW20();
		rollCoversRange(w20Roller, 20);
	}
	
	
	
	@Test
	public void W30IsInCorrectRange() {
		IntSupplier w30Roller = () -> DiceRoller.RollW30();
		rollIsInCorrectRange(w30Roller, 30);
	}
	
	
	
	@Test
	public void W30CoversRange() {
		IntSupplier w30Roller = () -> DiceRoller.RollW30();
		rollCoversRange(w30Roller, 30);
	}
}
