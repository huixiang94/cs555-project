package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class test09_10 {
    public test09_10() {
    }
    @Test
    public void testBirthbeforedeathofparents () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US09");
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


        if (!instance.errors.contains("Error US09: Birthday of Tim /Jams/(I1) is after 9 months of father's Death date in the family of F1")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        if (!instance.errors.contains("Error US09: Birthday of Petter /Jams/(I2) is after 9 months of father's Death date in the family of F2")) {
            fail("the second exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the second error");
        }
        if (!instance.errors.contains("Error US09: Birthday of Lili /Jams/(I6) is after 9 months of father's Death date in the family of F2")) {
            fail("the third exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the third error");
        }
        System.out.println("test of US09 passed");
    }
    @Test
    public void testMarriageafter14 () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US10");
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


        if (!instance.errors.contains("Error US10: Mary /Smith/（I3) is less than 14; she illegally married, in the family of F1")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }
        if (!instance.errors.contains("Error US10: Jan /White/（I5) is less than 14; she illegally married, in the family of F2")) {
            fail("the second exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the second error");
        }
        if (!instance.errors.contains("Error US10: Jason /Evans/（I7) was less than 14, when he got married, in the family of F4")) {
            fail("the third exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the third error");
        }
        System.out.println("test of US10 passed");
    }

}
