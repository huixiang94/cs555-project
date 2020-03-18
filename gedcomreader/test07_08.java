package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class test07_08 {
    public test07_08() {
    }
    @Test
    public void testLessthen150yearsold () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US07");
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


        if (!instance.errors.contains("Error US07: age ofDinkar /Chikane/is greater than 150.")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }


        System.out.println("test of US07 passed");
    }
    @Test
    public void testBirthbeforemarriageofparents () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US08");
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


        if (!instance.errors.contains("Error US08: Birthday of Jason /Evans/(I7) in the family of F6 is before the marriage of parents.")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        if (!instance.errors.contains("Error US08: Birthday of Petter /Ken/(I9) in the family of F5 is more than 9 months after parents divorce.")) {
            fail("the second exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the second error");
        }
        if (!instance.errors.contains("Error US08: Birthday of Lisa /Evens/(I10) in the family of F4 is more than 9 months after parents divorce.")) {
            fail("the third exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the third error");
        }


        System.out.println("test of US08 passed");
    }


}
