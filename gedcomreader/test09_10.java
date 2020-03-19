package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * @author yoohanko98
 */

public class test09_10 {
    public test09_10() {
    }
    @Test
    public void testBirthbeforedeathofparents () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US09");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        // Test #1
        assertTrue(true);

        // Test #2
        assertNotNull(instance.individuals);

        // Test #3
        assertNotNull(instance.families);

        // Test #4
        assertNotNull(instance.errors);

        // Test #5
        if (instance.errors.size() <= 0) {
            fail("the exception number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        if (!instance.errors.contains("Error US09: Birthday of Anthony /Holland/(I3) is after 9 months of father's Death date in the family of F5")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        System.out.println("test of US09 passed");
    }

    @Test
    public void testMarriageafter14 () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US10");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        // Test #1
        assertTrue(true);

        // Test #2
        assertNotNull(instance.individuals);

        // Test #3
        assertNotNull(instance.families);

        // Test #4
        assertNotNull(instance.errors);

        // Test #5
        if (instance.errors.size() <= 0) {
            fail("the exception number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        if (!instance.errors.contains("Error US10: Sadie /Ray/（I12) is less than 14; she illegally married, in the family of F6")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        System.out.println("test of US10 passed");
    }

}
