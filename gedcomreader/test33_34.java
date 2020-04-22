package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class test33_34 {
    public test33_34() {
    }
    @Test
    public void testlistorphans () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US33");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if(instance.orphans.size()==0){
            fail("the orphans number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.orphans.contains("Tim /Jams/I1"));
        assertTrue(instance.orphans.contains("Bloom /Jams/I13"));

        System.out.println("test of US33 passed");
    }

    @Test
    public void testlistLargeAgeDifferences () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US34");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if(instance.errors.size()==0){
            fail("the error number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.errors.contains("Family F2 has a large age difference."));
        assertTrue(instance.errors.contains("Family F6 has a large age difference."));
        assertTrue(instance.errors.contains("Family F8 has a large age difference."));
        assertTrue(instance.errors.contains("Family F9 has a large age difference."));
        System.out.println("test of US34 passed");
    }
}
