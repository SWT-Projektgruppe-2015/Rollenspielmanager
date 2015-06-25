package controller;

import java.util.Random;



/**
 * Zuständig für das Simulieren von Würfelwürfen.
 * 
 * @author Britta Heymann
 *
 */
public class Dice {
    
    static private Random random = new Random();

    
    
    public static int RollW4() {
        return rollDice(4);
    }

    
    
    public static int RollW6() {
        return rollDice(6);
    }

    
    
    public static int RollW8() {
        return rollDice(8);
    }

    
    
    public static int RollW10() {
        return rollDice(10);
    }

    
    
    public static int RollW12() {
        return rollDice(12);
    }

    
    
    public static int RollW20() {
        return rollDice(20);
    }

    
    
    public static int RollW30() {
        return rollDice(30);
    }

    
    
    public static int RollW2()  {
        return rollDice(2);
    }

    
    
    public static int RollW3()  {
        return rollDice(3);
    }

    
    
    public static int rollDice(int faces)  {
        return random.nextInt(faces) + 1;
    }

    
    
    public static int rollGeschick(int geschick)    {
        int wuerfel = getWuerfel(geschick);
        int bonus = getBonus(geschick);
        return rollDice(wuerfel) + bonus;
    }

    
    
    private static int getBonus(int geschick) throws IllegalArgumentException   {
        if(geschick <= 0)   {
            throw new IllegalArgumentException();
        }
        int stufe = (geschick - 1)/3;
        switch(stufe) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;    
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 1;  
            case 6:
                return 2;
            case 7:
                return 1;
            case 8:
                return 2; 
            case 9:
                return -2;
            case 10:
                return -1;
            case 11:
                return 1; 
            case 12:
                return 2;
            case 13:
                return 3;
            default:
                return stufe - 17;
        }
    }

    
    
    private static int getWuerfel(int geschick) throws IllegalArgumentException {
        if(geschick <= 0)   {
            throw new IllegalArgumentException();
        }
        int stufe = (geschick - 1)/3;
        switch(stufe) {
            case 0:
            case 1:
            case 2:
                return 4;    
            case 3:
            case 4:
                return 6;
            case 5: 
            case 6:
                return 8;
            case 7:
            case 8:
                return 12; 
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return 20;
            default:
                return 30;
        }
    }
}