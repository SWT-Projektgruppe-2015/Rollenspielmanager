package tests.controller;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import controller.Dice;

public class DiceTest {
    private abstract class DiceTester {
        public void rollCoversRange() {
            int result;
            int[] rolled = new int[getMaxValue()];

            for (int i = 0; i < 1000; i++) {
                result = roll();
                rolled[result - 1]++;
            }

            assertCoversRange(rolled);
        }

        public void rollIsInCorrectRange() {
            int result;
            for (int i = 0; i < 100; i++) {
                result = roll();
                assertCorrectRange(result, getMaxValue());
            }
        }

        protected abstract int roll();

        protected abstract int getMaxValue();

        private void assertCorrectRange(int result, int max) {
            assertTrue(1 <= result);
            assertTrue(result <= max);
        }

        private void assertCoversRange(int[] results) {
            for (int i = 0; i < results.length; i++) {
                assertTrue(results[i] != 0);
            }
        }

    }

    private class W2Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW2();
        }

        @Override
        protected int getMaxValue() {
            return 2;
        }
    }

    private class W3Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW3();
        }

        @Override
        protected int getMaxValue() {
            return 3;
        }
    }

    private class W4Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW4();
        }

        @Override
        protected int getMaxValue() {
            return 4;
        }
    }

    private class W6Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW6();
        }

        @Override
        protected int getMaxValue() {
            return 6;
        }
    }

    private class W8Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW8();
        }

        @Override
        protected int getMaxValue() {
            return 8;
        }
    }

    private class W10Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW10();
        }

        @Override
        protected int getMaxValue() {
            return 10;
        }
    }

    private class W12Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW12();
        }

        @Override
        protected int getMaxValue() {
            return 12;
        }
    }

    private class W20Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW20();
        }

        @Override
        protected int getMaxValue() {
            return 20;
        }
    }

    private class W30Tester extends DiceTester {
        @Override
        protected int roll() {
            return Dice.RollW30();
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
    
    
    @Test
    public void verifyIndependentRolls() {
        for(int i = 0; i < 20; ++i){
            int a[] = {Dice.RollW30() , Dice.RollW30() , Dice.RollW30()};
            int b[] = {Dice.RollW30() , Dice.RollW30() , Dice.RollW30()};
            assertTrue(a!=b);
        }
    }
    
    
    
    @Test
    public void negativeGeschickRollThrowsError() {
        boolean exceptionThrown = false;
        try {
            Dice.rollGeschick(-1);
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        
        assertTrue(exceptionThrown);
    }
    
    
    
    @Test
    public void zeroGeschickRollThrowsError() {
        boolean exceptionThrown = false;
        try {
            Dice.rollGeschick(0);
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        
        assertTrue(exceptionThrown);
    }
    
    @Test
    public void verifySingleGeschick1Roll() {
        int geschick = 1; 
        int upperBound = 4, lowerBound = 1;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    @Test
    public void verifySingleGeschick4Roll() {
        int geschick = 4; 
        int upperBound = 5, lowerBound = 2;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    @Test
    public void verifySingleGeschick8Roll() {
        int geschick = 8; 
        int upperBound = 6, lowerBound = 3;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    @Test
    public void verifySingleGeschick10Roll() {
        int geschick = 10; 
        int upperBound = 7, lowerBound = 2;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    @Test
    public void verifySingleGeschick14Roll() {
        int geschick = 14; 
        int upperBound = 8, lowerBound = 3;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick18Roll() {
        int geschick = 18; 
        int upperBound = 9, lowerBound = 2;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick19Roll() {
        int geschick = 19; 
        int upperBound = 10, lowerBound = 3;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick24Roll() {
        int geschick = 24; 
        int upperBound = 13, lowerBound = 2;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick27Roll() {
        int geschick = 27; 
        int upperBound = 14, lowerBound = 3;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick30Roll() {
        int geschick = 30; 
        int upperBound = 18, lowerBound = -1;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick31Roll() {
        int geschick = 31; 
        int upperBound = 19, lowerBound = 0;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick34Roll() {
        int geschick = 34; 
        int upperBound = 21, lowerBound = 2;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick39Roll() {
        int geschick = 39; 
        int upperBound = 22, lowerBound = 3;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick42Roll() {
        int geschick = 42; 
        int upperBound = 23, lowerBound = 4;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    
    
    
    @Test
    public void verifySingleGeschick55Roll() {
        int geschick = 67; 
        int upperBound = 35, lowerBound = 6;
        verifiyBoundsForGeschickRoll(geschick, upperBound, lowerBound);
    }
    


    private void verifiyBoundsForGeschickRoll(int geschick, int upperBound, int lowerBound) {
        for(int i = 0; i < 100; ++i){
            int ergebnis = Dice.rollGeschick(geschick);
            assertTrue(ergebnis <= upperBound);
            assertTrue(ergebnis >= lowerBound);
        }
    }
}
