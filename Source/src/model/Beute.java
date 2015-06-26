package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.*;

import controller.Dice;

public abstract class Beute {
    
    private Gegenstand ausruestung_;
    static private Random random = new Random();

    protected static double generateGaussianValue(double mean, double variance) {
        return random.nextGaussian()*Math.sqrt(variance) + mean;
    }
    
    
    
    // 0.5 Harnisch + 1/3 * 0.5 ...
//    public void generateAusruestungBeute() { // TODO: NOT FINISHED
//        List<Gegenstand> sortedList = Gegenstand.getAll();
//        Gegenstand.sortByKosten(sortedList);
//        
//        while(resumingValue > 0) {
//            Gegenstand chosen = Beute.getRandomItemWithMaxValue(resumingValue, sortedList);
//            resumingValue = updateResumingValue(resumingValue, chosen);
//        }
//    }
    
    protected static Gegenstand getRandomItemWithApproximateValue(int value, List<Gegenstand> sortedList) {
        for(int i = 0; i<sortedList.size(); i++) {
            Gegenstand item = sortedList.get(i);
            if(item.computeValue() >= value) {
                if(i == 0)
                    return item;
                Gegenstand previousItem = sortedList.get(i-1);
                boolean previousItemIsBetter = Math.abs(value - item.computeValue()) >
                    Math.abs(value - previousItem.computeValue());
                return previousItemIsBetter ? previousItem : item;
            }
        }
        return sortedList.get(sortedList.size() - 1);
    }
    
    protected static Gegenstand getRandomItemWithMaxValue(int maxValue, List<Gegenstand> sortedList) {
        int range = sortedList.size();
        while(range > 0) {
            int sample = Dice.rollDice(range) - 1;
            Gegenstand chosen = sortedList.get(sample);
            if(chosen.getKosten_() <= maxValue)
                return chosen;
            range = sample;
        }
        return null;
    }
}
