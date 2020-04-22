package gedcomreader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class test31_32 {

    public test31_32() {

    }

    @Test
    public void testlistLivingSingle() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US31");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if (instance.livingSingle.size() == 0) {
            fail("the livingSingle number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.livingSingle.contains("Bloom /Jams/(I13)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I18)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I19)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I20)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I21)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I22)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I23)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I24)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I25)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I26)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I27)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I28)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I29)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I30)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I31)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I32)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I33)"));
        assertTrue(instance.livingSingle.contains("Jake /Smith/(I34)"));

        System.out.println("test of US31 passed");
    }

    @Test
    public void testlistMultipleBirth() throws IOException, FileNotFoundException, ParseException {
        System.out.println("Testing US32");
        GED instance = new GED();
        instance.traversal();
        instance.errorsPrint();

        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);

        if (instance.errors.size() == 0) {
            fail("the error number is wrong");
        }
        else {
            System.out.println("the number of errors is correct");
        }

        assertTrue(instance.errors.contains("There are two same birthdays: 09-16-1972"));
        assertTrue(instance.errors.contains("There are two same birthdays: 10-15-1948"));

        System.out.println("test of US32 passed");
    }
}


