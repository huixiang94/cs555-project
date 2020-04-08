package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class test15_21 {
    public test15_21(){

    }
    @Test
    public void testfewerThan15Siblings() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US15");
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


        if (!instance.errors.contains("Error US15: Family (F2) have 15 or more siblings")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }


        System.out.println("test of US15 passed");
    }
    @Test
    public void testcorrectGender() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US21");
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


        if (!instance.errors.contains("Error US21: Husband in family F1 has a wrong gender.")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }


        System.out.println("test of US21 passed");
    }

}
