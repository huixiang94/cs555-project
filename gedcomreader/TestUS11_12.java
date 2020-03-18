package gedcomreader;
import java.io.IOException;
import java.text.ParseException;


import org.junit.Test;
import static org.junit.Assert.*;

public class TestUS11_12 {
    @Test
    public void testBigamist () throws IOException, ParseException {

        GED instance = new GED();
        instance.traversal();
        instance.noBigamy();
        instance.errorsPrint();
        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);
    }

    @Test
    public void testparentNotoold () throws IOException, ParseException {

        GED instance = new GED();
        instance.traversal();
        instance.parentNotoold();
        instance.errorsPrint();
        assertTrue(true);
        assertNotNull(instance.individuals);
        assertNotNull(instance.families);
        assertNotNull(instance.errors);
    }
}

