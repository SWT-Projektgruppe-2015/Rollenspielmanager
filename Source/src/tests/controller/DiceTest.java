package tests.controller;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import controller.DiceRoller;

public class DiceTest {
	private abstract class DiceTester {	
		public void rollCoversRange() {
			int result;
			int[] rolled = new int[getMaxValue()];
			
			for(int i = 0; i<1000; i++) {
				result = roll();
				rolled[result-1]++;
			}
			
			assertCoversRange(rolled);
		}
		
		
		
		public void rollIsInCorrectRange() {
			int result;
			for(int i = 0; i<100; i++) {
				result = roll();
				assertCorrectRange(result, getMaxValue());
			}
		}
		
		
		protected abstract int roll();
		protected abstract int getMaxValue();
		
		
		
		private void assertCorrectRange(int result, int max) {
			assertTrue(1<=result);
			assertTrue(result<=max);
		}
		
		
		
		private void assertCoversRange(int[] results) {
			for(int i = 0; i<results.length; i++) {
				assertTrue(results[i] != 0);
			}
		}
		
	}
	
	
	
	private class W2Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW2();
		}

		@Override
		protected int getMaxValue() {
			return 2;
		}		
	}
	
	
	
	private class W3Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW3();
		}

		@Override
		protected int getMaxValue() {
			return 3;
		}		
	}
	
	
	
	private class W4Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW4();
		}

		@Override
		protected int getMaxValue() {
			return 4;
		}		
	}
	
	
	
	private class W6Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW6();
		}

		@Override
		protected int getMaxValue() {
			return 6;
		}		
	}
	
	
	
	private class W8Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW8();
		}

		@Override
		protected int getMaxValue() {
			return 8;
		}		
	}
	
	
	
	private class W10Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW10();
		}

		@Override
		protected int getMaxValue() {
			return 10;
		}		
	}
	
	
	
	private class W12Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW12();
		}

		@Override
		protected int getMaxValue() {
			return 12;
		}		
	}
	
	
	
	private class W20Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW20();
		}

		@Override
		protected int getMaxValue() {
			return 20;
		}		
	}
	
	
	
	private class W30Tester extends DiceTester {
		@Override
		protected int roll() {
			return DiceRoller.RollW30();
		}

		@Override
		protected int getMaxValue() {
			return 30;
		}
		
	}
	
	
	@Test
	public void W2IsInCorrectRange() {
		W2Tester w2Roller = new W2Tester();
		w2Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W2CoversRange() {
		W2Tester w2Roller = new W2Tester();
		w2Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W3IsInCorrectRange() {
		W3Tester w3Roller = new W3Tester();
		w3Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W3CoversRange() {
		W3Tester w3Roller = new W3Tester();
		w3Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W4IsInCorrectRange() {
		W4Tester w4Roller = new W4Tester();
		w4Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W4CoversRange() {
		W4Tester w4Roller = new W4Tester();
		w4Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W6IsInCorrectRange() {
		W6Tester w6Roller = new W6Tester();
		w6Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W6CoversRange() {
		W6Tester w6Roller = new W6Tester();
		w6Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W8IsInCorrectRange() {
		W8Tester w8Roller = new W8Tester();
		w8Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W8CoversRange() {
		W8Tester w8Roller = new W8Tester();
		w8Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W10IsInCorrectRange() {
		W10Tester w10Roller = new W10Tester();
		w10Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W10CoversRange() {
		W10Tester w10Roller = new W10Tester();
		w10Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W12IsInCorrectRange() {
		W12Tester w12Roller = new W12Tester();
		w12Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W12CoversRange() {
		W12Tester w12Roller = new W12Tester();
		w12Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W20IsInCorrectRange() {
		W12Tester w12Roller = new W12Tester();
		w12Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W20CoversRange() {
		W20Tester w20Roller = new W20Tester();
		w20Roller.rollCoversRange();
	}
	
	
	
	@Test
	public void W30IsInCorrectRange() {
		W30Tester w30Roller = new W30Tester();
		w30Roller.rollIsInCorrectRange();
	}
	
	
	
	@Test
	public void W30CoversRange() {
		W30Tester w30Roller = new W30Tester();
		w30Roller.rollCoversRange();
	}
}
