package gedcomreader;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class TestUS17_18 {
    @Test
    public void testUS17 () throws IOException, ParseException {

        GED instance = new GED();
        instance.traversal();
        instance.noBigamy();
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
        if (!instance.errors.contains("Error US17:Lisa /Evens/is married with descendants")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        if (!instance.errors.contains("Error US17:Tim /Jams/is descendants that married")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        System.out.println("test of US17 passed");
    }

    @Test
    public void testUS18 () throws IOException, ParseException {

        GED instance = new GED();
        instance.traversal();
        instance.parentNotoold();
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
        if (!instance.errors.contains("Error US18:Tim /Jams/ and Lisa /Evens/ are siblings")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }



        System.out.println("test of US18 passed");
    }
}
