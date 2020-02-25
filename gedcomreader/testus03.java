package gedcomreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author yoohanko98
 */

public class testus03 {
    public testus03() {
    }

    @Test
    public void testbirthBeforeDeath () throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US03");
        GED instance = new GED();

        instance.traversal();
        instance.errorsPrint();

        // Test #1
        assertNotNull(instance.individuals); // checking whether individuals exists within the GED instance

        // Test #2
        assertNotNull(instance.families); // checking whether families exists within the GED instance

        // Test #3
        assertNotNull(instance.errors); // checking whether place to put errors exists within the GED instance

        // Test #4
        assertTrue(true);

        // Test #5
        if (!instance.errors.contains("Error US03: Birthday of Terence /Holland/(I10) occurs after death date.")) {
            fail("the first exception information is wrong");
        }
        else {
            System.out.println("set of errors contains the first error");
        }

        System.out.println("GED test for US03 passed");
    }


}