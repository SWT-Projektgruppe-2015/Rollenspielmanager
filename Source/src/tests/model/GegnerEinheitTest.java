package tests.model;

import static org.junit.Assert.*;

import java.util.List;

import model.GegnerEinheit;
import model.GegnerTyp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GegnerEinheitTest {
    
    static GegnerTyp normalerGegner;
    
    @BeforeClass
    public static void initObjects() throws Exception {
        normalerGegner = new GegnerTyp();
        normalerGegner.addToDB();
    }
    
    @AfterClass
    public static void deleteFromDB() {
        normalerGegner.deleteFromDB();
    }
    
    @Test
    public void einheitIsNotNull() {
        GegnerEinheit neueEinheit = new GegnerEinheit(normalerGegner,1);
        assertTrue(neueEinheit != null);
        assertTrue(neueEinheit.getID_() == 1);
    }
    
    @Test
    public void createEinheitenTest() {
        List<GegnerEinheit> einheiten = GegnerEinheit.createEinheiten(normalerGegner, 6);
        assertTrue("size is not correct", einheiten.size() == 6);
        assertTrue("created einheit is null", einheiten.get(2) != null);
        assertTrue("ID was not set correct", einheiten.get(5).getID_() == 5);
    }
    
    @Test
    public void verifyUniqueness()  {
        List<GegnerEinheit> einheiten = GegnerEinheit.createEinheiten(normalerGegner, 2);
        assertTrue(einheiten.get(0).compareTo(einheiten.get(1))!= 0);
    }
}
