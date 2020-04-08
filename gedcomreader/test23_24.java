package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class test23_24 {

    public test23_24(){

    }

    @Test
    public void testuniqueNameAndBirth() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US23");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);


        if (instance.errors.size() <= 0) {
            fail("the exception number is wrong");
        } else {
            System.out.println("the number of errors is correct");
        }


        if (!instance.errors.contains("US23 Error: There are two same names: Mary /Smith/(I3)")) {
            fail("the first exception information is wrong");
        } else {
            System.out.println("set of errors contains the first error");
        }

        System.out.println("test of US23 passed");
    }

    @Test
    public void testUniqueFamilyBySpouse() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US24");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if (instance.errors.size() <= 0) {
            fail("the exception number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        if (!instance.errors.contains("Error US24: there are 2 families(F1 and F4) with the same spouses(Petter /Jams/ and Mary /Smith/) getting married in the same date 9 JUL 2012")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }


        System.out.println("test of US24 passed");
    }
}


