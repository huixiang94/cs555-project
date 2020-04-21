package gedcomreader;

import org.junit.Test;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class test29_30 {
    public test29_30() {
    }
    @Test
    public void testlistDeceased () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US29");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if(instance.deceased.size()==0){
            fail("the deceased number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.deceased.contains("Petter /Jams/(I2)"));
        assertTrue(instance.deceased.contains("Rone /Jams/(I4)"));
        assertTrue(instance.deceased.contains("Sophea /Taylor/(I12)"));
        assertTrue(instance.deceased.contains("Ming /Huang/(I17)"));

        System.out.println("test of US29 passed");
    }

    @Test
    public void testlistLivingMarried () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US30");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if(instance.livingMarried.size()==0){
            fail("the deceased number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.livingMarried.contains("Mary /Smith/(I3)"));
        assertTrue(instance.livingMarried.contains("Jason /Evans/(I7)"));

        System.out.println("test of US30 passed");
    }
}
